import java.util.List;
import java.util.Scanner;
import model.Produto;
import service.*;


public class Main {
    public static void main(String[] args) {
        String caminhoArquivo = "produtos_10000.txt";

        // 1. Ler produtos do arquivo
        List<Produto> listaProdutos = LerProdutos.lerProdutosDeArquivo(caminhoArquivo);

        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto foi carregado.");
            return;
        }

        // 2. menu
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o criterio de ordenacao:");
        System.out.println("1 - Ordenar por nome (ShellSort modificado)");
        System.out.println("2 - Ordenar por preco (QuickSort modificado)");
        System.out.println("3 - Ordenar por validade (HeapSort modificado)");
        System.out.println("4 - Ordenar por nome (Insertion Sort com lista encadeada)");
        System.out.print("Digite sua opcao: ");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                Produto[] produtosShell = listaProdutos.toArray(new Produto[0]);
                produtosShell = ShellSort.ShellSort(produtosShell);
                Controladores.SalvarDados(produtosShell, "ShellOrdenado" + caminhoArquivo);
                System.out.println("\nProdutos ordenados por nome (ShellSort):");
                for (Produto p : produtosShell) {
                    System.out.println(p);
                }
                break;

            case 2:
                Produto[] produtosQuick = listaProdutos.toArray(new Produto[0]);
                QuickSort.QuickSort(produtosQuick, 0, produtosQuick.length - 1);
                Controladores.SalvarDados(produtosQuick, "QuickOrdenado" + caminhoArquivo);
                System.out.println("\nProdutos ordenados por preco (QuickSort):");
                for (Produto p : produtosQuick) {
                    System.out.println(p);
                }
                break;

            case 3:
                Produto[] produtosHeap = listaProdutos.toArray(new Produto[0]);
                HeapSort.HeapSort(produtosHeap);
                Controladores.SalvarDados(produtosHeap, "HeapOrdenado" + caminhoArquivo);
                System.out.println("\nProdutos ordenados por validade (HeapSort):");
                for (Produto p : produtosHeap) {
                    System.out.println(p);
                }
                break;

            case 4:
                ListaEncadeadaProduto listaEncadeada = new ListaEncadeadaProduto();
                for (Produto p : listaProdutos) {
                    listaEncadeada.inserir(p);
                }
                listaEncadeada.LinkedListInsertionSort();
                Controladores.SalvarDadosLinkedList(listaEncadeada, "ListaEncadeadaOrdenada" + caminhoArquivo);
                System.out.println("\nProdutos ordenados por nome (InsertionSort em Lista Encadeada):");
                listaEncadeada.imprimir();
                break;

            default:
                System.out.println("Opcao invalida.");
        }

        scanner.close();
    }
}
