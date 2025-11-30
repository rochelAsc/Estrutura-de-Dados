package estruturas;

import static java.lang.Math.max;
import java.util.*;

public class AVLTree<T extends Comparable<T>> {
    public class NoAVL<T> {
        T elemento;
        NoAVL<T> esquerda;
        NoAVL<T> direita;
        int altura;

        NoAVL(T e) {
            this(e, null, null);
        }

        NoAVL(T e, NoAVL esq, NoAVL dir) {
            elemento = e;
            esquerda = esq;
            direita = dir;
            altura = 0;
        }
    }

    private NoAVL<T> root;

    public AVLTree() {
        this.root = null;
    }

    // Método público para inserção
    public void insert(T x) {
        root = insert(x, root);
    }

    private NoAVL<T> insert(T x, NoAVL<T> t) {
        if (t == null) {
            t = new NoAVL<>(x);
        } else if (x.compareTo(t.elemento) < 0) {
            t.esquerda = insert(x, t.esquerda);
            if (getAltura(t.esquerda) - getAltura(t.direita) == 2) {
                if (x.compareTo(t.esquerda.elemento) < 0) {
                    t = rotacaoDireita(t);
                } else {
                    t = rotacaoDuplaDireita(t);
                }
            }
        } else if (x.compareTo(t.elemento) > 0) {
            t.direita = insert(x, t.direita);
            if (getAltura(t.direita) - getAltura(t.esquerda) == 2) {
                if (x.compareTo(t.direita.elemento) > 0) {
                    t = rotacaoEsquerda(t);
                } else {
                    t = rotacaoDuplaEsquerda(t);
                }
            }
        } else {
            // Elemento já existe, não faz nada
        }

        t.altura = max(getAltura(t.esquerda), getAltura(t.direita)) + 1;
        return t;
    }

    // Método para obter todos os elementos em ordem (do menor para o maior)
    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(NoAVL<T> node, List<T> result) {
        if (node != null) {
            inOrder(node.esquerda, result);
            result.add(node.elemento);
            inOrder(node.direita, result);
        }
    }

    // Método para obter todos os elementos em ordem reversa (do maior para o menor)
    public List<T> inOrderReverso() {
        List<T> result = new ArrayList<>();
        inOrderReverso(root, result);
        return result;
    }

    private void inOrderReverso(NoAVL<T> node, List<T> result) {
        if (node != null) {
            inOrderReverso(node.direita, result);
            result.add(node.elemento);
            inOrderReverso(node.esquerda, result);
        }
    }

    // Os métodos restantes permanecem iguais
    private NoAVL<T> rotacaoDireita(NoAVL<T> k2) {
        NoAVL<T> k1 = k2.esquerda;
        k2.esquerda = k1.direita;
        k1.direita = k2;
        k2.altura = max(getAltura(k2.esquerda), getAltura(k2.direita)) + 1;
        k1.altura = max(getAltura(k1.esquerda), k2.altura) + 1;
        return k1;
    }

    private NoAVL<T> rotacaoEsquerda(NoAVL<T> k1) {
        NoAVL<T> k2 = k1.direita;
        k1.direita = k2.esquerda;
        k2.esquerda = k1;
        k1.altura = max(getAltura(k1.esquerda), getAltura(k1.direita)) + 1;
        k2.altura = max(getAltura(k2.direita), k1.altura) + 1;
        return k2;
    }

    private NoAVL<T> rotacaoDuplaDireita(NoAVL<T> k3) {
        k3.esquerda = rotacaoEsquerda(k3.esquerda);
        return rotacaoDireita(k3);
    }

    private NoAVL<T> rotacaoDuplaEsquerda(NoAVL<T> k3) {
        k3.direita = rotacaoDireita(k3.direita);
        return rotacaoEsquerda(k3);
    }

    private int getAltura(NoAVL<T> node) {
        if (node == null) {
            return 0;
        }
        return node.altura;
    }
}