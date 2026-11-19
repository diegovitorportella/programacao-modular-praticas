package pedidos;

import pedidos.excecoes.ValorInvalidoException;

/** Eletrônico: 10% de desconto se valor > 100; frete = R$ 20 + 10% do valor. */
public class PedidoEletronico extends Pedido implements Entregavel {
    public static final double LIMITE_DESCONTO = 100;
    public static final double DESCONTO = 0.10;
    public static final double FRETE_FIXO = 20;
    public static final double FRETE_PERCENTUAL = 0.10;

    public PedidoEletronico(int id, String nomeCliente, double valor) throws ValorInvalidoException {
        super(id, nomeCliente, valor, Categoria.ELETRONICO);
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
