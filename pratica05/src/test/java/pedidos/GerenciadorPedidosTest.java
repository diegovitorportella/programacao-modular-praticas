package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import pedidos.excecoes.PedidoInvalidoException;

class GerenciadorPedidosTest {

    private GerenciadorPedidos gerenciador;
    private PedidoEletronico eletronico;
    private PedidoRoupa roupa;
    private PedidoAlimento alimento;

    /** Cria o gerenciador com um pedido de cada tipo antes de cada teste. */
    @BeforeEach
    void inicializar() throws Exception {
        gerenciador = new GerenciadorPedidos();
        eletronico = new PedidoEletronico(gerenciador.gerarId(), "Ana", 150);
        roupa = new PedidoRoupa(gerenciador.gerarId(), "Bia", 300);
        alimento = new PedidoAlimento(gerenciador.gerarId(), "Caio", 40);
        gerenciador.adicionar(eletronico);
        gerenciador.adicionar(roupa);
        gerenciador.adicionar(alimento);
    }

    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[GerenciadorPedidosTest] teste finalizado: " + info.getDisplayName());
    }

    @Test
    @DisplayName("gerarId é sequencial a partir de 1")
    void gerarIdDeveSerSequencial() {
        assertEquals(1, eletronico.getId());
        assertEquals(2, roupa.getId());
        assertEquals(3, alimento.getId());
        assertEquals(4, gerenciador.gerarId());
    }

    @Test
    @DisplayName("buscarPorId encontra o pedido ou retorna null")
    void buscarPorId() {
        assertSame(roupa, gerenciador.buscarPorId(2));
        assertNull(gerenciador.buscarPorId(99));
        assertEquals(3, gerenciador.getQuantidade());
    }

    // ---------- alterar status ----------

    @Test
    @DisplayName("alterarStatus muda o status do pedido encontrado")
    void alterarStatusDeveMudarStatus() throws Exception {
        assertTrue(gerenciador.alterarStatus(1, StatusPedido.ENVIADO));
        assertEquals(StatusPedido.ENVIADO, eletronico.getStatus());
    }

    @Test
    @DisplayName("alterarStatus de id inexistente retorna false")
    void alterarStatusDeIdInexistente() throws Exception {
        assertFalse(gerenciador.alterarStatus(99, StatusPedido.ENVIADO));
    }

    @Test
    @DisplayName("alterarStatus de pedido ENTREGUE lança PedidoInvalidoException")
    void alterarStatusDePedidoEntregue() throws Exception {
        gerenciador.alterarStatus(1, StatusPedido.ENTREGUE);
        assertThrows(PedidoInvalidoException.class,
                () -> gerenciador.alterarStatus(1, StatusPedido.CANCELADO));
        assertEquals(StatusPedido.ENTREGUE, eletronico.getStatus());
    }

    // ---------- processar todos (Iterator) ----------

    @Test
    @DisplayName("processarTodos avança todos os pedidos")
    void processarTodosDeveAvancarTodos() {
        List<String> relatorio = gerenciador.processarTodos();
        assertEquals(3, relatorio.size());
        assertEquals(StatusPedido.PROCESSANDO, eletronico.getStatus());
        assertEquals(StatusPedido.PROCESSANDO, roupa.getStatus());
        assertEquals(StatusPedido.PROCESSANDO, alimento.getStatus());
    }

    @Test
    @DisplayName("pedido inválido não interrompe o processamento dos demais")
    void processarTodosDeveContinuarAposErro() throws Exception {
        gerenciador.alterarStatus(1, StatusPedido.CANCELADO);
        List<String> relatorio = gerenciador.processarTodos();

        assertEquals(3, relatorio.size());
        assertTrue(relatorio.get(0).startsWith("Erro"));
        assertEquals(StatusPedido.CANCELADO, eletronico.getStatus());
        assertEquals(StatusPedido.PROCESSANDO, roupa.getStatus());
        assertEquals(StatusPedido.PROCESSANDO, alimento.getStatus());
    }

    @Test
    @DisplayName("pedido ENTREGUE gera erro no relatório e mantém o status")
    void processarTodosComPedidoEntregue() throws Exception {
        gerenciador.alterarStatus(3, StatusPedido.ENTREGUE);
        List<String> relatorio = gerenciador.processarTodos();

        assertTrue(relatorio.get(2).startsWith("Erro"));
        assertEquals(StatusPedido.ENTREGUE, alimento.getStatus());
    }

    // ---------- remover cancelados (Iterator) ----------

    @Test
    @DisplayName("removerCancelados remove só os cancelados")
    void removerCancelados() throws Exception {
        gerenciador.alterarStatus(1, StatusPedido.CANCELADO);
        gerenciador.alterarStatus(3, StatusPedido.CANCELADO);

        assertEquals(2, gerenciador.removerCancelados());
        assertEquals(1, gerenciador.getQuantidade());
        assertNull(gerenciador.buscarPorId(1));
        assertSame(roupa, gerenciador.buscarPorId(2));
    }

    @Test
    @DisplayName("removerCancelados sem cancelados não remove nada")
    void removerCanceladosSemCancelados() {
        assertEquals(0, gerenciador.removerCancelados());
        assertEquals(3, gerenciador.getQuantidade());
    }

    // ---------- listagens ----------

    @Test
    @DisplayName("listarNaoCancelados não inclui cancelados")
    void listarNaoCancelados() throws Exception {
        gerenciador.alterarStatus(2, StatusPedido.CANCELADO);
        List<Pedido> lista = gerenciador.listarNaoCancelados();

        assertEquals(List.of(eletronico, alimento), lista);
        assertEquals(3, gerenciador.getQuantidade()); // listar não remove
    }

    @Test
    @DisplayName("listarEntregaveis exclui alimento")
    void listarEntregaveisExcluiAlimento() {
        assertEquals(List.of(eletronico, roupa), gerenciador.listarEntregaveis());
    }

    @Test
    @DisplayName("listarEntregaveis exclui cancelados")
    void listarEntregaveisExcluiCancelados() throws Exception {
        gerenciador.alterarStatus(1, StatusPedido.CANCELADO);
        assertEquals(List.of(roupa), gerenciador.listarEntregaveis());
    }

    @Test
    @DisplayName("gerenciador vazio retorna listas vazias")
    void gerenciadorVazio() {
        GerenciadorPedidos vazio = new GerenciadorPedidos();
        assertTrue(vazio.listarNaoCancelados().isEmpty());
        assertTrue(vazio.listarEntregaveis().isEmpty());
        assertTrue(vazio.processarTodos().isEmpty());
        assertEquals(0, vazio.removerCancelados());
    }
}
