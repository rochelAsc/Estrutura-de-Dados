package service;

import model.Produto;

public class ShellSort {
    // shellsort que escolhe entre selectionsort e insertionsort dependendo do valor de h
    public static Produto[] ShellSort(Produto[] A) {
        int h;
        int n = A.length;

        // calcula o valor inicial de h pela sequência de 3h + 1
        for (h = 1; h < n; h = 3 * h + 1);
        // ajusta h para o valor anterior da sequência
        h = (h - 1) / 3;

        // enquanto h for maior que zero
        while (h > 0) {
            if (h > 1) {
                // para cada sublista definida pelo salto/gap h
                for (int start = 0; start < h; start++) {
                    // calcula o tamanho da sublista
                    int tamanho = 0;
                    for (int k = start; k < n; k += h) { tamanho++; }

                    // cria array para armazenar a sublista
                    Produto[] sublista = new Produto[tamanho];
                    int index = 0;
                    // copia os elementos da sublista para o array
                    for (int k = start; k < n; k += h) {
                        sublista[index++] = A[k];
                    }

                    // ordena a sublista com selection sort
                    SelectionSort.SelectionSort(sublista);

                    // coloca a sublista ordenada de volta no vetor original
                    index = 0;
                    for (int k = start; k < n; k += h) {
                        A[k] = sublista[index++];
                    }
                }
            } else {
                // quando h é 1, aplica insertion sort para ordenar completamente
                InsertionSort.InsertionSort(A);
            }

            // reduz h para o próximo valor da sequência
            h = h / 3;
        }

        // retorna o array ordenado
        return A;
    }

}
