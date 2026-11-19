package pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class StatusPedidoTest {

    private StatusPedido[] todos;

    @BeforeEach
    void inicializar() {
        todos = StatusPedido.values();
    }

    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[StatusPedidoTest] teste finalizado: " + info.getDisplayName());
    }

    @Test
    @DisplayName("tem os 5 status do enunciado, na ordem")
    void deveTerOsStatusDoEnunciado() {
        assertEquals(5, todos.length);
        assertEquals(StatusPedido.PENDENTE, todos[0]);
        assertEquals(StatusPedido.CANCELADO, todos[4]);
    }

    @Test
    @DisplayName("regra do enum: ENTREGUE não pode ser alterado")
    void entregueNaoPodeSerAlterado() {
        assertFalse(StatusPedido.ENTREGUE.podeSerAlterado());
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = "ENTREGUE", mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("os demais status podem ser alterados")
    void demaisStatusPodemSerAlterados(StatusPedido status) {
        assertTrue(status.podeSerAlterado());
    }

    @ParameterizedTest(name = "{0} -> proximo = {1}")
    @CsvSource({"PENDENTE, PROCESSANDO", "PROCESSANDO, ENVIADO", "ENVIADO, ENTREGUE"})
    void proximoDoFluxo(StatusPedido atual, StatusPedido esperado) {
        assertEquals(esperado, atual.proximo());
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"ENTREGUE", "CANCELADO"})
    @DisplayName("ENTREGUE e CANCELADO não têm próximo status")
    void semProximo(StatusPedido status) {
        assertNull(status.proximo());
    }
}
