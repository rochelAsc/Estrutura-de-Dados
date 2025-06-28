package estruturas;

import model.Transacao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação básica de Árvore AVL de Transações, balanceada por timestamp.
 */
public class TreeAVL implements Arvore {
    private class Node {
        Transacao transacao;
        Node left;
        Node right;
        int height;

        Node(Transacao transacao) {
            this.transacao = transacao;
            this.height = 1;
        }
    }

    private Node root;
    private int size;

    @Override
    public void inserir(Transacao transacao) {
        root = inserirRec(root, transacao);
    }

    // Insere e reequilibra a subárvore
    private Node inserirRec(Node node, Transacao t) {
        if (node == null) {
            size++;
            return new Node(t);
        }
        if (t.getTimestamp().isBefore(node.transacao.getTimestamp())) {
            node.left = inserirRec(node.left, t);
        } else {
            node.right = inserirRec(node.right, t);
        }
        // Atualiza altura e realiza rotações se necessário
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);
        // Caso LL
        if (balance > 1 && t.getTimestamp().isBefore(node.left.transacao.getTimestamp())) {
            return rotateRight(node);
        }
        // Caso RR
        if (balance < -1 && !t.getTimestamp().isBefore(node.right.transacao.getTimestamp())) {
            return rotateLeft(node);
        }
        // Caso LR
        if (balance > 1 && !t.getTimestamp().isBefore(node.left.transacao.getTimestamp())) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // Caso RL
        if (balance < -1 && t.getTimestamp().isBefore(node.right.transacao.getTimestamp())) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
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

    @Override
    public int tamanho() {
        return size;
    }

    // Helpers:
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
}
