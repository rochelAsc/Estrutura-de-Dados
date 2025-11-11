package model;

public class NodeProduto {
    public Produto data;
    public NodeProduto next;

    public NodeProduto(Produto data) {
        this.data = data;
        this.next = null;
    }
}

