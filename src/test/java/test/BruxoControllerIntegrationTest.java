package test;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.repository.BruxoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.br.harrypotter.Application.class)
@AutoConfigureMockMvc
class BruxoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BruxoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    void deveCadastrarBruxoDumbledoreERetornarDTO() throws Exception {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Alvo Dumbledore");
        dto.setIdade(115);
        dto.setCasa("GRIFINORIA");

        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Alvo Dumbledore"))
                .andExpect(jsonPath("$.casa").value("GRIFINORIA"))
                .andExpect(jsonPath("$.magia", not(emptyOrNullString())));
    }

    @Test
    void deveCadastrarBruxoSnapeERetornarDTO() throws Exception {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Severo Snape");
        dto.setIdade(48);
        dto.setCasa("SONSERINA");

        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Severo Snape"))
                .andExpect(jsonPath("$.casa").value("SONSERINA"))
                .andExpect(jsonPath("$.magia", not(emptyOrNullString())));
    }

    @Test
    void deveRetornarErroAoCadastrarCasaInvalida() throws Exception {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Outro Bruxo");
        dto.setIdade(30);
        dto.setCasa("CORVINAL");

        mockMvc.perform(post("/bruxos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro", containsString("Casa inválida")));
    }

    @Test
    void deveListarTodosOsBruxos() throws Exception {
        repository.save(new com.br.harrypotter.model.BruxoAlvoDumbledore("Alvo Dumbledore", 115));
        repository.save(new com.br.harrypotter.model.BruxoSeveroSnape("Severo Snape", 48));

        mockMvc.perform(get("/bruxos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", anyOf(is("Alvo Dumbledore"), is("Severo Snape"))))
                .andExpect(jsonPath("$[1].nome", anyOf(is("Alvo Dumbledore"), is("Severo Snape"))));
    }

    @Test
    void deveListarSomenteNomesDosBruxos() throws Exception {
        repository.save(new com.br.harrypotter.model.BruxoAlvoDumbledore("Alvo Dumbledore", 115));
        repository.save(new com.br.harrypotter.model.BruxoSeveroSnape("Severo Snape", 48));

        mockMvc.perform(get("/bruxos/listar-nomes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasItems("Alvo Dumbledore", "Severo Snape")));
    }

    @Test
    void deveSimularBatalhaComSucesso() throws Exception {
        repository.save(new com.br.harrypotter.model.BruxoAlvoDumbledore("Alvo Dumbledore", 115));
        repository.save(new com.br.harrypotter.model.BruxoSeveroSnape("Severo Snape", 48));

        mockMvc.perform(get("/bruxos/batalha")
                        .param("bruxo1", "Alvo Dumbledore")
                        .param("bruxo2", "Severo Snape"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bruxo1", is("Alvo Dumbledore")))
                .andExpect(jsonPath("$.bruxo2", is("Severo Snape")))
                .andExpect(jsonPath("$.magia1", not(emptyOrNullString())))
                .andExpect(jsonPath("$.magia2", not(emptyOrNullString())))
                .andExpect(jsonPath("$.vencedor", anyOf(is("Alvo Dumbledore"), is("Severo Snape"))));
    }

    @Test
    void deveRetornarErroSeBruxoNaoExisteNaBatalha() throws Exception {
        repository.save(new com.br.harrypotter.model.BruxoAlvoDumbledore("Alvo Dumbledore", 115));

        mockMvc.perform(get("/bruxos/batalha")
                        .param("bruxo1", "Alvo Dumbledore")
                        .param("bruxo2", "Inexistente"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro", containsString("Bruxo não encontrado")));
    }

    @Test
    void deveNormalizarENaoDiferenciarAcentosOuCaixaNaBatalha() throws Exception {
        repository.save(new com.br.harrypotter.model.BruxoAlvoDumbledore("Álvo Dumbledore", 115));
        repository.save(new com.br.harrypotter.model.BruxoSeveroSnape("Severo Snape", 48));

        mockMvc.perform(get("/bruxos/batalha")
                        .param("bruxo1", "alvo dumbledore")
                        .param("bruxo2", "severo snape"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vencedor", anyOf(is("Álvo Dumbledore"), is("Severo Snape"))));
    }
}