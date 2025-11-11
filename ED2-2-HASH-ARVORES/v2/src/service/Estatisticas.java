package service;

public class Estatisticas {

    private static int comparacoes = 0;
    private static int atribuicoes = 0;
    private static long tempoInicio = 0;
    private static long tempoFim = 0;

    // ---------------------
    // Tempo
    // ---------------------
    public static void iniciarTempo() {
        tempoInicio = System.nanoTime();
    }

    public static void finalizarTempo() {
        tempoFim = System.nanoTime();
    }

    public static double getTempoMs() {
        return (tempoFim - tempoInicio) / 1_000_000.0;
    }

    // ---------------------
    // Contadores
    // ---------------------
    public static void incrementarComparacoes() {
        comparacoes++;
    }

    public static void incrementarAtribuicoes() {
        atribuicoes++;
    }

    public static int getComparacoes() {
        return comparacoes;
    }

    public static int getAtribuicoes() {
        return atribuicoes;
    }

    public static void resetar() {
        comparacoes = 0;
        atribuicoes = 0;
        tempoInicio = 0;
        tempoFim = 0;
    }

    public static void imprimirResumo() {
        System.out.println("----- Estatísticas -----");
        System.out.println("Comparações: " + comparacoes);
        System.out.println("Atribuições: " + atribuicoes);
        System.out.printf("Tempo total: %.3f ms%n", getTempoMs());
    }
}
