package com.br.harrypotter.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class MagiaTest {

    @Test
    void deveExecutarLancarFeiticoComSucesso() {
        Magia magia = new Magia() {
            @Override
            public String lancarFeitico() {
                return "Expecto Patronum!";
            }
        };

        String resultado = magia.lancarFeitico();
        assertEquals("Expecto Patronum!", resultado);
    }

    @Test
    void devePermitirUsoComExpressaoLambda() {
        Magia magiaLambda = () -> "Wingardium Leviosa!";
        assertEquals("Wingardium Leviosa!", magiaLambda.lancarFeitico());
    }

    @Test
    void deveGarantirQueMetodoNaoRetornaNulo() {
        Magia magia = () -> "Alohomora!";
        assertNotNull(magia.lancarFeitico());
    }
}