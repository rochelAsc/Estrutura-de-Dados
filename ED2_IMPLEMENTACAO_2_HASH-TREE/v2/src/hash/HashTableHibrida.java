package hash;

import model.Transacao;
import tree.*;
import model.*;
import service.Estatisticas;

import java.util.*;

public class HashTableHibrida {

    private Object[] tabelaId;
    private Object[] tabelaOrigem;
    private int tamanho;

    private Map<String, Integer> contagemColisoesOrigem;

    public HashTableHibrida(int capacidade) {
        this.tamanho = HashUtils.proximoPrimo(capacidade);
        this.tabelaId = new Object[tamanho];
        this.tabelaOrigem = new Object[tamanho];
        this.contagemColisoesOrigem = new HashMap<>();
        Estatisticas.incrementarAtribuicoes();
    }

    // ------------------------
    // Inserção por ID (encadeamento)
    // ------------------------
    private void inserirPorId(Transacao t) {
        int h = HashUtils.hash(t.getId(), tamanho);
        Estatisticas.incrementarAtribuicoes();

        if (tabelaId[h] == null) {
            Estatisticas.incrementarComparacoes();
            tabelaId[h] = new LinkedList<Transacao>();
            Estatisticas.incrementarAtribuicoes();
        }

        @SuppressWarnings("unchecked")
        LinkedList<Transacao> lista = (LinkedList<Transacao>) tabelaId[h];
        lista.add(t);
        Estatisticas.incrementarAtribuicoes();
    }

    // ------------------------
    // Inserção por Origem (sondagem quadrática + migração)
    // ------------------------
    private void inserirPorOrigem(Transacao t) {
        int h = HashUtils.hash(t.getOrigem(), tamanho);
        Estatisticas.incrementarAtribuicoes();

        int i = 0;

        while (i < tamanho) {
            int pos = HashUtils.sondagemQuadratica(h, i, tamanho);
            Estatisticas.incrementarAtribuicoes();

            Object obj = tabelaOrigem[pos];
            Estatisticas.incrementarComparacoes();

            if (obj == null) {
                tabelaOrigem[pos] = t;
                Estatisticas.incrementarAtribuicoes();
                contagemColisoesOrigem.put(t.getOrigem(), 1);
                Estatisticas.incrementarAtribuicoes();
                return;
            }

            if (obj instanceof Transacao) {
                Estatisticas.incrementarComparacoes();

                Transacao existente = (Transacao) obj;
                Estatisticas.incrementarAtribuicoes();

                if (existente.getOrigem().equals(t.getOrigem())) {
                    Estatisticas.incrementarComparacoes();

                    contagemColisoesOrigem.merge(t.getOrigem(), 1, Integer::sum);
                    Estatisticas.incrementarAtribuicoes();
                    int colisoes = contagemColisoesOrigem.get(t.getOrigem());
                    Estatisticas.incrementarAtribuicoes();

                    if (colisoes > 3) {
                        Estatisticas.incrementarComparacoes();

                        TreeAVL avl = new TreeAVL();
                        Estatisticas.incrementarAtribuicoes();

                        avl.insert(existente);
                        avl.insert(t);
                        //System.out.println("🌳 Altura atual da AVL (" + t.getOrigem() + "): " + avl.getAltura());


                        if (avl.precisaMigrarParaRubroNegra()) {
                            TreeRB novaRB = new TreeRB();
                            for (Transacao tx : avl.getTodasTransacoes()) {
                                novaRB.insert(tx);
                            }
                            tabelaOrigem[pos] = novaRB;
                            contagemColisoesOrigem.remove(t.getOrigem());
                        } else {
                            tabelaOrigem[pos] = avl;
                        }
                        Estatisticas.incrementarAtribuicoes();
                    } else {
                        i++;
                    }
                    return;
                } else {
                    i++;
                    continue;
                }
            }

            if (obj instanceof TreeInterface) {
                Estatisticas.incrementarComparacoes();

                TreeInterface arvore = (TreeInterface) obj;
                Estatisticas.incrementarAtribuicoes();

                arvore.insert(t);

                // ✅ Verificação e migração ocorre IMEDIATAMENTE APÓS INSERÇÃO
                if (arvore instanceof TreeAVL) {
                    TreeAVL avl = (TreeAVL) arvore;
                    //System.out.println("🌳 Altura atual da AVL (" + t.getOrigem() + "): " + avl.getAltura());

                    if (avl.precisaMigrarParaRubroNegra()) {
                        //System.out.println("✅ Migrando AVL para Rubro-Negra: " + t.getOrigem());
                        TreeRB novaRB = new TreeRB();
                        Estatisticas.incrementarAtribuicoes();

                        for (Transacao tx : avl.getTodasTransacoes()) {
                            novaRB.insert(tx);
                        }

                        tabelaOrigem[pos] = novaRB;
                        Estatisticas.incrementarAtribuicoes();

                        contagemColisoesOrigem.remove(t.getOrigem());
                        Estatisticas.incrementarAtribuicoes();
                    }
                }

                return;
            }

            i++;
        }

        System.err.println("Falha ao inserir origem (tabela cheia?): " + t.getOrigem());
    }

    // ------------------------
    // Inserção pública
    // ------------------------
    public void inserir(Transacao t) {
        inserirPorId(t);
        inserirPorOrigem(t);
    }

    // ------------------------
    // Busca por origem exata
    // ------------------------
    public List<Transacao> buscarPorOrigem(String origem) {
        int h = HashUtils.hash(origem, tamanho);
        int i = 0;

        while (i < tamanho) {
            int pos = HashUtils.sondagemQuadratica(h, i, tamanho);
            Object obj = tabelaOrigem[pos];

            if (obj == null) return new ArrayList<>();

            if (obj instanceof Transacao) {
                Transacao t = (Transacao) obj;
                if (t.getOrigem().equals(origem)) {
                    return List.of(t);
                }
            }

            if (obj instanceof TreeInterface) {
                return ((TreeInterface) obj).searchByOrigem(origem);
            }

            i++;
        }

        return new ArrayList<>();
    }

    // ------------------------
    // Busca por intervalo
    // ------------------------
    public List<Transacao> buscarPorOrigemEIntervalo(String origem, String dataInicio, String dataFim) {
        int h = HashUtils.hash(origem, tamanho);
        int i = 0;

        while (i < tamanho) {
            int pos = HashUtils.sondagemQuadratica(h, i, tamanho);
            Object obj = tabelaOrigem[pos];

            if (obj == null) return new ArrayList<>();

            if (obj instanceof TreeInterface) {
                return ((TreeInterface) obj).searchByIntervalo(origem, dataInicio, dataFim);
            }

            i++;
        }

        return new ArrayList<>();
    }

    // ------------------------
    // Utilitários
    // ------------------------
    public int getTamanhoTabela() {
        return tamanho;
    }

    public int contarAVLs() {
        int count = 0;
        for (Object o : tabelaOrigem) {
            if (o instanceof TreeAVL) count++;
        }
        return count;
    }

    public int contarRBs() {
        int count = 0;
        for (Object o : tabelaOrigem) {
            if (o instanceof TreeRB) count++;
        }
        return count;
    }
}
