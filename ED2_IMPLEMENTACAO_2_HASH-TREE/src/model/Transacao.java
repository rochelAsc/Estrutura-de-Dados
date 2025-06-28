package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo de uma transação financeira conforme especificado na Prática 2.
 */
public class Transacao {
    private String id;
    private float valor;
    private String origem;
    private String destino;
    private LocalDateTime timestamp;

    // Formato ISO-8601: e.g., "2025-03-15T12:00:00"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Construtor principal da transação.
     * @param id Identificador único da transação
     * @param valor Valor financeiro
     * @param origem Código da conta de origem
     * @param destino Código da conta de destino
     * @param timestampStr Timestamp em formato ISO-8601
     */
    public Transacao(String id, float valor, String origem, String destino, String timestampStr) {
        this.id = id;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.timestamp = LocalDateTime.parse(timestampStr, FORMATTER);
    }

    public String getId() {
        return id;
    }

    public float getValor() {
        return valor;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id='" + id + '\'' +
                ", valor=" + valor +
                ", origem='" + origem + '\'' +
                ", destino='" + destino + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
