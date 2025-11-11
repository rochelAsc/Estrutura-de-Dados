package tree;

import model.RBNode;
import model.Transacao;
import service.Estatisticas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TreeRB implements TreeInterface {

    private RBNode raiz;
    private static final boolean VERMELHO = true;
    private static final boolean PRETO = false;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void insert(Transacao t) {
        raiz = inserir(raiz, t);
        raiz.cor = PRETO;
        Estatisticas.incrementarAtribuicoes();
    }

    private RBNode inserir(RBNode node, Transacao t) {
        if (node == null) {
            Estatisticas.incrementarAtribuicoes();
            return new RBNode(t.getOrigem(), t);
        }

        int cmp = t.getOrigem().compareTo(node.chave);
        Estatisticas.incrementarComparacoes();

        if (cmp < 0) {
            node.esquerda = inserir(node.esquerda, t);
            node.esquerda.pai = node;
            Estatisticas.incrementarAtribuicoes();
        } else if (cmp > 0) {
            node.direita = inserir(node.direita, t);
            node.direita.pai = node;
            Estatisticas.incrementarAtribuicoes();
        } else {
            node.adicionarTransacao(t);
            Estatisticas.incrementarAtribuicoes();
            return node;
        }

        if (ehVermelho(node.direita) && !ehVermelho(node.esquerda)) {
            Estatisticas.incrementarComparacoes();
            node = rotacaoEsquerda(node);
        }

        if (ehVermelho(node.esquerda) && ehVermelho(node.esquerda.esquerda)) {
            Estatisticas.incrementarComparacoes();
            node = rotacaoDireita(node);
        }

        if (ehVermelho(node.esquerda) && ehVermelho(node.direita)) {
            Estatisticas.incrementarComparacoes();
            inverterCores(node);
        }

        return node;
    }

    private boolean ehVermelho(RBNode node) {
        return node != null && node.cor == VERMELHO;
    }

    private RBNode rotacaoEsquerda(RBNode h) {
        RBNode x = h.direita;
        RBNode T2 = x.esquerda;

        x.esquerda = h;
        h.direita = T2;

        x.pai = h.pai;
        h.pai = x;

        x.cor = h.cor;
        h.cor = VERMELHO;

        Estatisticas.incrementarAtribuicoes();
        return x;
    }

    private RBNode rotacaoDireita(RBNode h) {
        RBNode x = h.esquerda;
        RBNode T2 = x.direita;

        x.direita = h;
        h.esquerda = T2;

        x.pai = h.pai;
        h.pai = x;

        x.cor = h.cor;
        h.cor = VERMELHO;

        Estatisticas.incrementarAtribuicoes();
        return x;
    }

    private void inverterCores(RBNode h) {
        h.cor = VERMELHO;
        if (h.esquerda != null) h.esquerda.cor = PRETO;
        if (h.direita != null) h.direita.cor = PRETO;
        Estatisticas.incrementarAtribuicoes();
    }

    private RBNode buscar(RBNode node, String chave) {
        if (node == null) return null;

        int cmp = chave.compareTo(node.chave);
        Estatisticas.incrementarComparacoes();

        if (cmp < 0) return buscar(node.esquerda, chave);
        if (cmp > 0) return buscar(node.direita, chave);
        return node;
    }

    @Override
    public List<Transacao> searchByOrigem(String origem) {
        RBNode node = buscar(raiz, origem);
        return (node != null) ? node.transacoes : new ArrayList<>();
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
        return calcularAltura(raiz);
    }

    private int calcularAltura(RBNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calcularAltura(node.esquerda), calcularAltura(node.direita));
    }

    @Override
    public boolean precisaMigrarParaRubroNegra() {
        return false; // já é RB
    }
}
