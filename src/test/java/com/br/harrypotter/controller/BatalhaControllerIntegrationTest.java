package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BatalhaRequestDto;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BatalhaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @BeforeAll
    static void setup() {
        System.out.println("\n===== INICIANDO TESTES DE INTEGRAÇÃO DE BATALHAS =====");
    }

    private static String b1 = "Harry";
    private static String b2 = "Draco";

    @Test
    @Order(1)
    void deveCriarBruxosParaBatalha() {

        BruxoRequestDTO harry = new BruxoRequestDTO(b1, "Grifinoria");
        BruxoRequestDTO draco = new BruxoRequestDTO(b2, "Sonserina");

        ResponseEntity<BruxoResponseDTO> r1 =
                rest.postForEntity("/api/bruxos", harry, BruxoResponseDTO.class);

        ResponseEntity<BruxoResponseDTO> r2 =
                rest.postForEntity("/api/bruxos", draco, BruxoResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, r1.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, r2.getStatusCode());
    }

    @Test
    @Order(2)
    void deveRealizarBatalhaComSucesso() {

        BatalhaRequestDto req = new BatalhaRequestDto(b1, b2);

        ResponseEntity<BatalhaResponseDTO> resposta =
                rest.postForEntity("/api/batalhas", req, BatalhaResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());

        BatalhaResponseDTO body = resposta.getBody();

        Assertions.assertEquals(b1, body.nomeBruxo1());
        Assertions.assertEquals(b2, body.nomeBruxo2());
        Assertions.assertNotNull(body.vencedor());
    }

    @Test
    @Order(3)
    void deveRetornarErroQuandoBruxosForemIguais() {
        BatalhaRequestDto req = new BatalhaRequestDto("Harry", "Harry");

        ResponseEntity<String> resposta =
                rest.postForEntity("/api/batalhas", req, String.class);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resposta.getStatusCode());
    }

    @Test
    @Order(4)
    void deveListarHistoricoDeBatalhas() {

        BruxoRequestDTO harry = new BruxoRequestDTO(b1, "Grifinoria");
        BruxoRequestDTO draco = new BruxoRequestDTO(b2, "Sonserina");

        rest.postForEntity("/api/bruxos", harry, BruxoResponseDTO.class);
        rest.postForEntity("/api/bruxos", draco, BruxoResponseDTO.class);

        BatalhaRequestDto req = new BatalhaRequestDto(b1, b2);
        rest.postForEntity("/api/batalhas", req, BatalhaResponseDTO.class);

        ResponseEntity<BatalhaResponseDTO[]> resposta =
                rest.getForEntity("/api/batalhas/historico", BatalhaResponseDTO[].class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());

        List<BatalhaResponseDTO> lista = Arrays.asList(resposta.getBody());
        Assertions.assertFalse(lista.isEmpty());
    }

    @Test
    @Order(5)
    void deveBuscarBatalhaPorId() {

        BruxoRequestDTO harry = new BruxoRequestDTO(b1, "Grifinoria");
        BruxoRequestDTO draco = new BruxoRequestDTO(b2, "Sonserina");

        rest.postForEntity("/api/bruxos", harry, BruxoResponseDTO.class);
        rest.postForEntity("/api/bruxos", draco, BruxoResponseDTO.class);

        BatalhaRequestDto req = new BatalhaRequestDto(b1, b2);
        rest.postForEntity("/api/batalhas", req, BatalhaResponseDTO.class);

        ResponseEntity<BatalhaResponseDTO[]> historico =
                rest.getForEntity("/api/batalhas/historico", BatalhaResponseDTO[].class);

        Assertions.assertEquals(HttpStatus.OK, historico.getStatusCode());
        Assertions.assertNotNull(historico.getBody());
        Assertions.assertTrue(historico.getBody().length > 0);

        Long id = historico.getBody()[0].id();

        ResponseEntity<BatalhaResponseDTO> resposta =
                rest.getForEntity("/api/batalhas/" + id, BatalhaResponseDTO.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals(id, resposta.getBody().id());
    }






}


