package pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import pedidos.excecoes.PedidoVazioException;
import pedidos.excecoes.StatusInvalidoException;

/** Classe base dos pedidos. Subclasses definem como o valor final é calculado. */
public abstract class Pedido {
    private final int id;
    private final String cliente;
    private final List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status = StatusPedido.CRIADO;

    protected Pedido(int id, String cliente) {
        Objects.requireNonNull(cliente, "cliente não pode ser nulo");
        if (cliente.isBlank()) {
            throw new IllegalArgumentException("cliente não pode ser vazio");
        }
        this.id = id;
        this.cliente = cliente;
    }

    /** Adiciona um item. Pedidos finalizados (ENTREGUE/CANCELADO) não podem ser alterados. */
    public void adicionarItem(ItemPedido item) throws StatusInvalidoException {
        Objects.requireNonNull(item, "item não pode ser nulo");
        if (status.isFinal()) {
            throw new StatusInvalidoException(status, status);
        }
        itens.add(item);
    }

    public BigDecimal getSubtotal() {
        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Valor final do pedido, já com descontos/acréscimos do tipo de pedido. */
    public final BigDecimal calcularValorFinal() {
        return calcularTotal(getSubtotal()).setScale(2, RoundingMode.HALF_UP);
    }

    /** Aplica a regra de preço específica do tipo de pedido sobre o subtotal. */
    protected abstract BigDecimal calcularTotal(BigDecimal subtotal);

    /** Avança o pedido para o próximo status do fluxo normal. */
    public void processar() throws PedidoVazioException, StatusInvalidoException {
        if (itens.isEmpty()) {
            throw new PedidoVazioException(id);
        }
        StatusPedido proximo = status.proximo();
        if (proximo == null) {
            throw new StatusInvalidoException(status, null);
        }
        status = proximo;
    }

    /** Altera o status para um destino válido (próximo do fluxo ou CANCELADO). */
    public void alterarStatus(StatusPedido novoStatus) throws StatusInvalidoException {
        if (!status.podeTransicionarPara(novoStatus)) {
            throw new StatusInvalidoException(status, novoStatus);
        }
        status = novoStatus;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}
