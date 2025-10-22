package test.DTOs;

import com.br.harrypotter.dto.BruxoRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do DTO BruxoRequestDTO")
public class BruxoRequestDTOTest {

    @Test
    @DisplayName("Deve criar um BruxoRequestDTO corretamente com dados do fixture padrão")
    void deveCriarBruxoComFixturePadrao() {
        BruxoRequestDTO bruxo = BruxoFixture.criarBruxoPadrao();

        assertNotNull(bruxo);
        assertEquals("Harry Potter", bruxo.nome());
        assertEquals("Grifinória", bruxo.casa());
    }

    @Test
    @DisplayName("Deve criar um BruxoRequestDTO personalizado corretamente")
    void deveCriarBruxoPersonalizado() {
        BruxoRequestDTO bruxo = BruxoFixture.criarBruxoPersonalizado("Hermione Granger", "Grifinória");

        assertEquals("Hermione Granger", bruxo.nome());
        assertEquals("Grifinória", bruxo.casa());
    }

    @Test
    @DisplayName("Deve comparar corretamente dois BruxoRequestDTO iguais")
    void deveCompararBruxosIguais() {
        BruxoRequestDTO bruxo1 = new BruxoRequestDTO("Harry Potter", "Grifinória");
        BruxoRequestDTO bruxo2 = new BruxoRequestDTO("Harry Potter", "Grifinória");

        assertEquals(bruxo1, bruxo2);
        assertEquals(bruxo1.hashCode(), bruxo2.hashCode());
    }

    @Test
    @DisplayName("Deve gerar toString contendo os campos nome e casa")
    void deveGerarToStringCorreto() {
        BruxoRequestDTO bruxo = BruxoFixture.criarBruxoPadrao();
        String toString = bruxo.toString();

        assertTrue(toString.contains("Harry Potter"));
        assertTrue(toString.contains("Grifinória"));
    }
}
