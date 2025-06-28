package testes;

import hash.HashHibrida;
import model.Transacao;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes básicos para HashHibrida: inserção e buscas.
 */
public class HashHibridaTest {
    @Test
    public void testInserirEBuscarPorId() {
        HashHibrida tabela = new HashHibrida(10);
        Transacao t1 = new Transacao("ID1", 100.0f, "O1", "D1", "2025-01-01T10:00:00");
        tabela.inserir(t1);

        Transacao resultado = tabela.buscarPorId("ID1");
        assertNotNull(resultado, "Transação não encontrada por ID");
        assertEquals("ID1", resultado.getId(), "ID da transação incorreto");
        assertEquals(100.0f, resultado.getValor(), 0.0001f, "Valor da transação incorreto");
    }

    @Test
    public void testBuscarPorOrigemIntervalo() {
        HashHibrida tabela = new HashHibrida(10);
        Transacao t1 = new Transacao("ID1", 50.0f, "O1", "D1", "2025-01-01T08:00:00");
        Transacao t2 = new Transacao("ID2", 75.0f, "O1", "D2", "2025-01-02T12:00:00");
        Transacao t3 = new Transacao("ID3", 125.0f, "O1", "D3", "2025-01-03T18:00:00");
        tabela.inserir(t1);
        tabela.inserir(t2);
        tabela.inserir(t3);

        LocalDateTime inicio = LocalDateTime.parse("2025-01-01T00:00:00");
        LocalDateTime fim = LocalDateTime.parse("2025-01-02T23:59:59");
        List<Transacao> resultado = tabela.buscarPorOrigemIntervalo("O1", inicio, fim);

        assertEquals(2, resultado.size(), "Deveriam ser retornadas duas transações");
        assertTrue(resultado.stream().anyMatch(tr -> tr.getId().equals("ID1")), "Transação ID1 não encontrada no intervalo");
        assertTrue(resultado.stream().anyMatch(tr -> tr.getId().equals("ID2")), "Transação ID2 não encontrada no intervalo");
    }
}
