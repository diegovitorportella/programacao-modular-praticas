package pedidos.excecoes;

/** Lançada ao tentar processar um pedido sem itens. */
public class PedidoVazioException extends PedidoException {
    public PedidoVazioException(int idPedido) {
        super("O pedido " + idPedido + " não possui itens.");
    }
}
