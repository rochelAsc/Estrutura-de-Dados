package service;
import model.Produto;

public class SelectionSort {

    // método selection sort para ordenar o array de produtos pelo número no nome
    public static void SelectionSort(Produto[] A) {
        int i, j, min;
        Produto temp;
        int n = A.length;

        // percorre o array até o penúltimo elemento
        for (i = 0; i < n - 1; i++) {
            min = i; // assume que o menor elemento está na posição i
            // procura pelo menor elemento no resto do array
            for (j = i + 1; j < n; j++) {
                // compara os números extraídos do nome dos produtos
                if (Controladores.extrairNumeroNome(A[j]) < Controladores.extrairNumeroNome(A[min])) {
                    min = j; // atualiza o índice do menor elemento
                }
            }
            // troca o menor elemento encontrado com o elemento da posição i
            temp = A[min];
            A[min] = A[i];
            A[i] = temp;
        }
    }

}
