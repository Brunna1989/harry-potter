package com.br.harrypotter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BatalhaTest {

    private Bruxo bruxo1;
    private Bruxo bruxo2;

    @BeforeEach
    void setUp() {
        bruxo1 = new BruxoGrifinoria();
        bruxo1.setId(1L);
        bruxo1.setNome("Harry Potter");

        bruxo2 = new BruxoGrifinoria();
        bruxo2.setId(2L);
        bruxo2.setNome("Hermione Granger");
    }

    @Test
    void testConstrutorPadrao() {
        Batalha batalha = new Batalha();
        assertNotNull(batalha);
    }

    @Test
    void testConstrutorCompleto() {
        Batalha batalha = new Batalha(
                10L,
                bruxo1,
                bruxo2,
                1L,
                "Harry venceu"
        );

        assertEquals(10L, batalha.getId());
        assertEquals(bruxo1, batalha.getBruxo1());
        assertEquals(bruxo2, batalha.getBruxo2());
        assertEquals(1L, batalha.getVencedorId());
        assertEquals("Harry venceu", batalha.getResultado());
    }

    @Test
    void testBuilder() {
        Batalha batalha = Batalha.builder()
                .id(99L)
                .bruxo1(bruxo1)
                .bruxo2(bruxo2)
                .vencedorId(1L)
                .resultado("Vitória do Harry")
                .build();

        assertEquals(99L, batalha.getId());
        assertEquals(bruxo1, batalha.getBruxo1());
        assertEquals(bruxo2, batalha.getBruxo2());
        assertEquals(1L, batalha.getVencedorId());
        assertEquals("Vitória do Harry", batalha.getResultado());
    }

    @Test
    void testSettersAndGetters() {
        Batalha batalha = new Batalha();

        batalha.setId(5L);
        batalha.setBruxo1(bruxo1);
        batalha.setBruxo2(bruxo2);
        batalha.setVencedorId(2L);
        batalha.setResultado("Draco venceu");

        assertEquals(5L, batalha.getId());
        assertEquals(bruxo1, batalha.getBruxo1());
        assertEquals(bruxo2, batalha.getBruxo2());
        assertEquals(2L, batalha.getVencedorId());
        assertEquals("Draco venceu", batalha.getResultado());
    }

    @Test
    void testToString() {
        Batalha batalha = Batalha.builder()
                .id(1L)
                .bruxo1(bruxo1)
                .bruxo2(bruxo2)
                .vencedorId(1L)
                .resultado("Vitória do Harry")
                .build();

        String toString = batalha.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("bruxo1="));
        assertTrue(toString.contains("bruxo2="));
        assertTrue(toString.contains("vencedorId=1"));
        assertTrue(toString.contains("resultado=Vitória do Harry"));
    }
}
