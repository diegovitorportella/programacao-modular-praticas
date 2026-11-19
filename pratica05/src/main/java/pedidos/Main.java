package pedidos;

import java.util.List;
import java.util.Scanner;
import pedidos.excecoes.PedidoInvalidoException;
import pedidos.excecoes.ValorInvalidoException;

/** Interface de console. As regras de negócio ficam em GerenciadorPedidos e nos pedidos. */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GerenciadorPedidos gerenciador = new GerenciadorPedidos();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> alterarStatus();
                case 3 -> gerenciador.processarTodos().forEach(System.out::println);
                case 4 -> System.out.println(gerenciador.removerCancelados() + " pedido(s) cancelado(s) removido(s).");
                case 5 -> exibir(gerenciador.listarNaoCancelados());
                case 6 -> exibirEntregaveis();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== PEDIDOS =====");
        System.out.println("1 - Cadastrar pedido");
        System.out.println("2 - Alterar status de um pedido");
        System.out.println("3 - Processar pedidos");
        System.out.println("4 - Remover cancelados");
        System.out.println("5 - Exibir lista de pedidos");
        System.out.println("6 - Exibir pedidos entregáveis");
        System.out.println("0 - Encerrar");
    }

    private static void cadastrar() {
        System.out.println("Categoria: 1 - Eletrônico | 2 - Roupa | 3 - Alimento | 0 - Voltar");
        int tipo = lerInt("Categoria: ");
        if (tipo == 0) {
            return;
        }
        if (tipo < 1 || tipo > 3) {
            System.out.println("Categoria inválida.");
            return;
        }
        String cliente = lerTexto("Cliente: ");
        double valor = lerDouble("Valor: ");
        int id = gerenciador.gerarId();
        try {
            Pedido pedido = switch (tipo) {
                case 1 -> new PedidoEletronico(id, cliente, valor);
                case 2 -> new PedidoRoupa(id, cliente, valor);
                default -> new PedidoAlimento(id, cliente, valor);
            };
            gerenciador.adicionar(pedido);
            System.out.println("Pedido cadastrado: " + pedido);
        } catch (ValorInvalidoException | IllegalArgumentException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }

    private static void alterarStatus() {
        int id = lerInt("Id do pedido: ");
        StatusPedido[] status = StatusPedido.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + " - " + status[i]);
        }
        int escolha = lerInt("Novo status (0 para voltar): ");
        if (escolha == 0) {
            return;
        }
        if (escolha < 1 || escolha > status.length) {
            System.out.println("Status inválido.");
            return;
        }
        try {
            if (gerenciador.alterarStatus(id, status[escolha - 1])) {
                System.out.println("Status alterado.");
            } else {
                System.out.println("Pedido não encontrado.");
            }
        } catch (PedidoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void exibir(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido para exibir.");
        }
        pedidos.forEach(System.out::println);
    }

    private static void exibirEntregaveis() {
        List<Pedido> entregaveis = gerenciador.listarEntregaveis();
        if (entregaveis.isEmpty()) {
            System.out.println("Nenhum pedido entregável.");
        }
        for (Pedido p : entregaveis) {
            System.out.printf("%s | frete R$ %.2f%n", p, ((Entregavel) p).calcularFrete());
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(lerTexto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                return Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
