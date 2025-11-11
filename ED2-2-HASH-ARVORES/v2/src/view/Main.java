package view;

import hash.HashTableHibrida;
import model.Transacao;
import service.TransacaoParser;
import service.Estatisticas;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String caminho = "transacoes.csv"; // ajuste conforme necessário
        List<Transacao> transacoes = TransacaoParser.carregarTransacoes(caminho);

        System.out.println("Total de transações carregadas: " + transacoes.size());

        HashTableHibrida hash = new HashTableHibrida(10007); // número primo

        Estatisticas.resetar();
        Estatisticas.iniciarTempo();

        for (Transacao t : transacoes) {
            hash.inserir(t);
        }

        Estatisticas.finalizarTempo();
        Estatisticas.imprimirResumo();

        System.out.println("AVLs geradas: " + hash.contarAVLs());
        System.out.println("Rubro-Negras geradas: " + hash.contarRBs());

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nBuscar transações de qual origem? ");
        String origem = scanner.nextLine();

        List<Transacao> resultado = hash.buscarPorOrigem(origem);
        System.out.println("Transações encontradas: " + resultado.size());
        for (Transacao t : resultado) {
            System.out.println(t);
        }

        System.out.print("\nInforme data inicial (yyyy-MM-dd): ");
        String ini = scanner.nextLine();
        System.out.print("Informe data final (yyyy-MM-dd): ");
        String fim = scanner.nextLine();

        List<Transacao> intervalo = hash.buscarPorOrigemEIntervalo(origem, ini, fim);
        System.out.println("Transações no intervalo: " + intervalo.size());
        for (Transacao t : intervalo) {
            System.out.println(t);
        }

        scanner.close();

        // 📝 Gera o relatório final após todas as inserções e buscas
        RelatorioGenerator.gerarRelatorio(hash, transacoes.size(), "relatorio_execucao.txt");
    }
}
