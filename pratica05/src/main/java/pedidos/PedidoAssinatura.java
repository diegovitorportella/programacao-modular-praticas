package pedidos;

import java.math.BigDecimal;

/** Pedido de assinatura recorrente: 20% de desconto para contratos de 12 meses ou mais. */
public class PedidoAssinatura extends Pedido {
    public static final BigDecimal DESCONTO_ANUAL = new BigDecimal("0.20");
    public static final int MESES_PARA_DESCONTO = 12;

    private final int meses;

    public PedidoAssinatura(int id, String cliente, int meses) {
        super(id, cliente);
        if (meses <= 0) {
            throw new IllegalArgumentException("meses deve ser maior que zero");
        }
        this.meses = meses;
    }

    public int getMeses() {
        return meses;
    }

    /** O subtotal dos itens é mensal; o total multiplica pelos meses e aplica o desconto anual. */
    @Override
    protected BigDecimal calcularTotal(BigDecimal subtotal) {
        BigDecimal total = subtotal.multiply(BigDecimal.valueOf(meses));
        if (meses >= MESES_PARA_DESCONTO) {
            total = total.subtract(total.multiply(DESCONTO_ANUAL));
        }
        return total;
    }
}
