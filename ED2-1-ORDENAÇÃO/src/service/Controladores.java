package service;
import model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Controladores {



    // método auxiliar para extrair o número do nome no formato "produto_xxxx"
    public static int extrairNumeroNome(Produto p) {
        // pega o nome do produto
        String nome = p.getNome();
        // retorna a parte numérica após "produto_"
        return Integer.parseInt(nome.substring(8));
    }

    // método para escolher o pivô baseado na média de 3 preços do array de produtos
    public static int MediaPivo(Produto[] A, int inicio, int fim) {
        // calcula a posição do meio
        int meio = inicio + (fim - inicio) / 2;
        // obtém o preço do produto no inicio
        double precoInicio = A[inicio].getPreco();
        // obtém o preço do produto no meio
        double precoMeio = A[meio].getPreco();
        // obtém o preço do produto no fim
        double precoFim = A[fim].getPreco();

        // usa xor para decidir qual índice será o pivô baseado nos preços
        if ((precoInicio > precoMeio) ^ (precoInicio > precoFim)) {
            return inicio;
        } else if ((precoMeio < precoInicio) ^ (precoMeio < precoFim)) {
            return meio;
        } else {
            return fim;
        }
    }

    // compara a validade de dois produtos convertendo as datas para localdate
    public static int compararValidade(Produto p1, Produto p2) {
        // converte a validade do produto 1 para localdate
        LocalDate d1 = parseValidade(p1.getValidade());
        // converte a validade do produto 2 para localdate
        LocalDate d2 = parseValidade(p2.getValidade());
        // compara as duas datas e retorna o resultado da comparação
        return d1.compareTo(d2);
    }

    // converte a string da validade no formato "ddMMyyyy" para um objeto localdate
    public static LocalDate parseValidade(String validade) {
        // define o formato da data esperado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        // faz o parse da string para localdate usando o formatter
        return LocalDate.parse(validade, formatter);
    }

    public static void SalvarDados(Produto[] produtos, String nomeArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Produto p : produtos) {
                // Formato: Nome, Fabricante, Preço, Validade
                bw.write(String.format("%s, %s, %.2f, %s",
                        p.getNome(), p.getFabricante(), p.getPreco(), p.getValidade()));
                bw.newLine();
            }
            System.out.println("Arquivo salvo com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public static void SalvarDadosLinkedList(ListaEncadeadaProduto lista, String nomeArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            NodeProduto atual = lista.head;  // acessa diretamente a cabeça da lista
            while (atual != null) {
                // Formato: Nome, Fabricante, Preço, Validade
                bw.write(String.format("%s, %s, %.2f, %s",
                        atual.data.getNome(),
                        atual.data.getFabricante(),
                        atual.data.getPreco(),
                        atual.data.getValidade()));
                bw.newLine();
                atual = atual.next;
            }
            System.out.println("Arquivo salvo com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }


}
