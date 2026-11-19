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

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"ENTREGUE", "CANCELADO"})
    @DisplayName("ENTREGUE e CANCELADO são finais e não transitam para nada")
    void statusFinaisNaoTransitam(StatusPedido final_) {
        assertTrue(final_.isFinal());
        assertNull(final_.proximo());
        for (StatusPedido destino : todos) {
            assertFalse(final_.podeTransicionarPara(destino),
                    final_ + " não deveria ir para " + destino);
        }
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"CRIADO", "PAGO", "EM_PREPARACAO", "ENVIADO"})
    @DisplayName("status em andamento não são finais")
    void statusEmAndamentoNaoSaoFinais(StatusPedido status) {
        assertFalse(status.isFinal());
    }

    @ParameterizedTest(name = "{0} -> proximo = {1}")
    @CsvSource({
        "CRIADO, PAGO",
        "PAGO, EM_PREPARACAO",
        "EM_PREPARACAO, ENVIADO",
        "ENVIADO, ENTREGUE"
    })
    void proximoDoFluxo(StatusPedido atual, StatusPedido esperado) {
        assertEquals(esperado, atual.proximo());
        assertTrue(atual.podeTransicionarPara(esperado));
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = {"CRIADO", "PAGO", "EM_PREPARACAO", "ENVIADO"})
    @DisplayName("qualquer status em andamento pode ser cancelado")
    void podeCancelarEmAndamento(StatusPedido status) {
        assertTrue(status.podeTransicionarPara(StatusPedido.CANCELADO));
    }

    @Test
    @DisplayName("não permite voltar nem pular etapas")
    void naoPermiteVoltarNemPular() {
        assertFalse(StatusPedido.PAGO.podeTransicionarPara(StatusPedido.CRIADO));
        assertFalse(StatusPedido.CRIADO.podeTransicionarPara(StatusPedido.ENVIADO));
        assertFalse(StatusPedido.CRIADO.podeTransicionarPara(StatusPedido.CRIADO));
    }

    @Test
    @DisplayName("destino nulo nunca é permitido")
    void destinoNuloNaoEPermitido() {
        for (StatusPedido status : todos) {
            assertFalse(status.podeTransicionarPara(null));
        }
    }
}
