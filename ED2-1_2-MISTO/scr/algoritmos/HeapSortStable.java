package algoritmos;

import java.util.*;
import dados.entrada.Commit;

public class HeapSortStable {

    public static List<Commit> heapSortEstavel(List<Commit> commits) {
        if (commits == null || commits.isEmpty()) {
            return new ArrayList<>();
        }

        // ==========================================
        // ORDENAR commits por timestamp e ordem original
        // ==========================================
        List<Commit> resultado = new ArrayList<>(commits);
        heapSortCommits(resultado);

        return resultado;
    }

    // ===================================================
    //  HeapSort para List<Commit>
    // ===================================================
    private static void heapSortCommits(List<Commit> commits) {
        int n = commits.size();
        buildMaxHeap(commits, n);

        for (int i = n - 1; i > 0; i--) {
            swap(commits, 0, i);
            maxHeapify(commits, 0, i);
        }
    }

    private static void buildMaxHeap(List<Commit> commits, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(commits, i, n);
        }
    }

    private static void maxHeapify(List<Commit> commits, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int largest = i;

        if (left < n && compareCommits(commits.get(left), commits.get(largest)) > 0)
            largest = left;

        if (right < n && compareCommits(commits.get(right), commits.get(largest)) > 0)
            largest = right;

        if (largest != i) {
            swap(commits, i, largest);
            maxHeapify(commits, largest, n);
        }
    }

    private static int compareCommits(Commit a, Commit b) {
        // Primeiro compara por timestamp
        int timestampCompare = a.getTimestamp().compareTo(b.getTimestamp());
        if (timestampCompare != 0) {
            return timestampCompare;
        }
        // Se timestamp for igual, compara por ordem original para garantir estabilidade
        return Integer.compare(a.getOrdemOriginal(), b.getOrdemOriginal());
    }

    private static void swap(List<Commit> commits, int i, int j) {
        Commit temp = commits.get(i);
        commits.set(i, commits.get(j));
        commits.set(j, temp);
    }
}