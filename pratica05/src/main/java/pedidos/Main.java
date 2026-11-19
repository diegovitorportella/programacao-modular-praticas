package pedidos;

import java.math.BigDecimal;

/** Demonstração simples. Nenhuma regra de negócio ou teste fica aqui. */
public class Main {
    public static void main(String[] args) throws Exception {
        Pedido pedido = new PedidoEletronico(1, "Ana");
        pedido.adicionarItem(new ItemPedido("E-book", new BigDecimal("50.00"), 2));

        System.out.println("Valor final: " + pedido.calcularValorFinal());
        while (!pedido.getStatus().isFinal()) {
            pedido.processar();
            System.out.println("Status: " + pedido.getStatus());
        }
    }
}
