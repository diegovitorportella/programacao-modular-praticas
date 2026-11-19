/** Contatos que podem ser acionados em emergências. Prioridade vai de 1 a 5. */
public class ContatoEmergencia extends Contato {
    public static final int PRIORIDADE_MINIMA = 1;
    public static final int PRIORIDADE_MAXIMA = 5;

    private int grauPrioridade;
    private String observacao;

    public ContatoEmergencia(String nome, String email, String telefone,
                             int grauPrioridade, String observacao) {
        super(nome, email, telefone);
        setGrauPrioridade(grauPrioridade);
        this.observacao = observacao;
    }

    public int getGrauPrioridade() {
        return grauPrioridade;
    }

    public void setGrauPrioridade(int grauPrioridade) {
        if (grauPrioridade < PRIORIDADE_MINIMA || grauPrioridade > PRIORIDADE_MAXIMA) {
            throw new IllegalArgumentException("Grau de prioridade deve estar entre "
                    + PRIORIDADE_MINIMA + " e " + PRIORIDADE_MAXIMA);
        }
        this.grauPrioridade = grauPrioridade;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public void exibir() {
        System.out.println("[Contato de Emergência]");
        super.exibir();
        System.out.println("Prioridade (1-5): " + grauPrioridade);
        System.out.println("Observação: " + observacao);
    }
}
