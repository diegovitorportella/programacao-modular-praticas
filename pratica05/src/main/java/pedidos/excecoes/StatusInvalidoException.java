package pedidos.excecoes;

import pedidos.StatusPedido;

/** Lançada quando uma transição de status não é permitida (ex.: alterar pedido ENTREGUE). */
public class StatusInvalidoException extends PedidoException {
    private final StatusPedido atual;
    private final StatusPedido destino;

    public StatusInvalidoException(StatusPedido atual, StatusPedido destino) {
        super("Transição inválida: " + atual + " -> " + destino);
        this.atual = atual;
        this.destino = destino;
    }

    public StatusPedido getAtual() {
        return atual;
    }

    public StatusPedido getDestino() {
        return destino;
    }
}
