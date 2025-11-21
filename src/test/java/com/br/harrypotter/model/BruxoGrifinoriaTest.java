package com.br.harrypotter.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BruxoGrifinoriaTest {

    @Test
    void deveRetornarCasaCorreta() {
        BruxoGrifinoria bruxo = new BruxoGrifinoria(1L, "Harry Potter");

        assertEquals("Grifinoria", bruxo.getCasa());
    }

    @Test
    void deveLancarFeiticoCorretamente() {
        BruxoGrifinoria bruxo = new BruxoGrifinoria(1L, "Harry Potter");

        String esperado = "Expelliarmus! O bruxo da Grifinoria lançou seu feitiço!";
        assertEquals(esperado, bruxo.lancarFeitico());
    }

    @Test
    void deveCriarComBuilder() {
        BruxoGrifinoria bruxo = BruxoGrifinoria.builder()
                .id(10L)
                .nome("Hermione Granger")
                .build();

        assertNotNull(bruxo);
        assertEquals(10L, bruxo.getId());
        assertEquals("Hermione Granger", bruxo.getNome());
        assertEquals("Grifinoria", bruxo.getCasa());
    }
}
