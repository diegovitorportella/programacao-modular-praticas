import java.util.Scanner;

public class MainPessoa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Calculadora de IMC ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Sobrenome: ");
        String sobrenome = scanner.nextLine();
        
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        
        System.out.print("Altura (em metros, ex: 1.75): ");
        double altura = scanner.nextDouble();
        
        System.out.print("Peso (em kg, ex: 70.5): ");
        double peso = scanner.nextDouble();

        Pessoa pessoa = new Pessoa(nome, sobrenome, idade, altura, peso);
        pessoa.calculaIMC();

        System.out.printf("\nResultado para %s %s:\n", pessoa.getNome(), pessoa.getSobrenome());
        System.out.printf("IMC: %.2f\n", pessoa.getImc());
        System.out.println("Classificação: " + pessoa.informaObesidade());

        scanner.close();
    }
}