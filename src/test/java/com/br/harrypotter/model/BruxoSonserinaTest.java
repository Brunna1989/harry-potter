package com.br.harrypotter.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BruxoSonserinaTest {

    @Test
    void deveRetornarCasaCorreta() {
        BruxoSonserina bruxo = new BruxoSonserina(2L, "Draco Malfoy");

        assertEquals("Sonserina", bruxo.getCasa());
    }

    @Test
    void deveLancarFeiticoCorretamente() {
        BruxoSonserina bruxo = new BruxoSonserina(2L, "Draco Malfoy");

        String esperado = "Serpensortia! O bruxo da Sonserina lançou seu feitiço!";
        assertEquals(esperado, bruxo.lancarFeitico());
    }

    @Test
    void deveCriarComBuilder() {
        BruxoSonserina bruxo = BruxoSonserina.builder()
                .id(20L)
                .nome("Severus Snape")
                .build();

        assertNotNull(bruxo);
        assertEquals(20L, bruxo.getId());
        assertEquals("Severus Snape", bruxo.getNome());
        assertEquals("Sonserina", bruxo.getCasa());
    }
}
