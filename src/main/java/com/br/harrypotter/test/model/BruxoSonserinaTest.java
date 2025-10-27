package com.br.harrypotter.test.model;

import com.br.harrypotter.model.BruxoSonserina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BruxoSonserinaTest {

    private BruxoSonserina bruxoPadrao;
    private BruxoSonserina bruxoComNome;

    @BeforeEach
    void setUp() {
        bruxoPadrao = new BruxoSonserina();
        bruxoComNome = new BruxoSonserina("Draco Malfoy");
    }

    @Test
    void deveCriarBruxoPadraoSemErro() {
        assertNotNull(bruxoPadrao);
    }

    @Test
    void deveCriarBruxoComNomeCorreto() {
        assertEquals("Draco Malfoy", bruxoComNome.getNome());
    }

    @Test
    void deveTerCasaSonserina() {
        assertEquals("Sonserina", bruxoComNome.getCasa());
    }

    @Test
    void deveLancarFeiticoCorretamente() {
        String esperado = "Serpensortia!";
        assertEquals(esperado, bruxoComNome.lancarFeitico());
    }

    @Test
    void devePermitirAlterarNomeDoBruxo() {
        bruxoComNome.setNome("Severo Snape");
        assertEquals("Severo Snape", bruxoComNome.getNome());
    }
}
