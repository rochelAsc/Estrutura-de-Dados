package model;

import java.util.ArrayList;
import java.util.List;

public class RBNode {
    public String chave;  // Origem
    public List<Transacao> transacoes;

    public RBNode esquerda;
    public RBNode direita;
    public RBNode pai;

    public boolean cor; // true = vermelho, false = preto

    public RBNode(String chave, Transacao transacao) {
        this.chave = chave;
        this.transacoes = new ArrayList<>();
        this.transacoes.add(transacao);
        this.cor = true; // Nós novos são vermelhos por padrão
        this.esquerda = null;
        this.direita = null;
        this.pai = null;
    }

    public void adicionarTransacao(Transacao t) {
        this.transacoes.add(t);
    }
}
