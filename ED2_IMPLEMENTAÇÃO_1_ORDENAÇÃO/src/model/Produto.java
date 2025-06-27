package model;

public class Produto {
    private String nome;
    private String fabricante;
    private double preco;
    private String validade;
    private String tipo;

    public Produto(String nome, String fabricante, double preco, String validade, String tipo) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
        this.validade = validade;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome + ", " + fabricante + ", " + preco + ", " + validade;
    }
}
