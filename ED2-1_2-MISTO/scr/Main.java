import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import algoritmos.HeapSortStable;
import dados.entrada.Commit;

public class Main {

    public static void main(String[] args) {

        try {
            // ============================
            // 1. LER ARQUIVO JSON
            // ============================
            String caminho = "C:\\Users\\Rochel\\Documents\\GitHub\\Estrutura-de-Dados\\ED2-1_2-MISTO\\scr\\dados\\saida\\commits_1000.json";  // ajuste o caminho
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

                // cria o commit REAL
                Commit c = new Commit(hash, autor, mensagem, timestamp, ordem);
                commits.add(c);
            }

            // ============================
            // 3. IMPRIMIR LISTA ORIGINAL
            // ============================
            System.out.println("===== LISTA ORIGINAL =====");
            for (Commit c : commits) {
                System.out.println(
                        c.getHash() + "  ts=" + c.getTimestamp() + "  ordem=" + c.getOrdemOriginal()
                );
            }

            // ============================
            // 4. ORDENAR COM HEAPSORT ESTÁVEL
            // ============================
            List<Commit> ordenados = HeapSortStable.heapSortEstavel(commits);

            // ============================
            // 5. IMPRIMIR LISTA ORDENADA
            // ============================
            System.out.println("\n===== LISTA ORDENADA =====");
            for (Commit c : ordenados) {
                System.out.println(
                        c.getHash() + "  ts=" + c.getTimestamp() + "  ordem=" + c.getOrdemOriginal()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
