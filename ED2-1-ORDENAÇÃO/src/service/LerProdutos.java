package service;
import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LerProdutos {  // classe para manipular a leitura de produtos de arquivo

    // método para ler produtos a partir do caminho de um arquivo texto
    public static List<Produto> lerProdutosDeArquivo(String caminhoArquivo) {
        // lista para armazenar os produtos lidos
        List<Produto> produtos = new ArrayList<>();

        // tenta abrir o arquivo com BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            // lê linha por linha até o fim do arquivo
            while ((linha = br.readLine()) != null) {
                // ignora linhas vazias
                if (linha.trim().isEmpty()) continue;

                // divide a linha pelos separadores " # "
                String[] campos = linha.split(" # ");

                // verifica se a linha tem exatamente 5 campos esperados
                if (campos.length != 5) {
                    System.err.println("linha inválida (esperado 5 campos): " + linha);
                    continue; // pula a linha inválida
                }

                // extrai e limpa os campos de texto
                String nome = campos[0].trim();
                String fabricante = campos[1].trim();

                // substitui vírgula por ponto para permitir parse de double no preço
                String precoStr = campos[2].trim().replace(",", ".");
                double preco = Double.parseDouble(precoStr);

                // mantém validade como string
                String validade = campos[3].trim();
                String tipo = campos[4].trim();

                // cria um novo objeto produto com os dados extraídos
                Produto produto = new Produto(nome, fabricante, preco, validade, tipo);

                // adiciona o produto na lista
                produtos.add(produto);
            }
        } catch (FileNotFoundException e) {
            // caso o arquivo não seja encontrado, imprime erro
            System.err.println("arquivo não encontrado: " + caminhoArquivo);
        } catch (IOException e) {
            // caso ocorra erro na leitura do arquivo, imprime erro
            System.err.println("erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            // caso o preço não possa ser convertido para double, imprime erro
            System.err.println("erro ao converter preço: " + e.getMessage());
        }

        // retorna a lista de produtos lidos do arquivo
        return produtos;
    }
}
