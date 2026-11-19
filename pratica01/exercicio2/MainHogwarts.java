import java.time.LocalDate;
import java.util.Scanner;

public class MainHogwarts {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Aluno[] alunos = new Aluno[10];
        int totalAlunos = 0;
        int opcao = 0;

        System.out.println("=== Sistema do Chapéu Seletor de Hogwarts ===");

        while (opcao != 8) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Listar todos os alunos");
            System.out.println("3. Exibir alunos e total de uma determinada casa");
            System.out.println("4. Exibir alunos por casa");
            System.out.println("5. Exibir alunos maiores de idade");
            System.out.println("6. Exibir alunos menores de idade");
            System.out.println("7. Buscar alunos por sobrenome");
            System.out.println("8. Encerrar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                if (totalAlunos >= 10) {
                    System.out.println("Capacidade máxima de cadastros (10) atingida.");
                    continue;
                }

                System.out.print("\nNome completo do aluno: ");
                String nome = scanner.nextLine();

                System.out.print("Ano de nascimento (ex: 2005): ");
                int ano = scanner.nextInt();
                System.out.print("Mês de nascimento (1-12): ");
                int mes = scanner.nextInt();
                System.out.print("Dia de nascimento (1-31): ");
                int dia = scanner.nextInt();
                LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

                System.out.print("Coragem (0 a 10): ");
                double coragem = scanner.nextDouble();
                System.out.print("Inteligência (0 a 10): ");
                double inteligencia = scanner.nextDouble();
                System.out.print("Ambição (0 a 10): ");
                double ambicao = scanner.nextDouble();
                System.out.print("Lealdade (0 a 10): ");
                double lealdade = scanner.nextDouble();
                System.out.print("Estratégia (0 a 10): ");
                double estrategia = scanner.nextDouble();
                System.out.print("Criatividade (0 a 10): ");
                double criatividade = scanner.nextDouble();
                scanner.nextLine();

                Aluno aluno = new Aluno(nome, dataNascimento, coragem, inteligencia, ambicao, lealdade, estrategia, criatividade);
                aluno.calcularCasa();
                aluno.gerarNomeUsuario();
                aluno.gerarCodigoMatricula(totalAlunos + 1);
                
                alunos[totalAlunos] = aluno;
                totalAlunos++;
                System.out.println("Aluno cadastrado com sucesso!");

            } else if (opcao == 2) {
                for (int i = 0; i < totalAlunos; i++) {
                    alunos[i].exibirInformacoes();
                }
            } else if (opcao == 3) {
                System.out.print("Informe a casa (Grifinória, Sonserina, Corvinal, Lufa-Lufa): ");
                String casaBusca = scanner.nextLine();
                int contadorCasa = 0;
                for (int i = 0; i < totalAlunos; i++) {
                    if (alunos[i].verificacaoCasa(casaBusca)) {
                        alunos[i].exibirInformacoes();
                        contadorCasa++;
                    }
                }
                System.out.println("Total de alunos na " + casaBusca.toUpperCase() + ": " + contadorCasa);
            } else if (opcao == 4) {
                String[] casas = {"Grifinória", "Sonserina", "Corvinal", "Lufa-Lufa"};
                for (String c : casas) {
                    System.out.println("\n--- " + c.toUpperCase() + " ---");
                    for (int i = 0; i < totalAlunos; i++) {
                        if (alunos[i].verificacaoCasa(c)) {
                            System.out.println("- " + alunos[i].getNome());
                        }
                    }
                }
            } else if (opcao == 5) {
                System.out.println("\n--- Alunos Maiores de Idade ---");
                for (int i = 0; i < totalAlunos; i++) {
                    if (alunos[i].verificarMaioridadeMagica()) {
                        alunos[i].exibirInformacoes();
                    }
                }
            } else if (opcao == 6) {
                System.out.println("\n--- Alunos Menores de Idade ---");
                for (int i = 0; i < totalAlunos; i++) {
                    if (!alunos[i].verificarMaioridadeMagica()) {
                        alunos[i].exibirInformacoes();
                    }
                }
            } else if (opcao == 7) {
                System.out.print("Informe o sobrenome para busca: ");
                String sobrenome = scanner.nextLine();
                for (int i = 0; i < totalAlunos; i++) {
                    if (alunos[i].verificarPresencaPalavra(sobrenome)) {
                        alunos[i].exibirInformacoes();
                    }
                }
            } else if (opcao == 8) {
                System.out.println("Encerrando o sistema. Dados de todos os alunos cadastrados:");
                for (int i = 0; i < totalAlunos; i++) {
                    alunos[i].exibirInformacoes();
                }
                break;
            } else {
                System.out.println("Opção inválida.");
            }
        }
        scanner.close();
    }
}