package com.br.harrypotter.integration;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.repository.BruxoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BruxoIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;
    private BruxoRepository repository;

    public BruxoIntegrationTest(ApplicationContext context) {
        this.restTemplate = context.getBean(TestRestTemplate.class);
        this.repository = context.getBean(BruxoRepository.class);
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void deveCriarEListarBruxoComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Harry Potter", "Grifinória");

        ResponseEntity<BruxoResponseDTO> response =
                restTemplate.postForEntity(url("/api/bruxos"), dto, BruxoResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().nome()).isEqualTo("Harry Potter");

        ResponseEntity<BruxoResponseDTO[]> lista =
                restTemplate.getForEntity(url("/api/bruxos"), BruxoResponseDTO[].class);

        assertThat(lista.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(lista.getBody()).isNotEmpty();
        assertThat(lista.getBody()[0].nome()).isEqualTo("Harry Potter");
    }

    @Test
    void deveDeletarBruxoComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Draco Malfoy", "Sonserina");
        restTemplate.postForEntity(url("/api/bruxos"), dto, BruxoResponseDTO.class);

        Long idCriado = repository.findAll().get(0).getId();

        ResponseEntity<Void> deleteResponse =
                restTemplate.exchange(url("/api/bruxos/" + idCriado), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(repository.findById(idCriado)).isEmpty();
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
