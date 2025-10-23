package com.br.harrypotter.test.DTOs;

import com.br.harrypotter.dto.BruxoResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes do DTO BruxoResponseDTO")
public class BruxoResponseDTOTest {

    @Test
    @DisplayName("Deve criar BruxoResponseDTO corretamente com dados do fixture padrão")
    void deveCriarBruxoResponsePadrao() {
        BruxoResponseDTO dto = BruxoResponseFixture.criarBruxoResponsePadrao();

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Harry Potter", dto.nome());
        assertEquals("Grifinória", dto.casa());
        assertEquals("Expelliarmus lançado com sucesso!", dto.mensagemFeitico());
    }

    @Test
    @DisplayName("Deve criar BruxoResponseDTO personalizado corretamente")
    void deveCriarBruxoResponsePersonalizado() {
        BruxoResponseDTO dto = BruxoResponseFixture.criarBruxoResponsePersonalizado(
                2L, "Hermione Granger", "Grifinória", "Alohomora executado!"
        );

        assertEquals(2L, dto.id());
        assertEquals("Hermione Granger", dto.nome());
        assertEquals("Grifinória", dto.casa());
        assertEquals("Alohomora executado!", dto.mensagemFeitico());
    }

    @Test
    @DisplayName("Deve comparar corretamente dois BruxoResponseDTO iguais")
    void deveCompararDTOsIguais() {
        BruxoResponseDTO dto1 = new BruxoResponseDTO(1L, "Harry Potter", "Grifinória", "Expelliarmus lançado com sucesso!");
        BruxoResponseDTO dto2 = new BruxoResponseDTO(1L, "Harry Potter", "Grifinória", "Expelliarmus lançado com sucesso!");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Deve gerar toString contendo informações principais do Bruxo")
    void deveGerarToStringCorreto() {
        BruxoResponseDTO dto = BruxoResponseFixture.criarBruxoResponsePadrao();
        String texto = dto.toString();

        assertTrue(texto.contains("Harry Potter"));
        assertTrue(texto.contains("Grifinória"));
        assertTrue(texto.contains("Expelliarmus"));
    }
}
