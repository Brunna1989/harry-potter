package com.br.harrypotter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe BruxoGrifinoria")
public class BruxoGrifinoriaTest {

    private BruxoGrifinoria bruxo;

    @BeforeEach
    void setUp() {
        bruxo = new BruxoGrifinoria("Harry Potter");
    }

    @Test
    @DisplayName("Deve criar BruxoGrifinoria corretamente com nome e casa padrão")
    void deveCriarBruxoGrifinoriaCorretamente() {
        assertNotNull(bruxo);
        assertEquals("Harry Potter", bruxo.getNome());
        assertEquals("Grifinória", bruxo.getCasa());
        assertNull(bruxo.getId());
    }

    @Test
    @DisplayName("Deve lançar feitiço corretamente para bruxo da Grifinória")
    void deveLancarFeiticoCorretamente() {
        String resultado = bruxo.lancarFeitico();

        assertNotNull(resultado);
        assertTrue(resultado.contains("Expelliarmus"));
        assertTrue(resultado.contains("Grifinória"));
        assertTrue(resultado.contains("Harry Potter"));
    }

    @Test
    @DisplayName("toString deve conter nome e casa")
    void deveGerarToStringCorreto() {
        String texto = bruxo.toString();

        assertTrue(texto.contains("Harry Potter"));
        assertTrue(texto.contains("Grifinória"));
    }
}
