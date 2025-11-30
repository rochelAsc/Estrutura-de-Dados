package algoritmos;

import java.util.*;
import dados.entrada.Commit;
import estruturas.AVLTree;

public class AVLTreeGroup {

    private AVLTree<Date> avlTree;
    private Map<Date, List<Commit>> commitMap;

    public AVLTreeGroup() {
        this.avlTree = new AVLTree<>();
        this.commitMap = new HashMap<>();
    }

    public void agruparCommits(List<Commit> commits) {
        for (Commit commit : commits) {
            Date timestamp = commit.getTimestamp();

            // Se o timestamp não existe no mapa, cria uma nova lista
            if (!commitMap.containsKey(timestamp)) {
                commitMap.put(timestamp, new LinkedList<>());
                // Insere o timestamp na AVLTree
                avlTree.insert(timestamp);
            }

            // Adiciona o commit à lista correspondente ao timestamp
            commitMap.get(timestamp).add(commit);
        }
    }

    public List<Commit> extrairTodosCommits() {
        List<Commit> todosCommits = new ArrayList<>();

        // Para extrair todos os commits sem ordem específica
        for (List<Commit> commitList : commitMap.values()) {
            todosCommits.addAll(commitList);
        }

        return todosCommits;
    }

    // Método para extrair commits ordenados por timestamp (do mais recente para o mais antigo)
    public List<Commit> extrairCommitsOrdenados() {
        List<Commit> commitsOrdenados = new ArrayList<>();

        // Primeiro precisamos obter os timestamps em ordem da AVLTree
        List<Date> timestampsOrdenados = obterTimestampsOrdenados();

        // Para cada timestamp em ordem, adiciona os commits correspondentes
        for (Date timestamp : timestampsOrdenados) {
            commitsOrdenados.addAll(commitMap.get(timestamp));
        }

        return commitsOrdenados;
    }

    // Método auxiliar para obter timestamps em ordem (do mais recente para o mais antigo)
// Método auxiliar para obter timestamps em ordem (do mais recente para o mais antigo)
    private List<Date> obterTimestampsOrdenados() {
        // Usa o método inOrderReverso da AVLTree para obter do maior (mais recente) para o menor (mais antigo)
        return avlTree.inOrderReverso();
    }

    // Getters para acesso externo se necessário
    public AVLTree<Date> getAvlTree() {
        return avlTree;
    }

    public Map<Date, List<Commit>> getCommitMap() {
        return commitMap;
    }
}