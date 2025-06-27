import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GeradorProduto {

    private static final String[] CATEGORIAS = {
        "BISCOITOS", "CEREAIS", "LATICINIOS", "BEBIDAS", 
        "CONGELADOS", "HIGIENE", "LIMPEZA"
    };

    private static final String[] MARCAS = {
        "NESTLE", "BAUDUCO", "UNIAO", "YOKI", "SADIA",
        "PERDIGAO", "COCA-COLA", "DOVE", "OMO"
    };

    private static final Random random = new Random();

    public static void main(String[] args) {
        int[] tamanhos = {1000, 10000, 100000, 1000000};
        
        for (int tamanho : tamanhos) {
            String nomeArquivo = "produtos_" + tamanho + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
                for (int i = 0; i < tamanho; i++) {
                    writer.println(gerarProduto());
                }
                System.out.println("Arquivo gerado: " + nomeArquivo);
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        }
    }

    private static String gerarProduto() {
        String nome = "PRODUTO_" + (random.nextInt(10000) + 1);
        String marca = MARCAS[random.nextInt(MARCAS.length)];
        double preco = 1.0 + (50.0 - 1.0) * random.nextDouble();
        String dataValidade = gerarDataValidade();
        String categoria = CATEGORIAS[random.nextInt(CATEGORIAS.length)];

        return String.format(
            "%s # %s # %.2f # %s # %s",
            nome, marca, preco, dataValidade, categoria
        );
    }

    private static String gerarDataValidade() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Date hoje = new Date();
        long milissegundosPorDia = 24 * 60 * 60 * 1000L;
        long diasParaValidade = 30 + random.nextInt(3 * 365 - 30);
        Date dataValidade = new Date(hoje.getTime() + diasParaValidade * milissegundosPorDia);
        return sdf.format(dataValidade);
    }
}
