package model;

import java.util.ArrayList;
import java.util.List;

public class AVLNode {
    public String chave;  // No nosso caso, a "origem"
    public List<Transacao> transacoes;

    public AVLNode esquerda;
    public AVLNode direita;
    public int altura;

    public AVLNode(String chave, Transacao transacao) {
        this.chave = chave;
        this.transacoes = new ArrayList<>();
        this.transacoes.add(transacao);
        this.altura = 1; // Altura inicial de um novo nó é 1
        this.esquerda = null;
        this.direita = null;
    }

    public void adicionarTransacao(Transacao t) {
        this.transacoes.add(t);
    }
}
