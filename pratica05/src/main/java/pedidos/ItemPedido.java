package pedidos;

import java.math.BigDecimal;
import java.util.Objects;

/** Item de um pedido: produto, preço unitário e quantidade. */
public class ItemPedido {
    private final String produto;
    private final BigDecimal precoUnitario;
    private final int quantidade;

    public ItemPedido(String produto, BigDecimal precoUnitario, int quantidade) {
        Objects.requireNonNull(produto, "produto não pode ser nulo");
        Objects.requireNonNull(precoUnitario, "preço não pode ser nulo");
        if (produto.isBlank()) {
            throw new IllegalArgumentException("produto não pode ser vazio");
        }
        if (precoUnitario.signum() < 0) {
            throw new IllegalArgumentException("preço não pode ser negativo");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser maior que zero");
        }
        this.produto = produto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public String getProduto() {
        return produto;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
