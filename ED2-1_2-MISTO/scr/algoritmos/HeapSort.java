package algoritmos;

public class HeapSort {

    // Função para construir o Max-Heap
    public static void buildMaxHeap(int[] A, int n) {
        // Inicia do último nó não folha até a raiz (n/2)
        for (int i = n / 2; i >= 1; i--) {
            maxHeapify(A, i, n);
        }
    }

    // Função para manter a propriedade do Max-Heap
    public static void maxHeapify(int[] A, int i, int n) {
        int l = left(i); // Índice do filho esquerdo
        int r = right(i); // Índice do filho direito
        int largest;

        // Verifica se o filho esquerdo é maior que o nó atual
        if (l <= n && A[l - 1] > A[i - 1]) {
            largest = l;
        } else {
            largest = i;
        }

        // Verifica se o filho direito é maior que o maior entre o nó atual e o filho esquerdo
        if (r <= n && A[r - 1] > A[largest - 1]) {
            largest = r;
        }

        // Se o maior não é o nó atual, troca e faz recursão
        if (largest != i) {
            swap(A, i, largest);
            maxHeapify(A, largest, n);
        }
    }

    // Função para ordenar o array usando HeapSort
    public static void heapSort(int[] A, int n) {
        buildMaxHeap(A, n); // Constrói o Max-Heap
        for (int i = n; i >= 2; i--) {
            // Troca o primeiro elemento (maior) com o último
            swap(A, 0, i - 1);
            maxHeapify(A, 1, i - 1); // Restabelece a propriedade do Max-Heap
        }
    }

    // Função auxiliar para trocar dois elementos
    public static void swap(int[] A, int i, int j) {
        int temp = A[i - 1];
        A[i - 1] = A[j - 1];
        A[j - 1] = temp;
    }

    // Função auxiliar para obter o índice do filho esquerdo
    public static int left(int i) {
        return 2 * i;
    }

    // Função auxiliar para obter o índice do filho direito
    public static int right(int i) {
        return 2 * i + 1;
    }


}
