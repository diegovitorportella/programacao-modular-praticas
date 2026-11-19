package pedidos.excecoes;

/** Valor do pedido menor ou igual a zero. */
public class ValorInvalidoException extends Exception {
    public ValorInvalidoException(double valor) {
        super("Valor inválido: " + valor + ". O valor do pedido deve ser maior que zero.");
    }
}
