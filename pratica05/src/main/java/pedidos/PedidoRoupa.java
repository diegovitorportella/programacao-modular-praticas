package pedidos;

import pedidos.excecoes.ValorInvalidoException;

/** Roupa: 20% de desconto se valor > 200; frete = R$ 10 + 5% do valor. */
public class PedidoRoupa extends Pedido implements Entregavel {
    public static final double LIMITE_DESCONTO = 200;
    public static final double DESCONTO = 0.20;
    public static final double FRETE_FIXO = 10;
    public static final double FRETE_PERCENTUAL = 0.05;

    public PedidoRoupa(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ROUPA);
    }

    @Override
    public double calcularValorFinal() {
        return getValor() > LIMITE_DESCONTO ? getValor() * (1 - DESCONTO) : getValor();
    }

    @Override
    public double calcularFrete() {
        return FRETE_FIXO + FRETE_PERCENTUAL * getValor();
    }
}
