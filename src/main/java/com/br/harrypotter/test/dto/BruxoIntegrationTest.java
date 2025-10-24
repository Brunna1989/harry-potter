package com.br.harrypotter.test.dto;

import com.br.harrypotter.Application;
import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false"
})
@DisplayName("Testes de Integração - Bruxos (endpoints)")
public class BruxoIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Deve criar bruxos, listar e executar batalha com sucesso")
    public void deveCriarListarEExecutarBatalha() throws Exception {
        BruxoRequestDTO bruxo1 = new BruxoRequestDTO("Harry Potter", "Grifinória");
        BruxoRequestDTO bruxo2 = new BruxoRequestDTO("Draco Malfoy", "Sonserina");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request1 = new HttpEntity<>(mapper.writeValueAsString(bruxo1), headers);
        ResponseEntity<BruxoResponseDTO> resp1 = rest.postForEntity("/bruxos", request1, BruxoResponseDTO.class);
        assertEquals(HttpStatus.OK, resp1.getStatusCode(), "Criar bruxo1 deve retornar 200 OK");
        assertNotNull(resp1.getBody());
        assertEquals("Harry Potter", resp1.getBody().nome());

        HttpEntity<String> request2 = new HttpEntity<>(mapper.writeValueAsString(bruxo2), headers);
        ResponseEntity<BruxoResponseDTO> resp2 = rest.postForEntity("/bruxos", request2, BruxoResponseDTO.class);
        assertEquals(HttpStatus.OK, resp2.getStatusCode(), "Criar bruxo2 deve retornar 200 OK");
        assertNotNull(resp2.getBody());
        assertEquals("Draco Malfoy", resp2.getBody().nome());

        ResponseEntity<String> listResp = rest.getForEntity("/bruxos", String.class);
        assertEquals(HttpStatus.OK, listResp.getStatusCode());
        String body = listResp.getBody();
        assertNotNull(body);
        assertTrue(body.contains("Harry Potter"));
        assertTrue(body.contains("Draco Malfoy"));

        ResponseEntity<List<BruxoResponseDTO>> typedListResp = rest.exchange(
                "/bruxos",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BruxoResponseDTO>>() {}
        );
        assertEquals(HttpStatus.OK, typedListResp.getStatusCode());
        List<BruxoResponseDTO> lista = typedListResp.getBody();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(b -> "Harry Potter".equals(b.nome())));
        assertTrue(lista.stream().anyMatch(b -> "Draco Malfoy".equals(b.nome())));

        String urlBatalha = "/bruxos/batalha?bruxo1={b1}&bruxo2={b2}";
        ResponseEntity<BatalhaResponseDTO> batalhaResp = rest.getForEntity(urlBatalha, BatalhaResponseDTO.class, "Harry Potter", "Draco Malfoy");
        assertEquals(HttpStatus.OK, batalhaResp.getStatusCode());
        BatalhaResponseDTO resultado = batalhaResp.getBody();
        assertNotNull(resultado);
        assertEquals("Harry Potter", resultado.bruxo1());
        assertEquals("Draco Malfoy", resultado.bruxo2());
        assertNotNull(resultado.magia1());
        assertNotNull(resultado.magia2());
        assertNotNull(resultado.vencedor());
        assertTrue(resultado.vencedor().equals("Harry Potter") || resultado.vencedor().equals("Draco Malfoy"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar batalhar com nomes iguais ou inexistentes")
    public void deveRetornarBadRequestParaParametrosInvalidos() throws Exception {
        BruxoRequestDTO bruxo = new BruxoRequestDTO("Luna Lovegood", "Grifinória");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        rest.postForEntity("/bruxos", new HttpEntity<>(mapper.writeValueAsString(bruxo), headers), BruxoResponseDTO.class);

        ResponseEntity<String> respIguais = rest.getForEntity("/bruxos/batalha?bruxo1={b}&bruxo2={b}", String.class, "Luna Lovegood", "Luna Lovegood");
        assertEquals(HttpStatus.BAD_REQUEST, respIguais.getStatusCode());
        assertTrue(respIguais.getBody().toLowerCase().contains("selecione dois bruxos diferentes"));

        ResponseEntity<String> respInexistente = rest.getForEntity("/bruxos/batalha?bruxo1={b1}&bruxo2={b2}", String.class, "Luna Lovegood", "NomeInexistente");
        assertEquals(HttpStatus.BAD_REQUEST, respInexistente.getStatusCode());
        assertTrue(respInexistente.getBody().toLowerCase().contains("nao encontrado") || respInexistente.getBody().toLowerCase().contains("não encontrado"));
    }
}