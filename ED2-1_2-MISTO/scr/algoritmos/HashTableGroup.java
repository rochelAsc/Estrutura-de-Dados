package algoritmos;

import java.util.*;
import estruturas.HashTable;
import estruturas.HashTable.Entry;
import dados.entrada.Commit;

public class HashTableGroup {

    public static HashTable<Object> agruparCommitsPorTimestamp(List<Commit> commits) {
        HashTable<Object> tabela = new HashTable<>(commits.size() * 2);

        for (Commit c : commits) {
            Object chave = c.getTimestamp();

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

        return tabela;
    }

    public static List<Object> extrairTimestamps(HashTable<Object> tabela) {
        List<Object> timestamps = new ArrayList<>();

        for (int i = 0; i < tabela.getCapacidade(); i++) {
            for (Entry<Object> entry : tabela.getTabela()[i]) {
                timestamps.add(entry.getChave());
            }
        }

        return timestamps;
    }
}