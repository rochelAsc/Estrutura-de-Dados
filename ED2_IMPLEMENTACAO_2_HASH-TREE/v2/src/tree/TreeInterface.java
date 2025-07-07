package tree;

import model.Transacao;
import java.util.List;

public interface TreeInterface {

    /**
     * Insere uma transação na árvore.
     */
    void insert(Transacao t);

    /**
     * Busca todas as transações com a origem exata fornecida.
     */
    List<Transacao> searchByOrigem(String origem);

    /**
     * Busca as transações para uma origem dentro de um intervalo de datas.
     * O intervalo inclui as datas de início e fim.
     */
    List<Transacao> searchByIntervalo(String origem, String dataInicio, String dataFim);

    /**
     * Retorna a altura da árvore.
     */
    int getAltura();

    /**
     * Indica se a árvore deve ser convertida em Rubro-Negra.
     * No caso da AVL, isso acontece se a altura for maior que 10.
     */
    boolean precisaMigrarParaRubroNegra();
}
