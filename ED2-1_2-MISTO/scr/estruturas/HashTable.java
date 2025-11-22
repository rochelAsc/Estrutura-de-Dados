package estruturas;

import java.util.LinkedList;

public class HashTable<T> {
    private LinkedList<Entry<T>>[] tabela;
    private int capacidade;
    private int tamanho;

    public static class Entry<T> {
        T chave;
        T valor;

        public Entry(T chave, T valor) {
            this.chave = chave;
            this.valor = valor;
        }

        public T getValor() {
            return valor;
        }

        public T getChave() {
            return chave;
        }
    }

    public LinkedList<Entry<T>>[] getTabela() {
        return tabela;
    }

    public void setTabela(LinkedList<Entry<T>>[] tabela) {
        this.tabela = tabela;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public HashTable(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        tabela = new LinkedList[capacidade];

        for (int i = 0; i < capacidade; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(T chave) {
        return (chave.hashCode() & Integer.MAX_VALUE) % capacidade;
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
                for (Entry<T> entry : tabela[i]) {
                    System.out.print("[" + entry.chave + "=" + entry.valor + "] ");
                }
                System.out.println();
            }
        }
    }



    public void chainedHashInsert(Entry<T> x) {
        int i = hash(x.chave);
        for (Entry<T> e : tabela[i]) {
            if (e.chave.equals(x.chave)) {
                return;
            }
        }
        tabela[i].addFirst(x);
        tamanho++;
    }

    public void chainedHashDelete(Entry<T> x) {
        int i = hash(x.chave);

        Entry<T> toRemove = null;
        for (Entry<T> e : tabela[i]) {
            if (e.chave.equals(x.chave)) {
                toRemove = e;
                break;
            }
        }
        if (toRemove != null) {
            tabela[i].remove(toRemove);
            tamanho--;
        }
    }



    public Entry<T> chainedHashSearch(T chave) {
        int i = hash(chave);
        for (Entry<T> e : tabela[i]) {
            if (e.chave.equals(chave)) {
                return e;
            }
        }
        return null;
    }
}
