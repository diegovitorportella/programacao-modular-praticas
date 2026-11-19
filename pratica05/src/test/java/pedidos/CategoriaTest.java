package pedidos;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class CategoriaTest {

    private Categoria[] categorias;

    @BeforeEach
    void inicializar() {
        categorias = Categoria.values();
    }

    @AfterEach
    void finalizar(TestInfo info) {
        System.out.println("[CategoriaTest] teste finalizado: " + info.getDisplayName());
    }

    @Test
    @DisplayName("tem as 3 categorias do enunciado")
    void deveTerAsCategoriasDoEnunciado() {
        assertArrayEquals(
                new Categoria[] {Categoria.ELETRONICO, Categoria.ROUPA, Categoria.ALIMENTO},
                categorias);
    }

    @Test
    @DisplayName("valueOf converte o nome")
    void valueOfDeveConverterNome() {
        assertEquals(Categoria.ROUPA, Categoria.valueOf("ROUPA"));
    }

    @Test
    @DisplayName("valueOf de nome inexistente lança IllegalArgumentException")
    void valueOfInvalidoDeveLancarExcecao() {
        assertThrows(IllegalArgumentException.class, () -> Categoria.valueOf("BRINQUEDO"));
    }
}
