package service;
import model.*;

public class InsertionSort {

    // método de insertion sort aplicado ao array de produtos
    public static void InsertionSort(Produto[] A) {
        int i, j;
        Produto chave;
        int n = A.length;

        // percorre o array a partir do segundo elemento
        for (j = 1; j < n; j++) {
            // guarda o elemento atual na chave
            chave = A[j];
            // começa a comparar com o elemento anterior
            i = j - 1;
            // enquanto o índice for válido e o número extraído do nome for maior que o da chave
            while (i >= 0 && Controladores.extrairNumeroNome(A[i]) > Controladores.extrairNumeroNome(chave)) {
                // move o elemento maior uma posição para frente
                A[i + 1] = A[i];
                i--;
            }
            // insere a chave na posição correta
            A[i + 1] = chave;
        }
    }

}
