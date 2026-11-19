import java.util.ArrayList;
import java.util.List;

/** Controla os ingressos vendidos. Trabalha sempre com o tipo Ingresso (polimorfismo). */
public class Bilheteria {
    private final List<Ingresso> ingressos = new ArrayList<>();

    /** Adiciona o ingresso. Retorna false se já existir um ingresso com o mesmo código. */
    public boolean adicionarIngresso(Ingresso ingresso) {
        if (buscarIngresso(ingresso.getCodigo()) != null) {
            return false;
        }
        ingressos.add(ingresso);
        return true;
    }

    /** Remove pelo código. Retorna true se o ingresso existia. */
    public boolean removerIngresso(int codigo) {
        Ingresso ingresso = buscarIngresso(codigo);
        if (ingresso == null) {
            return false;
        }
        ingressos.remove(ingresso);
        return true;
    }

    /** Retorna o ingresso com o código, ou null se não existir. */
    public Ingresso buscarIngresso(int codigo) {
        for (Ingresso i : ingressos) {
            if (i.getCodigo() == codigo) {
                return i;
            }
        }
        return null;
    }

    public void listarIngressos() {
        if (ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso cadastrado.");
            return;
        }
        for (Ingresso i : ingressos) {
            i.exibirInformacoes();
            System.out.println("-----------------------");
        }
    }

    public double calcularArrecadacaoTotal() {
        double total = 0;
        for (Ingresso i : ingressos) {
            total += i.calcularValorFinal();
        }
        return total;
    }

    public void listarBeneficios() {
        if (ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso cadastrado.");
            return;
        }
        for (Ingresso i : ingressos) {
            System.out.println("Ingresso " + i.getCodigo() + " (" + i.getNomeEvento() + "): "
                    + i.obterBeneficios());
        }
    }

    public int getQuantidade() {
        return ingressos.size();
    }
}
