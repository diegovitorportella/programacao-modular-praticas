package pedidos.excecoes;

/** Exceção base das regras de negócio de pedidos. */
public class PedidoException extends Exception {
    public PedidoException(String mensagem) {
        super(mensagem);
    }
}
