/** Ingresso VIP: acréscimo de 80% e acesso privilegiado. */
public class IngressoVIP extends Ingresso {
    public static final double ACRESCIMO = 0.80;

    private boolean acessoBackstage;
    private int numeroLounge;

    public IngressoVIP(int codigo, String nomeEvento, String setor, double valorBase,
                       boolean acessoBackstage, int numeroLounge) {
        super(codigo, nomeEvento, setor, valorBase);
        this.acessoBackstage = acessoBackstage;
        this.numeroLounge = numeroLounge;
    }

    public boolean isAcessoBackstage() {
        return acessoBackstage;
    }

    public void setAcessoBackstage(boolean acessoBackstage) {
        this.acessoBackstage = acessoBackstage;
    }

    public int getNumeroLounge() {
        return numeroLounge;
    }

    public void setNumeroLounge(int numeroLounge) {
        this.numeroLounge = numeroLounge;
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 + ACRESCIMO);
    }

    @Override
    public String obterBeneficios() {
        String beneficios = "Acesso ao lounge nº " + numeroLounge + " e entrada prioritária.";
        if (acessoBackstage) {
            beneficios += " Acesso ao backstage.";
        }
        return beneficios;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("[Ingresso VIP]");
        super.exibirInformacoes();
        System.out.println("Lounge: " + numeroLounge);
        System.out.println("Backstage: " + (acessoBackstage ? "sim" : "não"));
        System.out.printf("Valor final: R$ %.2f%n", calcularValorFinal());
    }
}
