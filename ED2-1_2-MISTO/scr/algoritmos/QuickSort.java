package algoritmos;

public class QuickSort {
    public static void QuickSort(int [] vetor, int inicio, int fim) {
        if (inicio < fim){
            int posicaoPivo = particiona(vetor, inicio, fim);

            QuickSort(vetor, inicio, posicaoPivo - 1);
            QuickSort(vetor, posicaoPivo + 1, fim);
        }
    }

    private static int particiona(int[] vetor, int inicio, int fim) {
        int pivo = vetor[inicio];
        int i = inicio +1, f = fim;

        System.out.println("---------------------");
        while (i <= f) {
            if (vetor[i] <= pivo){
                i++;
            }
            else if (pivo < vetor[f]){
                f--;
            }
            else {
                int troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
    }
}
