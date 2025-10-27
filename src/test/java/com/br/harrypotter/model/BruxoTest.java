package com.br.harrypotter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe abstrata Bruxo")
public class BruxoTest {

    private Bruxo bruxo;

    @BeforeEach
    void setUp() {
        bruxo = BruxoFixture.criarBruxoPadrao();
    }

    @Test
    @DisplayName("Deve criar um bruxo com valores corretos")
    void deveCriarBruxoComValoresCorretos() {
        assertNotNull(bruxo);
        assertEquals(1L, bruxo.getId());
        assertEquals("Harry Potter", bruxo.getNome());
    }

    @Test
    @DisplayName("Deve permitir alteração dos atributos via setters")
    void deveAlterarAtributosCorretamente() {
        bruxo.setId(2L);
        bruxo.setNome("Hermione Granger");

        assertEquals(2L, bruxo.getId());
        assertEquals("Hermione Granger", bruxo.getNome());
    }

    @Test
    @DisplayName("Deve executar o método abstrato lancarFeitico na subclasse concreta")
    void deveExecutarMetodoLancarFeitico() {
        String resultado = bruxo.lancarFeitico();
        assertEquals("Feitiço lançado com sucesso!", resultado);
    }

    @Test
    @DisplayName("toString deve conter nome e id do bruxo")
    void deveGerarToStringCorreto() {
        String texto = bruxo.toString();
        assertTrue(texto.contains("Harry Potter"));
        assertTrue(texto.contains("id=1"));
    }

    @Test
    @DisplayName("Construtor padrão deve criar um bruxo vazio")
    void deveCriarBruxoVazioComConstrutorPadrao() {
        Bruxo bruxoVazio = new BruxoFixture.BruxoFake();
        assertNotNull(bruxoVazio.getId());
        assertNotNull(bruxoVazio.getNome());
    }
}
