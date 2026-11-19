package pedidos;

import java.math.BigDecimal;

/** Pedido de produto físico: subtotal + frete. */
public class PedidoFisico extends Pedido {
    private final BigDecimal frete;

    public PedidoFisico(int id, String cliente, BigDecimal frete) {
        super(id, cliente);
        if (frete == null || frete.signum() < 0) {
            throw new IllegalArgumentException("frete inválido");
        }
        this.frete = frete;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    @Override
    protected BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.add(frete);
    }
}
