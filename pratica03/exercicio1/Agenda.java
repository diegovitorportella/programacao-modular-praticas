import java.util.ArrayList;
import java.util.List;

/** Agenda de contatos de qualquer tipo (polimorfismo via Contato). */
public class Agenda {
    private final List<Contato> contatos = new ArrayList<>();
    private int quantidade = 0;

    public void adicionarContato(Contato contato) {
        contatos.add(contato);
        quantidade = contatos.size();
    }

    /** Remove o primeiro contato com o nome informado. Retorna true se encontrou e removeu. */
    public boolean removerContato(String nome) {
        Contato contato = buscarPorNome(nome);
        if (contato == null) {
            return false;
        }
        contatos.remove(contato);
        quantidade = contatos.size();
        return true;
    }

    public Contato buscarPorNome(String nome) {
        for (Contato c : contatos) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    public Contato buscarPorEmail(String email) {
        for (Contato c : contatos) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    public Contato buscarPorTelefone(String telefone) {
        for (Contato c : contatos) {
            if (c.getTelefone().equals(telefone)) {
                return c;
            }
        }
        return null;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public List<Contato> getContatos() {
        return new ArrayList<>(contatos);
    }
}
