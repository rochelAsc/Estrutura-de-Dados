package estruturas;

import java.util.LinkedList;

public class HashTable<AnyType> {
    private LinkedList<Entry<AnyType>>[] tabela;  
    private int capacidade;
    private int tamanho;

    public static class Entry<AnyType> {
        AnyType chave;
        AnyType valor;

        public Entry(AnyType chave, AnyType valor) {
            this.chave = chave;
            this.valor = valor;
        }
    }

    public HashTable(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        tabela = new LinkedList[capacidade];

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(AnyType chave) {
        return Math.abs(chave.hashCode()) % capacidade;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public int size() {
        return tamanho;
    }

    public void printTable() {
        for (int i = 0; i < capacidade; i++) {
            if (!tabela[i].isEmpty()) {
                System.out.print("Índice " + i + ": ");
                for (Entry<AnyType> entry : tabela[i]) {
                    System.out.print("[" + entry.chave + "=" + entry.valor + "] ");
                }
                System.out.println();
            }
        }
    }


    public void chainedHashInsert(Entry<AnyType> x) {
        int i = hash(x.chave);
        tabela[i].addFirst(x);
        tamanho++;
    }

    public void chainedHashDelete(Entry<AnyType> x) {
        int i = hash(x.chave);
        boolean removed = tabela[i].remove(x);
        if (removed) {
            tamanho--;
        }
    }

    public Entry<AnyType> chainedHashSearch(AnyType chave) {
        int i = hash(chave);
        for (Entry<AnyType> e : tabela[i]) {
            if (e.chave.equals(chave)) {
                return e;
            }
        }
        return null;
    }
}
