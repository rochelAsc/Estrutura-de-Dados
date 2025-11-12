package estruturas;

public class RBTree<AnyType extends Comparable<AnyType>> {
    public static final boolean VERMELHO = true;
    public static final boolean PRETO = false;
    public class NoRN<AnyType>{
        AnyType elemento;
        NoRN<AnyType> pai;
        NoRN<AnyType> esquerda;
        NoRN<AnyType> direita;
        boolean cor;
        int N;

        NoRN(AnyType e){
            this (e, null, null, false);
        }

        NoRN(AnyType e, NoRN esq, NoRN dir, boolean cor){
            elemento = e;
            esquerda = esq;
            direita = dir;
            this.cor = cor;
            pai = null;
            N = 1;
        }
    }

    private NoRN<AnyType> root;
    public RBTree(){this.root = null;}

    public void leftRotate(NoRN<AnyType> x) {
        NoRN<AnyType> y = x.direita; 
        x.direita = y.esquerda;

        if (y.esquerda != null) {
            y.esquerda.pai = x;
        }

        y.pai = x.pai;

        if (x.pai == null) {
            root = y;
        } else if (x == x.pai.esquerda) {
            x.pai.esquerda = y;
        } else {
            x.pai.direita = y;
        }

        y.esquerda = x;
        x.pai = y;
    }

    public void rightRotate(NoRN<AnyType> x) {
        NoRN<AnyType> y = x.esquerda;
        x.esquerda = y.direita;

        if (y.direita != null) {
            y.direita.pai = x;
        }

        y.pai = x.pai;

        if (x.pai == null) {
            root = y;
        } else if (x == x.pai.direita) {
            x.pai.direita = y;
        } else {
            x.pai.esquerda = y;
        }

        y.direita = x;
        x.pai = y;
    }


}
