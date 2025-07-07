package service;

import model.Transacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoParser {

    /**
     * Lê um arquivo CSV e retorna uma lista de transações.
     * Espera o formato: id;origem;destino;valor;timestamp
     */
    public static List<Transacao> carregarTransacoes(String caminhoArquivo) {
        List<Transacao> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula cabeçalho
                    continue;
                }

                String[] campos = linha.split(";");
                if (campos.length != 5) continue;

                String id = campos[0];
                String origem = campos[1];
                String destino = campos[2];
                float valor = Float.parseFloat(campos[3].replace(",", "."));
                String timestamp = campos[4];

                Transacao t = new Transacao(id, valor, origem, destino, timestamp);
                lista.add(t);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return lista;
    }
}
