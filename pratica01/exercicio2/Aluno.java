import java.time.LocalDate;

public class Aluno {
    private String nome;
    private LocalDate dataNascimento;
    private int idade;
    private double coragem;
    private double inteligencia;
    private double ambicao;
    private double lealdade;
    private double estrategia; 
    private double criatividade;
    private String casa;
    private String codigoMatricula;
    private String nomeUsuario;

    public Aluno(String nome, LocalDate dataNascimento, double coragem, double inteligencia, double ambicao, double lealdade, double estrategia, double criatividade) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.coragem = coragem;
        this.inteligencia = inteligencia;
        this.ambicao = ambicao;
        this.lealdade = lealdade;
        this.estrategia = estrategia;
        this.criatividade = criatividade;
        calcularIdade();
    }

    public void calcularIdade() {
        LocalDate hoje = LocalDate.now();
        int idadeCalculada = hoje.getYear() - this.dataNascimento.getYear();
        if (hoje.getMonthValue() < this.dataNascimento.getMonthValue() || 
           (hoje.getMonthValue() == this.dataNascimento.getMonthValue() && hoje.getDayOfMonth() < this.dataNascimento.getDayOfMonth())) {
            idadeCalculada--;
        }
        this.idade = idadeCalculada;
    }

    public boolean verificarMaioridadeMagica() {
        return this.idade >= 17;
    }

    public String formatarCasa() {
        if (this.casa != null) {
            return this.casa.toUpperCase();
        }
        return "NÃO DEFINIDA";
    }

    public void gerarNomeUsuario() {
        String[] partesNome = this.nome.trim().split("\\s+");
        String primeiraLetra = String.valueOf(this.nome.charAt(0)).toLowerCase();
        String sobrenome = partesNome.length > 1 ? partesNome[partesNome.length - 1].toLowerCase() : "";
        this.nomeUsuario = primeiraLetra + sobrenome;
    }

    public void gerarCodigoMatricula(int posicaoCadastro) {
        StringBuilder iniciais = new StringBuilder();
        String[] partesNome = this.nome.trim().split("\\s+");
        for (String parte : partesNome) {
            if (!parte.isEmpty()) {
                iniciais.append(parte.charAt(0));
            }
        }
        int anoAtual = LocalDate.now().getYear();
        this.codigoMatricula = String.format("%s-%d-%02d", iniciais.toString().toUpperCase(), anoAtual, posicaoCadastro);
    }

    public void calcularCasa() {
        double ptGrifinoria = (2 * coragem) + lealdade;
        double ptSonserina = (2 * ambicao) + estrategia;
        double ptCorvinal = (2 * inteligencia) + criatividade;
        double ptLufaLufa = ((2 * lealdade) + coragem) / 3.0;

        double maxPontos = ptGrifinoria;
        this.casa = "Grifinória";

        if (ptSonserina > maxPontos) {
            maxPontos = ptSonserina;
            this.casa = "Sonserina";
        }
        if (ptCorvinal > maxPontos) {
            maxPontos = ptCorvinal;
            this.casa = "Corvinal";
        }
        if (ptLufaLufa > maxPontos) {
            this.casa = "Lufa-Lufa";
        }
    }

    public boolean verificacaoCasa(String casaInformada) {
        return this.casa != null && this.casa.equalsIgnoreCase(casaInformada);
    }

    public boolean verificarPresencaPalavra(String palavra) {
        String[] partesNome = this.nome.trim().split("\\s+");
        if (partesNome.length > 1) {
            String sobrenome = partesNome[partesNome.length - 1];
            return sobrenome.toLowerCase().contains(palavra.toLowerCase());
        }
        return false;
    }

    public void exibirInformacoes() {
        System.out.println("\n--- Informações do Aluno ---");
        System.out.println("Matrícula: " + this.codigoMatricula);
        System.out.println("Nome de Usuário: " + this.nomeUsuario);
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade + " anos (Maioridade Mágica: " + (verificarMaioridadeMagica() ? "Sim" : "Não") + ")");
        System.out.println("Casa selecionada: " + formatarCasa());
        System.out.println("----------------------------");
    }

    public String getNome() { return nome; }
    public String getCasa() { return casa; }
}