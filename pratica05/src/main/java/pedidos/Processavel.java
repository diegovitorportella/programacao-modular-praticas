package pedidos;

import pedidos.excecoes.PedidoInvalidoException;

public interface Processavel {
    void processarPedido() throws PedidoInvalidoException;

    double calcularValorFinal();
}
