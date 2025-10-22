package com.br.harrypotter.integration;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BruxoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BruxoRequestDTO bruxoGrifinoria;
    private BruxoRequestDTO bruxoSonserina;

    @BeforeEach
    void setup() {
        bruxoGrifinoria = new BruxoRequestDTO("Harry Potter", "Grifinória");
        bruxoSonserina = new BruxoRequestDTO("Draco Malfoy", "Sonserina");
    }

    @Test
    @DisplayName("Deve criar um bruxo da Grifinória com sucesso")
    void deveCriarBruxoGrifinoria() throws Exception {
        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bruxoGrifinoria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Harry Potter"))
                .andExpect(jsonPath("$.casa").value("Grifinória"))
                .andExpect(jsonPath("$.mensagemFeitico", containsString("Expelliarmus")));
    }

    @Test
    @DisplayName("Deve criar um bruxo da Sonserina com sucesso")
    void deveCriarBruxoSonserina() throws Exception {
        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bruxoSonserina)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Draco Malfoy"))
                .andExpect(jsonPath("$.casa").value("Sonserina"))
                .andExpect(jsonPath("$.mensagemFeitico", containsString("Serpensortia")));
    }

    @Test
    @DisplayName("Deve listar todos os bruxos cadastrados")
    void deveListarBruxos() throws Exception {
        // Cria dois bruxos
        mockMvc.perform(post("/bruxos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bruxoGrifinoria)));

        mockMvc.perform(post("/bruxos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bruxoSonserina)));

        // Lista todos
        mockMvc.perform(get("/bruxos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", anyOf(is("Harry Potter"), is("Draco Malfoy"))))
                .andExpect(jsonPath("$[1].nome", anyOf(is("Harry Potter"), is("Draco Malfoy"))));
    }

    @Test
    @DisplayName("Deve listar apenas os nomes dos bruxos")
    void deveListarNomesBruxos() throws Exception {
        // Cria um bruxo
        mockMvc.perform(post("/bruxos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bruxoGrifinoria)));

        mockMvc.perform(get("/bruxos/listar-nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Harry Potter"));
    }

    @Test
    @DisplayName("Deve realizar uma batalha entre dois bruxos e retornar o vencedor")
    void deveRealizarBatalha() throws Exception {
        // Cria dois bruxos
        mockMvc.perform(post("/bruxos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bruxoGrifinoria)));
        mockMvc.perform(post("/bruxos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bruxoSonserina)));

        mockMvc.perform(get("/bruxos/batalha")
                        .param("bruxo1", "Harry Potter")
                        .param("bruxo2", "Draco Malfoy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bruxo1").value(anyOf(is("Harry Potter"), is("Draco Malfoy"))))
                .andExpect(jsonPath("$.bruxo2").value(anyOf(is("Harry Potter"), is("Draco Malfoy"))))
                .andExpect(jsonPath("$.vencedor").value(anyOf(is("Harry Potter"), is("Draco Malfoy"))));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar batalhar com o mesmo bruxo")
    void deveRetornarErroAoBatalharMesmoBruxo() throws Exception {
        mockMvc.perform(get("/bruxos/batalha")
                        .param("bruxo1", "Harry Potter")
                        .param("bruxo2", "Harry Potter"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value(containsString("Selecione dois bruxos diferentes")));
    }

    @Test
    @DisplayName("Deve retornar erro ao criar bruxo com casa inválida")
    void deveRetornarErroCasaInvalida() throws Exception {
        BruxoRequestDTO invalido = new BruxoRequestDTO("Severo Snape", "Lufa-Lufa");

        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro", containsString("Casa desconhecida")));
    }
}
