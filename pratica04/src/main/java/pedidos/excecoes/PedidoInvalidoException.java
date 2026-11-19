package pedidos.excecoes;

/** Pedido cancelado sendo processado, ou pedido entregue tentando mudar de status. */
public class PedidoInvalidoException extends Exception {
    public PedidoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
