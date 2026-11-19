package pedidos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pedidos.excecoes.PedidoInvalidoException;

/** Regras de negócio sobre a lista de pedidos (a classe Main só cuida da interface). */
public class GerenciadorPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();
    private int proximoId = 1;

    public int gerarId() {
        return proximoId++;
    }

    public void adicionar(Pedido pedido) {
        pedidos.add(pedido);
    }

    /** Retorna o pedido com o id, ou null se não existir. */
    public Pedido buscarPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Altera o status de um pedido.
     *
     * @return false se o pedido não existir
     * @throws PedidoInvalidoException se o pedido já foi entregue
     */
    public boolean alterarStatus(int id, StatusPedido novoStatus) throws PedidoInvalidoException {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            return false;
        }
        pedido.alterarStatus(novoStatus);
        return true;
    }

    /**
     * Processa todos os pedidos usando Iterator. Um pedido inválido não interrompe os demais.
     *
     * @return uma linha de relatório por pedido
     */
    public List<String> processarTodos() {
        List<String> relatorio = new ArrayList<>();
        Iterator<Pedido> it = pedidos.iterator();
        while (it.hasNext()) {
            Pedido pedido = it.next();
            try {
                pedido.processarPedido();
                relatorio.add("Pedido " + pedido.getId() + " -> " + pedido.getStatus());
            } catch (PedidoInvalidoException e) {
                relatorio.add("Erro: " + e.getMessage());
            }
        }
        return relatorio;
    }

    /** Remove todos os pedidos cancelados usando Iterator. Retorna quantos foram removidos. */
    public int removerCancelados() {
        int removidos = 0;
        Iterator<Pedido> it = pedidos.iterator();
        while (it.hasNext()) {
            if (it.next().isCancelado()) {
                it.remove();
                removidos++;
            }
        }
        return removidos;
    }

    /** Pedidos para exibição: não inclui os cancelados. */
    public List<Pedido> listarNaoCancelados() {
        List<Pedido> visiveis = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isCancelado()) {
                visiveis.add(p);
            }
        }
        return visiveis;
    }

    /** Pedidos entregáveis (tudo menos alimento), ignorando os cancelados. */
    public List<Pedido> listarEntregaveis() {
        List<Pedido> entregaveis = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p instanceof Entregavel && !p.isCancelado()) {
                entregaveis.add(p);
            }
        }
        return entregaveis;
    }

    public int getQuantidade() {
        return pedidos.size();
    }
}
