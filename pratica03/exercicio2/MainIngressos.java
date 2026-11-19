import java.util.Scanner;

public class MainIngressos {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bilheteria bilheteria = new Bilheteria();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> remover();
                case 3 -> buscar();
                case 4 -> bilheteria.listarIngressos();
                case 5 -> System.out.printf("Arrecadação total: R$ %.2f%n",
                        bilheteria.calcularArrecadacaoTotal());
                case 6 -> bilheteria.listarBeneficios();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== BILHETERIA =====");
        System.out.println("1 - Cadastrar ingresso");
        System.out.println("2 - Remover ingresso");
        System.out.println("3 - Buscar ingresso por código");
        System.out.println("4 - Listar todos os ingressos");
        System.out.println("5 - Calcular arrecadação total");
        System.out.println("6 - Listar benefícios dos ingressos");
        System.out.println("0 - Finalizar");
    }

    private static void cadastrar() {
        System.out.println("Tipo: 1 - Comum | 2 - Estudante | 3 - VIP | 0 - Voltar");
        int tipo = lerInt("Tipo: ");
        if (tipo == 0) {
            return;
        }
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido.");
            return;
        }
        int codigo = lerInt("Código: ");
        String evento = lerTexto("Evento: ");
        String setor = lerTexto("Setor: ");
        double valor = lerDouble("Valor base: ");

        Ingresso ingresso;
        switch (tipo) {
            case 1 -> ingresso = new IngressoComum(codigo, evento, setor, valor);
            case 2 -> ingresso = new IngressoEstudante(codigo, evento, setor, valor,
                    lerTexto("Instituição de ensino: "));
            default -> {
                boolean backstage = lerTexto("Acesso ao backstage (s/n): ").equalsIgnoreCase("s");
                int lounge = lerInt("Número do lounge: ");
                ingresso = new IngressoVIP(codigo, evento, setor, valor, backstage, lounge);
            }
        }
        if (bilheteria.adicionarIngresso(ingresso)) {
            System.out.println("Ingresso cadastrado!");
        } else {
            System.out.println("Já existe um ingresso com o código " + codigo + ".");
        }
    }

    private static void remover() {
        int codigo = lerInt("Código do ingresso a remover: ");
        if (bilheteria.removerIngresso(codigo)) {
            System.out.println("Ingresso removido com sucesso.");
        } else {
            System.out.println("Ingresso não encontrado.");
        }
    }

    private static void buscar() {
        Ingresso ingresso = bilheteria.buscarIngresso(lerInt("Código: "));
        if (ingresso == null) {
            System.out.println("Ingresso não encontrado.");
        } else {
            ingresso.exibirInformacoes();
            System.out.println("Benefícios: " + ingresso.obterBeneficios());
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
                double valor = Double.parseDouble(lerTexto(mensagem).replace(',', '.'));
                if (valor >= 0) {
                    return valor;
                }
                System.out.println("O valor não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
