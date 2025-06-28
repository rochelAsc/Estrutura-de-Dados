package hash;

import model.Transacao;
import estruturas.Arvore;
import estruturas.TreeAVL;
import estruturas.TreeRB;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * Tabela Hash híbrida de transações:
 * - Indexação por 'id' via encadeamento (listas ligadas)
 * - Indexação por 'origem' via sondagem quadrática com migração a árvore AVL → RB
 */
public class HashHibrida {
    private int M;
    private LinkedList<Transacao>[] idTable;
    private Object[] originTable;

    @SuppressWarnings("unchecked")
    public HashHibrida(int cap) {
        this.M = cap;
        idTable = (LinkedList<Transacao>[]) new LinkedList[M];
        originTable = new Object[M];
        for (int i = 0; i < M; i++) {
            idTable[i] = new LinkedList<>();
            originTable[i] = null;
        }
    }

    /**
     * Função de hash (módulo) para strings
     */
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * Insere transação em ambos índices: por id e por origem
     */
    public void inserir(Transacao t) {
        inserirPorId(t);
        inserirPorOrigem(t);
    }

    /**
     * Encadeamento simples por 'id'
     */
    private void inserirPorId(Transacao t) {
        int i = hash(t.getId());
        idTable[i].add(t);
    }

    /**
     * Sondagem quadrática por 'origem' com migração a árvore após 3 colisões
     */
    private void inserirPorOrigem(Transacao t) {
        String o = t.getOrigem();
        int j = hash(o);
        int colisoes = 0;

        for (int k = 0; k < M; k++) {
            int idx = (j + k * k) % M;
            Object slot = originTable[idx];

            if (slot == null) {
                // posição livre: insere transação
                originTable[idx] = t;
                return;
            }

            if (slot instanceof Arvore) {
                // já migrado para árvore: delega inserção
                Arvore tree = (Arvore) slot;
                tree.inserir(t);
                // se era AVL e ficou muito alta, converte para RB
                if (tree instanceof TreeAVL && tree.altura() > 10) {
                    TreeRB rb = new TreeRB();
                    // coleta todas as transações da AVL
                    List<Transacao> todas = tree.buscarIntervalo(
                            LocalDateTime.MIN, LocalDateTime.MAX);
                    for (Transacao tr : todas) {
                        rb.inserir(tr);
                    }
                    originTable[j] = rb;
                }
                return;
            }

            // slot contém outra Transacao → colisão
            Transacao existente = (Transacao) slot;
            if (!existente.getOrigem().equals(o)) {
                // colisão por outra origem, continua sondagem
                colisoes++;
                if (colisoes <= 3) {
                    continue;
                }
                // 4ª colisão para esta origem: migra todas para AVL
                TreeAVL avl = new TreeAVL();
                for (int m = 0; m < M; m++) {
                    Object obj = originTable[m];
                    if (obj instanceof Transacao) {
                        Transacao tr = (Transacao) obj;
                        if (tr.getOrigem().equals(o)) {
                            avl.inserir(tr);
                            originTable[m] = null;
                        }
                    }
                }
                // insere a transação atual e armazena a AVL no slot inicial
                avl.inserir(t);
                originTable[j] = avl;
                return;
            } else {
                // mesma origem em posição ocupada: aumenta colisões mas mantém inserção linear
                colisoes++;
                if (colisoes <= 3) {
                    continue;
                }
                // migra para AVL como acima
                TreeAVL avl = new TreeAVL();
                for (int m = 0; m < M; m++) {
                    Object obj = originTable[m];
                    if (obj instanceof Transacao) {
                        Transacao tr = (Transacao) obj;
                        if (tr.getOrigem().equals(o)) {
                            avl.inserir(tr);
                            originTable[m] = null;
                        }
                    }
                }
                avl.inserir(t);
                originTable[j] = avl;
                return;
            }
        }
        // TODO: tratar tabela cheia ou resize
    }

    /**
     * Busca transação por 'id'
     */
    public Transacao buscarPorId(String id) {
        int i = hash(id);
        for (Transacao t : idTable[i]) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Busca todas as transações para uma origem em intervalo de timestamp
     */
    public List<Transacao> buscarPorOrigemIntervalo(String o,
                                                    LocalDateTime inicio,
                                                    LocalDateTime fim) {
        List<Transacao> resultado = new ArrayList<>();
        int j = hash(o);

        for (int k = 0; k < M; k++) {
            int idx = (j + k * k) % M;
            Object slot = originTable[idx];
            if (slot == null) {
                break;
            }
            if (slot instanceof Arvore) {
                // delega busca na árvore
                return ((Arvore) slot).buscarIntervalo(inicio, fim);
            }
            Transacao tr = (Transacao) slot;
            if (tr.getOrigem().equals(o)
                    && !tr.getTimestamp().isBefore(inicio)
                    && !tr.getTimestamp().isAfter(fim)) {
                resultado.add(tr);
            }
            // continua sondagem
        }
        return resultado;
    }

    /**
     * Ajusta o tamanho da tabela quando estiver muito cheia
     */
    private void resize() {
        // TODO: implementar redimensionamento dinâmico (geração de nova tabela com M*2)
    }
}
