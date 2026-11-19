/** Ingresso tradicional: sem desconto nem benefícios. */
public class IngressoComum extends Ingresso {

    public IngressoComum(int codigo, String nomeEvento, String setor, double valorBase) {
        super(codigo, nomeEvento, setor, valorBase);
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase();
    }

    @Override
    public String obterBeneficios() {
        return "Nenhum benefício adicional.";
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("[Ingresso Comum]");
        super.exibirInformacoes();
        System.out.printf("Valor final: R$ %.2f%n", calcularValorFinal());
    }
}
