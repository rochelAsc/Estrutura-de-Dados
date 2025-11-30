import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import algoritmos.HeapSortStable;
import algoritmos.HashTableGroup;
import estruturas.HashTable;
import estruturas.HashTable.Entry;
import dados.entrada.Commit;
import algoritmos.AVLTreeGroup;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // ============================
            // 1. LER ARQUIVO JSON
            // ============================
            String caminho = "C:\\Users\\Rochel\\Documents\\GitHub\\Estrutura-de-Dados\\ED2-1_2-MISTO\\scr\\dados\\saida\\commits_1000.json";
            FileReader reader = new FileReader(caminho);

            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            List<Commit> commits = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // ============================
            // 2. PARSEAR CADA OBJETO JSON
            // ============================
            for (JsonElement elem : jsonArray) {
                JsonObject obj = elem.getAsJsonObject();

                String hash = obj.get("hash").getAsString();
                String autor = obj.get("autor").getAsString();
                String mensagem = obj.get("mensagem").getAsString();
                String timestampStr = obj.get("timestamp").getAsString();
                int ordem = obj.get("ordem_original").getAsInt();

                Date timestamp = sdf.parse(timestampStr);

                Commit c = new Commit(hash, autor, mensagem, timestamp, ordem);
                commits.add(c);
            }

            System.out.println("===== LISTA ORIGINAL (ORDEM DO JSON) =====");
            for (Commit c : commits) {
                System.out.println(
                        c.getHash() + "  ts=" + c.getTimestamp() + "  ordem=" + c.getOrdemOriginal()
                );
            }




            // ============================
            // 3. MENU DE ESTRUTURA DE ARMAZENAMENTO
            // ============================
            Object estruturaArmazenamento = null;
            String tipoEstrutura = "";

            System.out.println("===== ESCOLHA A ESTRUTURA DE ARMAZENAMENTO =====");
            System.out.println("1 - HashTable");
            System.out.println("2 - AVL Tree");
            System.out.println("3 - RB Tree");
            System.out.print("Escolha uma opção: ");

            int opcaoEstrutura = scanner.nextInt();

            switch (opcaoEstrutura) {
                case 1:
                    System.out.println("\n=== ARMAZENANDO EM HASHTABLE ===");
                    estruturaArmazenamento = HashTableGroup.agruparCommitsPorTimestamp(commits);
                    tipoEstrutura = "HashTable";


                    break;
                case 2:
                    System.out.println("\n=== ARMAZENANDO EM AVL TREE ===");
                    AVLTreeGroup avlGroup = new AVLTreeGroup();
                    avlGroup.agruparCommits(commits);
                    estruturaArmazenamento = avlGroup;
                    tipoEstrutura = "AVLTree";
                    break;

                case 3:
                    System.out.println("\n=== ARMAZENANDO EM RB TREE ===");
                    // Aqui você chamará seu método para RB Tree
                    // estruturaArmazenamento = RBTreeGroup.agruparCommitsPorTimestamp(commits);
                    tipoEstrutura = "RBTree";
                    System.out.println("RB Tree - Implementação pendente");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    return;
            }

            // ============================
            // 4. MENU DE ALGORITMO DE ORDENAÇÃO
            // ============================
            List<Commit> ordenados = null;

            System.out.println("\n===== ESCOLHA O ALGORITMO DE ORDENAÇÃO =====");
            System.out.println("1 - HeapSort");
            System.out.println("2 - QuickSort");
            System.out.println("3 - SelectionSort");
            System.out.print("Escolha uma opção: ");

            int opcaoOrdenacao = scanner.nextInt();

            switch (opcaoOrdenacao) {
                // No menu de ordenação, caso 1 (HeapSort):
                case 1:
                    System.out.println("\n=== ORDENANDO COM HEAPSORT ===");

                    // Extrair todos os commits da estrutura de armazenamento
                    List<Commit> commitsParaOrdenar = new ArrayList<>();

                    if (opcaoEstrutura == 1) {
                        // Extrair da HashTable
                        HashTable<Object> tabela = (HashTable<Object>) estruturaArmazenamento;
                        for (int i = 0; i < tabela.getCapacidade(); i++) {
                            for (Entry<Object> entry : tabela.getTabela()[i]) {
                                @SuppressWarnings("unchecked")
                                LinkedList<Commit> lista = (LinkedList<Commit>) entry.getValor();
                                commitsParaOrdenar.addAll(lista);
                            }
                        }
                    } else if (opcaoEstrutura == 2) {
                        // Extrair da AVL Tree
                        AVLTreeGroup avlGroup = (AVLTreeGroup) estruturaArmazenamento;
                        commitsParaOrdenar = avlGroup.extrairTodosCommits();
                    } else if (opcaoEstrutura == 3) {
                        // Extrair da RB Tree (implementar quando tiver)
                        // commitsParaOrdenar = RBTreeExtrair.extrairTodosCommits(estruturaArmazenamento);
                        System.out.println("Extrair da RB Tree - Implementação pendente");
                        return;
                    }

                    // Ordenar com HeapSort
                    ordenados = HeapSortStable.heapSortEstavel(commitsParaOrdenar);
                    break;
                case 2:
                    System.out.println("\n=== ORDENANDO COM QUICKSORT ===");
                    // Aqui você chamará seu QuickSort
                    // if (opcaoEstrutura == 1) {
                    //     ordenados = QuickSortHashTable.quickSort(estruturaArmazenamento);
                    // } else if (opcaoEstrutura == 2) {
                    //     ordenados = QuickSortAVL.quickSort(estruturaArmazenamento);
                    // } else {
                    //     ordenados = QuickSortRB.quickSort(estruturaArmazenamento);
                    // }
                    System.out.println("QuickSort - Implementação pendente");
                    break;
                case 3:
                    System.out.println("\n=== ORDENANDO COM SELECTIONSORT ===");
                    // Aqui você chamará seu SelectionSort
                    // if (opcaoEstrutura == 1) {
                    //     ordenados = SelectionSortHashTable.selectionSort(estruturaArmazenamento);
                    // } else if (opcaoEstrutura == 2) {
                    //     ordenados = SelectionSortAVL.selectionSort(estruturaArmazenamento);
                    // } else {
                    //     ordenados = SelectionSortRB.selectionSort(estruturaArmazenamento);
                    // }
                    System.out.println("SelectionSort - Implementação pendente");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    return;
            }

            // ============================
            // 5. IMPRIMIR RESULTADOS
            // ============================
            if (ordenados != null) {
                System.out.println("\n===== LISTA ORDENADA =====");
                for (Commit c : ordenados) {
                    System.out.println(
                            c.getHash() + "  ts=" + c.getTimestamp() + "  ordem=" + c.getOrdemOriginal()
                    );
                }
                System.out.println("\nTotal de commits ordenados: " + ordenados.size());
            } else {
                System.out.println("\nNenhum resultado para exibir.");
            }

            // ============================
            // 6. ESTATÍSTICAS
            // ============================
            System.out.println("\n===== ESTATÍSTICAS =====");
            System.out.println("Estrutura de armazenamento: " + tipoEstrutura);
            System.out.println("Algoritmo de ordenação: " +
                    (opcaoOrdenacao == 1 ? "HeapSort" :
                            opcaoOrdenacao == 2 ? "QuickSort" : "SelectionSort"));
            System.out.println("Total de commits processados: " + commits.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}