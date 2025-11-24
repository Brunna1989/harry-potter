package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import java.util.Arrays;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BruxoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    private static Long idCriado;

    @BeforeAll
    static void setup() {
        System.out.println("\n===== INICIANDO TESTES DE INTEGRAÇÃO DE CRIAÇÃO DE BRUXCS =====");
    }


    @Test
    @Order(1)
    void deveCriarBruxoComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Harry", "Grifinoria");

        ResponseEntity<BruxoResponseDTO> resposta =
                rest.postForEntity("/api/bruxos", dto, BruxoResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());

        BruxoResponseDTO body = resposta.getBody();
        idCriado = body.id();

        Assertions.assertEquals("Harry", body.nome());
        Assertions.assertEquals("Grifinoria", body.casa());
        Assertions.assertTrue(body.feitico().toLowerCase().contains("expelliarmus"));
    }

    @Test
    @Order(2)
    void deveListarBruxos() {
        ResponseEntity<BruxoResponseDTO[]> resposta =
                rest.getForEntity("/api/bruxos", BruxoResponseDTO[].class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        List<BruxoResponseDTO> lista = Arrays.asList(resposta.getBody());

        Assertions.assertFalse(lista.isEmpty());
    }

    @Test
    @Order(3)
    void deveBuscarBruxoPorId() {
        ResponseEntity<BruxoResponseDTO> resposta =
                rest.getForEntity("/api/bruxos/" + idCriado, BruxoResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals("Harry", resposta.getBody().nome());
    }

    @Test
    @Order(4)
    void deveRetornarErroParaCasaInvalida() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Draco", "LufaLufa");

        ResponseEntity<String> resposta =
                rest.postForEntity("/api/bruxos", dto, String.class);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.getStatusCode());
    }

    @Test
    @Order(5)
    void deveDeletarBruxoPorId() {
        rest.delete("/api/bruxos/" + idCriado);

        ResponseEntity<String> resposta =
                rest.getForEntity("/api/bruxos/" + idCriado, String.class);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.getStatusCode());
    }

    @Test
    @Order(6)
    void deveDeletarTodosBruxos() {
        rest.delete("/api/bruxos");

        ResponseEntity<BruxoResponseDTO[]> resposta =
                rest.getForEntity("/api/bruxos", BruxoResponseDTO[].class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertEquals(0, resposta.getBody().length);
    }
}
