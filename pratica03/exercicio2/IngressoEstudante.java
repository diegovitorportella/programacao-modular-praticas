/** Ingresso de estudante: 50% de desconto (meia-entrada). */
public class IngressoEstudante extends Ingresso {
    public static final double DESCONTO = 0.50;

    private String instituicaoEnsino;

    public IngressoEstudante(int codigo, String nomeEvento, String setor, double valorBase,
                             String instituicaoEnsino) {
        super(codigo, nomeEvento, setor, valorBase);
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public String getInstituicaoEnsino() {
        return instituicaoEnsino;
    }

    public void setInstituicaoEnsino(String instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 - DESCONTO);
    }

    @Override
    public String obterBeneficios() {
        return "Direito à meia-entrada (" + instituicaoEnsino + ").";
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("[Ingresso Estudante]");
        super.exibirInformacoes();
        System.out.println("Instituição: " + instituicaoEnsino);
        System.out.printf("Valor final: R$ %.2f%n", calcularValorFinal());
    }
}
