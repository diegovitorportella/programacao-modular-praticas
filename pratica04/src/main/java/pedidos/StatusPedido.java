package pedidos;

/** Status de um pedido. ENTREGUE é final: um pedido entregue não pode mais mudar de status. */
public enum StatusPedido {
    PENDENTE,
    PROCESSANDO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;

    /** Regra do enum: pedidos ENTREGUE não podem ser alterados. */
    public boolean podeSerAlterado() {
        return this != ENTREGUE;
    }

    /** Próximo status do fluxo normal (PENDENTE → PROCESSANDO → ENVIADO → ENTREGUE), ou null. */
    public StatusPedido proximo() {
        return switch (this) {
            case PENDENTE -> PROCESSANDO;
            case PROCESSANDO -> ENVIADO;
            case ENVIADO -> ENTREGUE;
            case ENTREGUE, CANCELADO -> null;
        };
    }
}
