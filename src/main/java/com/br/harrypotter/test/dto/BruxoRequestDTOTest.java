package com.br.harrypotter.test.dto;

import com.br.harrypotter.dto.BruxoRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do DTO BruxoRequestDTO")
public class BruxoRequestDTOTest {

    @Test
    @DisplayName("Deve criar um BruxoRequestDTO corretamente")
    void deveCriarBruxoRequestDTO() {
        BruxoRequestDTO bruxo = new BruxoRequestDTO("Harry Potter", "Grifinória");

        assertNotNull(bruxo);
        assertEquals("Harry Potter", bruxo.nome());
        assertEquals("Grifinória", bruxo.casa());
    }

    @Test
    @DisplayName("Deve comparar dois BruxoRequestDTO iguais")
    void deveCompararBruxosIguais() {
        BruxoRequestDTO bruxo1 = new BruxoRequestDTO("Hermione Granger", "Grifinória");
        BruxoRequestDTO bruxo2 = new BruxoRequestDTO("Hermione Granger", "Grifinória");

        assertEquals(bruxo1, bruxo2);
        assertEquals(bruxo1.hashCode(), bruxo2.hashCode());
    }

    @Test
    @DisplayName("Deve gerar toString contendo os campos nome e casa")
    void deveGerarToStringCorreto() {
        BruxoRequestDTO bruxo = new BruxoRequestDTO("Ron Weasley", "Grifinória");
        String toString = bruxo.toString();

        assertTrue(toString.contains("Ron Weasley"));
        assertTrue(toString.contains("Grifinória"));
    }

    @Test
    @DisplayName("Deve permitir criar DTOs personalizados")
    void deveCriarDTOsPersonalizados() {
        BruxoRequestDTO bruxo = new BruxoRequestDTO("Draco Malfoy", "Sonserina");

        assertEquals("Draco Malfoy", bruxo.nome());
        assertEquals("Sonserina", bruxo.casa());
    }
}
