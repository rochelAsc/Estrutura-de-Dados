package service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatasetGenerator {

    public static void gerarDataset(String caminho, int quantidade) {
        List<Map<String, String>> transacoes = new ArrayList<>();
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 20% origens mais frequentes
        List<String> origensFrequentes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            origensFrequentes.add("ORIG" + String.format("%03d", i));
        }

        // 80% origens menos frequentes
        List<String> origensRaras = new ArrayList<>();
        for (int i = 20; i < 100; i++) {
            origensRaras.add("ORIG" + String.format("%03d", i));
        }

        for (int i = 0; i < quantidade; i++) {
            Map<String, String> t = new HashMap<>();

            // ID com 10% de chance de repetir
            String id;
            if (i > 0 && random.nextDouble() < 0.1) {
                id = transacoes.get(random.nextInt(i)).get("id");
            } else {
                id = "ID" + String.format("%05d", i);
            }

            // Origem 80/20
            String origem = (random.nextDouble() < 0.8)
                    ? origensFrequentes.get(random.nextInt(origensFrequentes.size()))
                    : origensRaras.get(random.nextInt(origensRaras.size()));

            String destino = "DEST" + String.format("%03d", random.nextInt(1000));

            // Valor aleatório entre 10 e 1000
            String valor = String.format("%.2f", 10 + random.nextDouble() * 990).replace(",", ".");

            // Data aleatória entre 2020 e 2024
            Calendar cal = Calendar.getInstance();
            cal.set(2020 + random.nextInt(5), random.nextInt(12), 1 + random.nextInt(28));
            String timestamp = sdf.format(cal.getTime());

            t.put("id", id);
            t.put("origem", origem);
            t.put("destino", destino);
            t.put("valor", valor);
            t.put("timestamp", timestamp);

            transacoes.add(t);
        }

        // Exporta para CSV
        try (FileWriter writer = new FileWriter(caminho)) {
            writer.write("id;origem;destino;valor;timestamp\n");
            for (Map<String, String> t : transacoes) {
                writer.write(String.join(";", t.get("id"), t.get("origem"),
                        t.get("destino"), t.get("valor"), t.get("timestamp")) + "\n");
            }
            System.out.println("Dataset gerado com sucesso: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao salvar CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        gerarDataset("transacoes.csv", 10000);
    }
}
