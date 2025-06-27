package service;
import model.*;

public class ListaEncadeadaProduto {
    public NodeProduto head; // referência para o início da lista encadeada

    // construtor que inicializa a lista vazia (head = null)
    public ListaEncadeadaProduto() {
        this.head = null; // inicializa a lista encadeada vazia
    }

    // método para inserir um produto no início da lista
    public void inserir(Produto p) {
        NodeProduto novo = new NodeProduto(p); // cria um novo nó com o produto
        novo.next = head; // novo nó aponta para o atual primeiro nó
        head = novo; // atualiza o head para o novo nó
    }

    // insertion sort para ordenar a lista encadeada pelo número extraído do nome do produto
    public void LinkedListInsertionSort() {
        NodeProduto sorted = null; // nova lista ordenada começa vazia
        NodeProduto cur = head; // ponteiro para o nó cur na lista original

        // percorre toda a lista original
        while (cur != null) {
            NodeProduto proximo = cur.next; // salva o próximo nó
            // insere o nó cur na lista ordenada na posição correta
            sorted = inserirOrdenado(sorted, cur);
            cur = proximo; // avança para o próximo nó
        }

        head = sorted; // atualiza o head para a lista ordenada
    }

    // método auxiliar para inserir um nó na lista ordenada pelo número no nome do produto
    public NodeProduto inserirOrdenado(NodeProduto inicio, NodeProduto newNode) {
        // insere no início se lista vazia ou número do newNode menor que o do início
        if (inicio == null || Controladores.extrairNumeroNome(newNode.data) < Controladores.extrairNumeroNome(inicio.data)) {
            newNode.next = inicio;
            return newNode;
        }

        NodeProduto atual = inicio;
        // percorre até encontrar a posição correta para inserir o newNode
        while (atual.next != null && Controladores.extrairNumeroNome(atual.next.data) < Controladores.extrairNumeroNome(newNode.data)) {
            atual = atual.next;
        }

        // insere o newNode na posição encontrada
        newNode.next = atual.next;
        atual.next = newNode;

        return inicio; // retorna o início da lista
    }

    // método para imprimir os produtos da lista encadeada
    public void imprimir() {
        NodeProduto atual = head; // começa do início da lista
        while (atual != null) {
            System.out.println(atual.data); // imprime o produto atual
            atual = atual.next; // avança para o próximo nó
        }
    }

}
