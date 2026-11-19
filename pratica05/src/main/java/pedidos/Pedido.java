package pedidos;

import java.util.Objects;
import pedidos.excecoes.PedidoInvalidoException;
import pedidos.excecoes.ValorInvalidoException;

/** Base dos pedidos. Subclasses definem o valor final (desconto) e, se entregáveis, o frete. */
public abstract class Pedido implements Processavel {
    private final int id;
    private final String nomeCliente;
    private final double valor;
    private final Categoria categoria;
    private StatusPedido status = StatusPedido.PENDENTE;

    protected Pedido(int id, String nomeCliente, double valor, Categoria categoria)
            throws ValorInvalidoException {
        if (!(valor > 0)) { // também rejeita NaN
            throw new ValorInvalidoException(valor);
        }
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("nome do cliente não pode ser vazio");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.categoria = Objects.requireNonNull(categoria, "categoria não pode ser nula");
    }

    /**
     * Avança o pedido no fluxo normal (PENDENTE → PROCESSANDO → ENVIADO → ENTREGUE).
     *
     * @throws PedidoInvalidoException se o pedido estiver cancelado ou já entregue
     */
    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (status == StatusPedido.CANCELADO) {
            throw new PedidoInvalidoException(
                    "Pedido " + id + " está cancelado e não pode ser processado.");
        }
        validarAlteracao();
        status = status.proximo();
    }

    /**
     * Altera o status manualmente.
     *
     * @throws PedidoInvalidoException se o pedido já foi entregue
     */
    public void alterarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        Objects.requireNonNull(novoStatus, "status não pode ser nulo");
        validarAlteracao();
        status = novoStatus;
    }

    private void validarAlteracao() throws PedidoInvalidoException {
        if (!status.podeSerAlterado()) {
            throw new PedidoInvalidoException(
                    "Pedido " + id + " já foi entregue e não pode mudar de status.");
        }
    }

    public boolean isCancelado() {
        return status == StatusPedido.CANCELADO;
    }

    public int getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public double getValor() {
        return valor;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("#%d | %s | %s | valor R$ %.2f | final R$ %.2f | %s",
                id, nomeCliente, categoria, valor, calcularValorFinal(), status);
    }
}
