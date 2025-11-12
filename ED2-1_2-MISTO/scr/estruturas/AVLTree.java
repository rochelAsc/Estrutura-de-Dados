package estruturas;

import static java.lang.Math.max;

public class AVLTree<AnyType extends Comparable<AnyType>> {
    public class NoAVL <AnyType> {
        AnyType elemento;
        NoAVL<AnyType> esquerda;
        NoAVL<AnyType> direita;
        int altura;

        NoAVL(AnyType e){
            this (e, null, null);
        }

        NoAVL(AnyType e, NoAVL esq, NoAVL dir){
            elemento = e;
            esquerda = esq;
            direita = dir;
            altura = 0;
        }

    }

    private NoAVL<AnyType> root;

    public AVLTree(){
        this.root = null;
    }


    private NoAVL<AnyType> rotacaoDireita (NoAVL<AnyType> k2){
        NoAVL<AnyType> k1 = k2.esquerda;
        k2.esquerda = k1.direita;
        k1.direita = k2;
        k2.altura = max( getAltura( k2.esquerda), getAltura( k2.direita)) + 1;
        k1.altura = max( getAltura( k1.esquerda), k2.altura) + 1;
        return k1;
    }

    private NoAVL<AnyType> rotacaoEsquerda (NoAVL<AnyType> k1){
        NoAVL<AnyType> k2 = k1.direita;
        k1.direita = k2.esquerda;
        k2.esquerda = k1;
        k1.altura = max( getAltura( k1.esquerda), getAltura( k1.direita)) + 1;
        k2.altura = max( getAltura( k2.direita), k1.altura) + 1;
        return k2;
    }

    private NoAVL<AnyType> rotacaoDuplaDireita(NoAVL<AnyType> k3){
        k3.esquerda = rotacaoEsquerda(k3.esquerda);
        return rotacaoDireita(k3);
    }

    private NoAVL<AnyType> rotacaoDuplaEsquerda(NoAVL<AnyType> k3){
        k3.direita = rotacaoDireita(k3.direita);
        return rotacaoEsquerda(k3);
    }

    private NoAVL insert(AnyType x, NoAVL t)
    {
        if (t == null){
            t = new NoAVL(x);
        }
        else if(x.compareTo((AnyType) t.elemento) < 0){
            t.esquerda = insert(x, t.esquerda);
            if(getAltura(t.esquerda) - getAltura(t.direita) == 2){
                if(x.compareTo((AnyType) t.esquerda.elemento) < 0){
                    t = rotacaoEsquerda(t);
                }
                else{
                    t = rotacaoDuplaEsquerda(t);
                }
            }
        }
        else if(x.compareTo((AnyType) t.elemento) > 0){
            t.direita = insert(x, t.direita);
            if(getAltura(t.direita) - getAltura(t.esquerda) == 2){
                if(x.compareTo((AnyType) t.direita.elemento) > 0){
                    t = rotacaoDireita(t);
                }
                else{
                    t = rotacaoDuplaDireita(t);
                }
            }
        }
        else{;}
        
            t.altura = max( getAltura(t.esquerda), getAltura(t.direita)) + 1;
            return t;
    }

    private int getAltura (NoAVL <AnyType> node){
        if (node == null){
            return 0;
        }
        return node.altura;
    }
}
