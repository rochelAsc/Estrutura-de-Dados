package view;

import service.Estatisticas;
import hash.HashTableHibrida;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RelatorioGenerator {

    /**
     * Gera um relatório de execução e o salva em arquivo.
     */
    public static void gerarRelatorio(
            HashTableHibrida tabela,
            int totalTransacoes,
            String caminhoArquivo
    ) {
        try (PrintWriter out = new PrintWriter(new FileWriter(caminhoArquivo))) {

            out.println("RELATÓRIO DE EXECUÇÃO");
            out.println("---------------------");
            out.println("Total de transações inseridas: " + totalTransacoes);
            out.println("Tamanho da tabela hash: " + tabela.getTamanhoTabela());
            out.println("Tempo total de execução (ms): " + String.format("%.3f", Estatisticas.getTempoMs()));
            out.println("Número de comparações: " + Estatisticas.getComparacoes());
            out.println("Número de atribuições: " + Estatisticas.getAtribuicoes());
            out.println("Total de árvores AVL: " + tabela.contarAVLs());
            out.println("Total de árvores Rubro-Negras: " + tabela.contarRBs());

            out.println();
            out.println("Observações:");
            out.println("- Registros com mais de 3 colisões em 'origem' migraram para AVL.");
            out.println("- AVLs com altura > 10 foram migradas para Rubro-Negras.");
            out.println("- Comparações e atribuições contabilizadas durante a inserção.");

            System.out.println("Relatório gerado com sucesso em: " + caminhoArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
        }
    }
}
