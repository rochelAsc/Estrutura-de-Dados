package model;

public class Transacao {
    public String id;
    public float valor;
    public String origem;
    public String destino;
    public String timestamp;

    public Transacao(String id, float valor, String origem, String destino, String timestamp) {
        this.id = id;
        this.valor = valor;
        this.origem = origem;
        this.destino = destino;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format(
                "[ID: %s | Valor: %.2f | %s → %s | Data: %s]",
                id, valor, origem, destino, timestamp
        );
    }
}
