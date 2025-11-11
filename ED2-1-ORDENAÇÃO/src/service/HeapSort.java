package service;

import model.Produto;

public class HeapSort {

    // método principal para ordenar o array de produtos usando heapsort
    public static void HeapSort(Produto[] A) {
        int n = A.length;

        // constroi a max heap a partir do array
        buildMaxHeap(A);

        // percorre o array do fim até o início
        for (int i = n - 1; i > 0; i--) {
            // troca o maior elemento (raiz da heap) com o elemento na posição i
            Produto temp = A[0];
            A[0] = A[i];
            A[i] = temp;

            // ajusta a heap para manter a propriedade de max heap, ignorando os elementos já ordenados no fim
            maxHeapify(A, 0, i);
        }
    }

    // método que constrói a max heap no array de produtos
    public static void buildMaxHeap(Produto[] A) {
        int n = A.length;
        // começa dos nós internos e aplica maxHeapify para formar a heap
        for (int i = (n / 2) - 1; i >= 0; i--) {
            maxHeapify(A, i, n);
        }
    }

    // método que garante a propriedade de max heap para o nó i, no array com tamanho n
    public static void maxHeapify(Produto[] A, int i, int n) {
        int largest = i;
        while (true) {
            // calcula o índice do filho esquerdo
            int left = 2 * i + 1;
            // calcula o índice do filho direito
            int right = 2 * i + 2;
            largest = i;

            // verifica se o filho esquerdo existe e é maior que o atual maior
            if (left < n && Controladores.compararValidade(A[left], A[largest]) > 0) {
                largest = left;
            }
            // verifica se o filho direito existe e é maior que o atual maior
            if (right < n && Controladores.compararValidade(A[right], A[largest]) > 0) {
                largest = right;
            }

            // se o maior não é o nó atual, troca os elementos e continua descendo no heap
            if (largest != i) {
                Produto temp = A[i];
                A[i] = A[largest];
                A[largest] = temp;
                i = largest;  // atualiza o índice para continuar o ajuste
            } else {
                break; // heap já está satisfeito, termina o loop
            }
        }
    }

}
