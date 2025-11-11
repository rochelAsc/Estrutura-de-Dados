package service;

import model.*;
import service.*;

public class QuickSort {

    // método quicksort para ordenar o array de produtos pelo preço
    public static void QuickSort(Produto[] vetor, int inicio, int fim) {
        // condição base para parar a recursão
        if (inicio < fim) {
            // particiona o array e obtém a posição do pivô
            int posPivo = particiona(vetor, inicio, fim);
            // ordena recursivamente a parte à esquerda do pivô
            QuickSort(vetor, inicio, posPivo - 1);
            // ordena recursivamente a parte à direita do pivô
            QuickSort(vetor, posPivo + 1, fim);
        }
    }

    // método particiona que reorganiza o array em torno do pivô
    private static int particiona(Produto[] vetor, int inicio, int fim) {
        // escolhe o pivô usando a mediana de três preços
        int indicePivo = Controladores.MediaPivo(vetor, inicio, fim);
        Produto pivo = vetor[indicePivo];

        // coloca o pivô no início do subarray (posição inicio)
        Produto temp = vetor[inicio];
        vetor[inicio] = vetor[indicePivo];
        vetor[indicePivo] = temp;

        // cursor para marcar onde serão colocados elementos menores que o pivô
        int cursor = inicio + 1;

        // percorre o subarray do cursor até o fim
        for (int i = cursor; i <= fim; i++) {
            // se o preço do elemento atual for menor que o do pivô
            if (vetor[i].getPreco() < pivo.getPreco()) {
                // troca o elemento atual com o elemento na posição do cursor
                Produto aux = vetor[i];
                vetor[i] = vetor[cursor];
                vetor[cursor] = aux;
                cursor++; // avança o cursor
            }
        }

        // coloca o pivô na sua posição correta (antes do cursor)
        vetor[inicio] = vetor[cursor - 1];
        vetor[cursor - 1] = pivo;

        // retorna a posição final do pivô no array
        return cursor - 1;
    }

}
