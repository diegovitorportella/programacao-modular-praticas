import java.util.Scanner;

public class MainAgenda {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Agenda agenda = new Agenda();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            switch (opcao) {
                case 1 -> adicionar();
                case 2 -> remover();
                case 3 -> exibirResultado(agenda.buscarPorNome(lerTexto("Nome: ")));
                case 4 -> exibirResultado(agenda.buscarPorEmail(lerTexto("E-mail: ")));
                case 5 -> exibirResultado(agenda.buscarPorTelefone(lerTexto("Telefone: ")));
                case 6 -> System.out.println("A agenda possui " + agenda.getQuantidade() + " contato(s).");
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== AGENDA =====");
        System.out.println("1 - Adicionar contato");
        System.out.println("2 - Remover contato");
        System.out.println("3 - Buscar por nome");
        System.out.println("4 - Buscar por e-mail");
        System.out.println("5 - Buscar por telefone");
        System.out.println("6 - Consultar tamanho da agenda");
        System.out.println("0 - Finalizar");
    }

    private static void adicionar() {
        System.out.println("Tipo: 1 - Pessoal | 2 - Profissional | 3 - Emergência | 0 - Voltar");
        int tipo = lerInt("Tipo: ");
        if (tipo == 0) {
            return;
        }
        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido.");
            return;
        }
        String nome = lerTexto("Nome: ");
        String email = lerTexto("E-mail: ");
        String telefone = lerTexto("Telefone: ");

        Contato contato;
        switch (tipo) {
            case 1 -> contato = new ContatoPessoal(nome, email, telefone,
                    lerTexto("Data de aniversário: "), lerTexto("Parentesco: "));
            case 2 -> contato = new ContatoProfissional(nome, email, telefone,
                    lerTexto("Empresa: "), lerTexto("Cargo: "));
            default -> {
                int prioridade;
                do {
                    prioridade = lerInt("Grau de prioridade (1 a 5): ");
                } while (prioridade < ContatoEmergencia.PRIORIDADE_MINIMA
                        || prioridade > ContatoEmergencia.PRIORIDADE_MAXIMA);
                contato = new ContatoEmergencia(nome, email, telefone, prioridade,
                        lerTexto("Observação: "));
            }
        }
        agenda.adicionarContato(contato);
        System.out.println("Contato adicionado!");
    }

    private static void remover() {
        String nome = lerTexto("Nome do contato a remover: ");
        if (agenda.removerContato(nome)) {
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    private static void exibirResultado(Contato contato) {
        if (contato == null) {
            System.out.println("Contato não encontrado.");
        } else {
            contato.exibir();
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
}
