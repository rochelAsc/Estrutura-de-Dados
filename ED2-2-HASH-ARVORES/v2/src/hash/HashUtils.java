package hash;

public class HashUtils {

    /**
     * Calcula um valor de hash não negativo a partir de uma string.
     * Pode ser usado tanto para "id" quanto para "origem".
     */
    public static int hash(String chave, int tamanhoTabela) {
        int hash = 0;
        for (int i = 0; i < chave.length(); i++) {
            hash = (31 * hash + chave.charAt(i)) % tamanhoTabela;
        }
        return Math.abs(hash);
    }

    /**
     * Função de sondagem quadrática:
     * Retorna o novo índice baseado em h (hash original), i (tentativa), e m (tamanho).
     */
    public static int sondagemQuadratica(int h, int i, int tamanhoTabela) {
        return (h + i * i) % tamanhoTabela;
    }

    /**
     * Retorna true se o número é primo.
     */
    public static boolean isPrimo(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        return true;
    }

    /**
     * Retorna o próximo número primo >= n.
     */
    public static int proximoPrimo(int n) {
        while (!isPrimo(n)) n++;
        return n;
    }
}
