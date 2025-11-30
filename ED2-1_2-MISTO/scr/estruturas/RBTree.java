package estruturas;

public class RBTree<T extends Comparable<T>> {
    public static final boolean VERMELHO = true;
    public static final boolean PRETO = false;
    public class NoRN<T>{
        T elemento;
        NoRN<T> pai;
        NoRN<T> esquerda;
        NoRN<T> direita;
        boolean cor;
        int N;

        NoRN(T e){
            this (e, null, null, false);
        }

        NoRN(T e, NoRN esq, NoRN dir, boolean cor){
            elemento = e;
            esquerda = esq;
            direita = dir;
            this.cor = cor;
            pai = null;
            N = 1;
        }
    }

    private NoRN<T> root;
    public RBTree(){this.root = null;}

    public void leftRotate(NoRN<T> x) {
        NoRN<T> y = x.direita;
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

    public void rightRotate(NoRN<T> x) {
        NoRN<T> y = x.esquerda;
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

    public void rbInsert(NoRN<T> z) {
        NoRN<T> y = null;
        NoRN<T> x = root;

        while (x != null) {
            y = x;
            if (z.elemento.compareTo(x.elemento) < 0) {
                x = x.esquerda;
            } else {
                x = x.direita;
            }
        }

        z.pai = y;

        if (y == null) {
            root = z;
        } else if (z.elemento.compareTo(y.elemento) < 0) {
            y.esquerda = z;
        } else {
            y.direita = z;
        }

        z.esquerda = null;
        z.direita = null;

        z.cor = VERMELHO;

        rbInsertFixup(z);
    }

    public void rbInsertFixup(NoRN<T> z) {
        while (z.pai != null && z.pai.cor == VERMELHO) {
            if (z.pai == z.pai.pai.esquerda) {
                NoRN<T> y = z.pai.pai.direita;
                if (y != null && y.cor == VERMELHO) {
                    z.pai.cor = PRETO;
                    y.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        leftRotate(z);
                    }
                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    rightRotate(z.pai.pai);
                }
            } else {
                NoRN<T> y = z.pai.pai.esquerda;
                if (y != null && y.cor == VERMELHO) {
                    z.pai.cor = PRETO;
                    y.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rightRotate(z);
                    }
                    z.pai.cor = PRETO;
                    z.pai.pai.cor = VERMELHO;
                    leftRotate(z.pai.pai);
                }
            }
        }

        root.cor = PRETO;
    }

    public NoRN<T> rbDelete(NoRN<T> z) {
        NoRN<T> y;
        NoRN<T> x;

        if (z.esquerda == null || z.direita == null) {
            y = z;
        } else {
            y = treeSuccessor(z);
        }

        if (y.esquerda != null) {
            x = y.esquerda;
        } else {
            x = y.direita;
        }

        if (x != null) {
            x.pai = y.pai;
        }

        if (y.pai == null) {
            root = x;
        } else if (y == y.pai.esquerda) {
            y.pai.esquerda = x;
        } else {
            y.pai.direita = x;
        }

        if (y != z) {
            z.elemento = y.elemento;
        }

        if (y.cor == PRETO) {
            rbDeleteFixup(x);
        }

        return y;
    }

    public NoRN<T> treeSuccessor(NoRN<T> z) {
        if (z.direita != null) {
            return treeMin(z.direita);
        }

        NoRN<T> y = z.pai;
        while (y != null && z == y.direita) {
            z = y;
            y = y.pai;
        }
        return y;
    }

    public NoRN<T> treeMin(NoRN<T> x) {
        while (x.esquerda != null) {
            x = x.esquerda;
        }
        return x;
    }

    public void rbDeleteFixup(NoRN<T> x) {
        while (x != root && x.cor == PRETO) {  
            if (x == x.pai.esquerda) {
                NoRN<T> w = x.pai.direita;

                if (w.cor == VERMELHO) {
                    w.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    leftRotate(x.pai);
                    w = x.pai.direita;
                }

                if (w.esquerda.cor == PRETO && w.direita.cor == PRETO) {
                    w.cor = VERMELHO;
                    x = x.pai;
                } else {
                    if (w.direita.cor == PRETO) {
                        w.esquerda.cor = PRETO;
                        w.cor = VERMELHO;
                        rightRotate(w);
                        w = x.pai.direita;
                    }

                    w.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    w.direita.cor = PRETO;
                    leftRotate(x.pai);
                    x = root;
                }
            } else {
                NoRN<T> w = x.pai.esquerda;

                if (w.cor == VERMELHO) {
                    w.cor = PRETO;
                    x.pai.cor = VERMELHO;
                    rightRotate(x.pai);
                    w = x.pai.esquerda;
                }

                if (w.direita.cor == PRETO && w.esquerda.cor == PRETO) {
                    w.cor = VERMELHO;
                    x = x.pai;
                } else {
                    if (w.esquerda.cor == PRETO) {
                        w.direita.cor = PRETO;
                        w.cor = VERMELHO;
                        leftRotate(w);
                        w = x.pai.esquerda;
                    }

                    w.cor = x.pai.cor;
                    x.pai.cor = PRETO;
                    w.esquerda.cor = PRETO;
                    rightRotate(x.pai);
                    x = root;
                }
            }
        }

        x.cor = PRETO;
    }







}
