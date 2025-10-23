package com.br.harrypotter.test.DTOs;

import com.br.harrypotter.dto.BatalhaResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BatalhaResponseDTOTest {

    private BatalhaResponseDTO fixture;

    @BeforeEach
    void setUp() {
        fixture = new BatalhaResponseDTO(
                "Harry Potter",
                "Expelliarmus",
                "Draco Malfoy",
                "Serpensortia",
                "Harry Potter"
        );
    }

    @Test
    void deveCriarBatalhaResponseDTOComValoresCorretos() {
        assertNotNull(fixture);
        assertEquals("Harry Potter", fixture.bruxo1());
        assertEquals("Expelliarmus", fixture.magia1());
        assertEquals("Draco Malfoy", fixture.bruxo2());
        assertEquals("Serpensortia", fixture.magia2());
        assertEquals("Harry Potter", fixture.vencedor());
    }

    @Test
    void devePermitirComparacaoEntreObjetosIguais() {
        BatalhaResponseDTO outro = new BatalhaResponseDTO(
                "Harry Potter",
                "Expelliarmus",
                "Draco Malfoy",
                "Serpensortia",
                "Harry Potter"
        );

        assertEquals(fixture, outro);
        assertEquals(fixture.hashCode(), outro.hashCode());
    }

    @Test
    void deveDetectarObjetosDiferentes() {
        BatalhaResponseDTO diferente = new BatalhaResponseDTO(
                "Hermione Granger",
                "Alohomora",
                "Bellatrix Lestrange",
                "Crucio",
                "Hermione Granger"
        );

        assertNotEquals(fixture, diferente);
    }

    @Test
    void toStringDeveConterInformacoesPrincipais() {
        String texto = fixture.toString();
        assertTrue(texto.contains("Harry Potter"));
        assertTrue(texto.contains("Draco Malfoy"));
        assertTrue(texto.contains("Expelliarmus"));
        assertTrue(texto.contains("Serpensortia"));
    }
}