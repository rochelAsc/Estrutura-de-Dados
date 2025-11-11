package tree;

import model.AVLNode;
import model.Transacao;
import service.Estatisticas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TreeAVL implements TreeInterface {

    private AVLNode raiz;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void insert(Transacao t) {
        raiz = insertRec(raiz, t);
        Estatisticas.incrementarAtribuicoes();
    }

    private AVLNode insertRec(AVLNode node, Transacao t) {
        if (node == null) {
            Estatisticas.incrementarComparacoes();
            Estatisticas.incrementarAtribuicoes();
            return new AVLNode(t.getId(), t);
        }

        int cmp = t.getOrigem().compareTo(node.chave);
        Estatisticas.incrementarComparacoes();

        if (cmp < 0) {
            node.esquerda = insertRec(node.esquerda, t);
            Estatisticas.incrementarAtribuicoes();
        } else if (cmp > 0) {
            node.direita = insertRec(node.direita, t);
            Estatisticas.incrementarAtribuicoes();
        } else {
            node.adicionarTransacao(t);
            Estatisticas.incrementarAtribuicoes();
            return node;
        }

        atualizarAltura(node);
        return balancear(node);
    }

    private void atualizarAltura(AVLNode node) {
        int alturaEsq = (node.esquerda != null) ? node.esquerda.altura : 0;
        int alturaDir = (node.direita != null) ? node.direita.altura : 0;
        node.altura = 1 + Math.max(alturaEsq, alturaDir);
        Estatisticas.incrementarAtribuicoes();
    }

    private int fatorBalanceamento(AVLNode node) {
        int alturaEsq = (node.esquerda != null) ? node.esquerda.altura : 0;
        int alturaDir = (node.direita != null) ? node.direita.altura : 0;
        return alturaEsq - alturaDir;
    }

    private AVLNode balancear(AVLNode node) {
        int fb = fatorBalanceamento(node);
        Estatisticas.incrementarComparacoes();

        if (fb > 1) {
            if (fatorBalanceamento(node.esquerda) < 0) {
                Estatisticas.incrementarComparacoes();
                node.esquerda = rotacaoEsquerda(node.esquerda);
                Estatisticas.incrementarAtribuicoes();
            }
            return rotacaoDireita(node);
        }

        if (fb < -1) {
            if (fatorBalanceamento(node.direita) > 0) {
                Estatisticas.incrementarComparacoes();
                node.direita = rotacaoDireita(node.direita);
                Estatisticas.incrementarAtribuicoes();
            }
            return rotacaoEsquerda(node);
        }

        return node;
    }

    private AVLNode rotacaoDireita(AVLNode y) {
        AVLNode x = y.esquerda;
        AVLNode T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        atualizarAltura(y);
        atualizarAltura(x);

        Estatisticas.incrementarAtribuicoes();
        return x;
    }

    private AVLNode rotacaoEsquerda(AVLNode x) {
        AVLNode y = x.direita;
        AVLNode T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        atualizarAltura(x);
        atualizarAltura(y);

        Estatisticas.incrementarAtribuicoes();
        return y;
    }

    @Override
    public List<Transacao> searchByOrigem(String origem) {
        AVLNode node = buscar(raiz, origem);
        Estatisticas.incrementarComparacoes();
        return (node != null) ? node.transacoes : new ArrayList<>();
    }

    private AVLNode buscar(AVLNode node, String origem) {
        if (node == null) return null;

        int cmp = origem.compareTo(node.chave);
        Estatisticas.incrementarComparacoes();

        if (cmp < 0) return buscar(node.esquerda, origem);
        if (cmp > 0) return buscar(node.direita, origem);
        return node;
    }

    @Override
    public List<Transacao> searchByIntervalo(String origem, String dataInicio, String dataFim) {
        List<Transacao> todas = searchByOrigem(origem);
        List<Transacao> resultado = new ArrayList<>();

        try {
            Date ini = sdf.parse(dataInicio);
            Date fim = sdf.parse(dataFim);

            for (Transacao t : todas) {
                Date data = sdf.parse(t.getTimestamp());
                if (!data.before(ini) && !data.after(fim)) {
                    resultado.add(t);
                }
            }
        } catch (ParseException e) {
            System.err.println("Erro ao converter datas: " + e.getMessage());
        }

        return resultado;
    }

    @Override
    public int getAltura() {
        return (raiz != null) ? raiz.altura : 0;
    }

    @Override
    public boolean precisaMigrarParaRubroNegra() {
        return getAltura() > 10;
    }

    public List<Transacao> getTodasTransacoes() {
        List<Transacao> resultado = new ArrayList<>();
        percorrer(raiz, resultado);
        return resultado;
    }

    private void percorrer(AVLNode node, List<Transacao> lista) {
        if (node == null) return;
        lista.addAll(node.transacoes);
        percorrer(node.esquerda, lista);
        percorrer(node.direita, lista);
    }
}
