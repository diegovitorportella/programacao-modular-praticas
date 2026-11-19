package pedidos;

/**
 * Status possíveis de um pedido e as regras de transição entre eles.
 * ENTREGUE e CANCELADO são estados finais: o pedido não pode mais ser alterado.
 */
public enum StatusPedido {
    CRIADO,
    PAGO,
    EM_PREPARACAO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    /** Indica se o pedido neste status não pode mais ser alterado. */
    public boolean isFinal() {
        return this == ENTREGUE || this == CANCELADO;
    }

    /** Próximo status do fluxo normal de processamento, ou null se o status for final. */
    public StatusPedido proximo() {
        return switch (this) {
            case CRIADO -> PAGO;
            case PAGO -> EM_PREPARACAO;
            case EM_PREPARACAO -> ENVIADO;
            case ENVIADO -> ENTREGUE;
            case ENTREGUE, CANCELADO -> null;
        };
    }

    /** Verifica se a transição para o status informado é permitida. */
    public boolean podeTransicionarPara(StatusPedido destino) {
        if (isFinal() || destino == null) {
            return false;
        }
        return destino == proximo() || destino == CANCELADO;
    }
}
