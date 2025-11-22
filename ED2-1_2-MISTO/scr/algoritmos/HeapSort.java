package algoritmos;

public class HeapSort {

    // Corrigido para 0-based
    public static void heapSort(int[] A, int n) {

        // build max heap
        buildMaxHeap(A, n);

        // extrair elementos
        for (int i = n - 1; i > 0; i--) {
            swap(A, 0, i); // maior vai para o final
            maxHeapify(A, 0, i); // heap reduzido
        }
    }

    // Constrói max-heap em 0-based
    private static void buildMaxHeap(int[] A, int n) {
        // último nó interno fica em (n/2 - 1)
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(A, i, n);
        }
    }

    // Heapify 0-based
    private static void maxHeapify(int[] A, int i, int n) {
        int left = 2 * i + 1;     // filho esquerdo
        int right = 2 * i + 2;    // filho direito
        int largest = i;

        if (left < n && A[left] > A[largest]) {
            largest = left;
        }

        if (right < n && A[right] > A[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(A, i, largest);
            maxHeapify(A, largest, n);
        }
    }

    // troca padrão 0-based
    private static void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }
}
