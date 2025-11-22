package algoritmos;

import java.util.*;
import estruturas.HashTable;
import estruturas.HashTable.Entry;
import dados.entrada.Commit;

public class HeapSortStable {

    public static List<Commit> heapSortEstavel(List<Commit> commits) {

        // ==========================================
        // 1. AGRUPAR COMMITS POR TIMESTAMP (estável)
        // ==========================================
        HashTable<Object> tabela = new HashTable<>(commits.size() * 2);

        for (Commit c : commits) {
            Object chave = c.getTimestamp(); // Date

            Entry<Object> bucket = tabela.chainedHashSearch(chave);

            if (bucket == null) {
                LinkedList<Commit> lista = new LinkedList<>();
                lista.add(c);
                tabela.chainedHashInsert(new Entry<>(chave, lista));
            } else {
                @SuppressWarnings("unchecked")
                LinkedList<Commit> lista = (LinkedList<Commit>) bucket.getValor();
                lista.add(c);
            }
        }

        // ==========================================
        // 2. Extrair todas as chaves de timestamp
        // ==========================================
        List<Object> timestamps = new ArrayList<>();

        for (int i = 0; i < tabela.getCapacidade(); i++) {
            for (Entry<Object> entry : tabela.getTabela()[i]) {
                timestamps.add(entry.getChave());
            }
        }

        // ==========================================
        // 3. ORDENAR timestamps por Date.getTime()
        // ==========================================
        int n = timestamps.size();
        long[] times = new long[n];

        for (int i = 0; i < n; i++) {
            Date ts = (Date) timestamps.get(i);
            times[i] = ts.getTime();
        }

        heapSortLong(times, n);

        // reconstruir timestamps ordenados
        List<Object> ordenados = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            long value = times[i];

            for (Object chave : timestamps) {
                Date ts = (Date) chave;

                if (ts.getTime() == value && !ordenados.contains(chave)) {
                    ordenados.add(chave);
                    break;
                }
            }
        }

        // ==========================================
        // 4. Reconstruir lista final estável
        // ==========================================
        List<Commit> resultado = new ArrayList<>();

        for (Object chave : ordenados) {
            Entry<Object> bucket = tabela.chainedHashSearch(chave);

            @SuppressWarnings("unchecked")
            LinkedList<Commit> lista = (LinkedList<Commit>) bucket.getValor();

            resultado.addAll(lista);
        }

        return resultado;
    }

    // ===================================================
    //  HeapSort para long[] (0-based, totalmente seguro)
    // ===================================================
    private static void heapSortLong(long[] A, int n) {
        buildMaxHeap(A, n);

        for (int i = n - 1; i > 0; i--) {
            swap(A, 0, i);
            maxHeapify(A, 0, i);
        }
    }

    private static void buildMaxHeap(long[] A, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            maxHeapify(A, i, n);
        }
    }

    private static void maxHeapify(long[] A, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        int largest = i;

        if (left < n && A[left] > A[largest])
            largest = left;

        if (right < n && A[right] > A[largest])
            largest = right;

        if (largest != i) {
            swap(A, i, largest);
            maxHeapify(A, largest, n);
        }
    }

    private static void swap(long[] A, int i, int j) {
        long temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}
