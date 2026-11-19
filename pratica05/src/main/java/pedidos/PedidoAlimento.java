package pedidos;

import pedidos.excecoes.ValorInvalidoException;

/**
 * Alimento: sem desconto e NÃO entregável (não implementa Entregavel).
 * A taxa fixa de R$ 5 do enunciado fica em {@link #getTaxaFixa()}.
 */
public class PedidoAlimento extends Pedido {
    public static final double TAXA_FIXA = 5;

    public PedidoAlimento(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ALIMENTO);
    }

    @Override
    public double calcularValorFinal() {
        return getValor();
    }

    public double getTaxaFixa() {
        return TAXA_FIXA;
    }
}
