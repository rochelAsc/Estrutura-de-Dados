package estruturas;

import model.Transacao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação básica de Árvore Rubro-Negra de Transações, ordenada por timestamp.
 * Esta versão inicial insere seguindo BST e marca o nó como vermelho.
 * As rotações e recolorizações para balanceamento devem ser implementadas.
 */
public class TreeRB implements Arvore {
    private enum Cor { VERMELHO, PRETO }

    private class Node {
        Transacao transacao;
        Node left, right, parent;
        Cor cor;

        Node(Transacao t) {
            this.transacao = t;
            this.cor = Cor.VERMELHO; // Novo nó sempre vermelho inicialmente
        }
    }

    private Node root;
    private int size;

    @Override
    public void inserir(Transacao transacao) {
        Node novo = new Node(transacao);
        bstInsert(novo);
        fixViolations(novo);
        size++;
    }

    /**
     * Inserção padrão de BST por chave timestamp.
     */
    private void bstInsert(Node node) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (node.transacao.getTimestamp().isBefore(x.transacao.getTimestamp())) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.transacao.getTimestamp().isBefore(y.transacao.getTimestamp())) {
            y.left = node;
        } else {
            y.right = node;
        }
    }

    /**
     * Corrige violações das propriedades Rubro-Negra após inserção.
     * TODO: implementar rotações (leftRotate, rightRotate) e recolorizações.
     */
    private void fixViolations(Node node) {
        // Exemplo de stub, sem balanceamento real
        // Enquanto o pai for vermelho, aplicar rotações e recolorir
        // ...
    }

    @Override
    public List<Transacao> buscarIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        List<Transacao> resultado = new ArrayList<>();
        buscarRec(root, inicio, fim, resultado);
        return resultado;
    }

    private void buscarRec(Node node, LocalDateTime inicio, LocalDateTime fim, List<Transacao> lista) {
        if (node == null) return;
        if (node.transacao.getTimestamp().isAfter(inicio)) {
            buscarRec(node.left, inicio, fim, lista);
        }
        if (!node.transacao.getTimestamp().isBefore(inicio)
                && !node.transacao.getTimestamp().isAfter(fim)) {
            lista.add(node.transacao);
        }
        if (node.transacao.getTimestamp().isBefore(fim)) {
            buscarRec(node.right, inicio, fim, lista);
        }
    }

    @Override
    public int altura() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    @Override
    public int tamanho() {
        return size;
    }
}
