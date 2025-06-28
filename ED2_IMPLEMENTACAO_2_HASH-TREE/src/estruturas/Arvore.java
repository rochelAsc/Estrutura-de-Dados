package estruturas;

import model.Transacao;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface para estruturas de árvore de transações ordenadas por timestamp.
 * Implementada por TreeAVL e TreeRB.
 */
public interface Arvore {
    /**
     * Insere uma transação na árvore.
     * @param transacao objeto Transacao a ser inserido
     */
    void inserir(Transacao transacao);

    /**
     * Busca todas as transações cujo timestamp está no intervalo [inicio, fim].
     * @param inicio data/hora inicial do intervalo (inclusivo)
     * @param fim data/hora final do intervalo (inclusivo)
     * @return lista de transações encontradas em ordem crescente de timestamp
     */
    List<Transacao> buscarIntervalo(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Retorna a altura da árvore.
     * @return altura (nível máximo) da árvore
     */
    int altura();

    /**
     * Retorna o número de nós (transações) na árvore.
     * @return quantidade de nós na árvore
     */
    int tamanho();
}
