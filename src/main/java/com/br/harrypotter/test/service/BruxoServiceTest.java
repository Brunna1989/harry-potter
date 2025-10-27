package com.br.harrypotter.test.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.repository.BruxoRepository;
import com.br.harrypotter.service.BruxoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BruxoServiceTest {

    @Mock
    private BruxoRepository repository;

    @InjectMocks
    private BruxoService service;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarBruxoGrifinoriaComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Harry Potter", "Grifinória");
        ArgumentCaptor<Bruxo> captor = ArgumentCaptor.forClass(Bruxo.class);

        BruxoResponseDTO response = service.criarBruxo(dto);

        verify(repository).save(captor.capture());
        Bruxo salvo = captor.getValue();

        assertNotNull(salvo);
        assertEquals("Harry Potter", salvo.getNome());
        assertEquals("Grifinória", salvo.getCasa());
        assertEquals("Harry Potter", response.nome());
        assertEquals("Grifinória", response.casa());
        assertNotNull(response.feitico());
    }


    @Test
    void deveCriarBruxoSonserinaComSucesso() {
        // DTO de entrada
        BruxoRequestDTO dto = new BruxoRequestDTO("Draco Malfoy", "Sonserina");

        // Capturador do objeto que será salvo
        ArgumentCaptor<Bruxo> captor = ArgumentCaptor.forClass(Bruxo.class);

        // Mock do comportamento do repository
        when(repository.save(any(Bruxo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chamada do service
        BruxoResponseDTO response = service.criarBruxo(dto);

        // Verifica se repository.save foi chamado e captura o objeto
        verify(repository).save(captor.capture());
        Bruxo salvo = captor.getValue();

        // Verificações da entidade salva
        assertNotNull(salvo);
        assertEquals("Draco Malfoy", salvo.getNome());
        assertEquals("Sonserina", salvo.getCasa());

        // Verificações do DTO de resposta
        assertEquals("Draco Malfoy", response.nome());
        assertEquals("Sonserina", response.casa());
        assertNotNull(response.feitico());
        assertEquals("Serpensortia!", response.feitico());
    }

    @Test
    void deveLancarExcecaoParaCasaInvalida() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Luna Lovegood", "Corvinal");
        assertThrows(IllegalArgumentException.class, () -> service.criarBruxo(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarTodosOsBruxos() {
        Bruxo b1 = new BruxoGrifinoria(1L, "Harry Potter");
        Bruxo b2 = new BruxoSonserina(2L, "Draco Malfoy");
        when(repository.findAll()).thenReturn(List.of(b1, b2));

        List<BruxoResponseDTO> lista = service.listarBruxos();

        assertEquals(2, lista.size());
        assertEquals("Harry Potter", lista.get(0).nome());
        assertEquals("Draco Malfoy", lista.get(1).nome());
    }

    @Test
    void deveListarApenasNomesDosBruxos() {
        Bruxo b1 = new BruxoGrifinoria(1L, "Harry Potter");
        Bruxo b2 = new BruxoSonserina(2L, "Draco Malfoy");
        when(repository.findAll()).thenReturn(List.of(b1, b2));

        List<String> nomes = service.listarNomes();

        assertEquals(2, nomes.size());
        assertTrue(nomes.contains("Harry Potter"));
        assertTrue(nomes.contains("Draco Malfoy"));
    }


    @Test
    void deveRealizarBatalhaEntreDoisBruxos() {
        Bruxo b1 = new BruxoGrifinoria(1L, "Harry Potter");
        Bruxo b2 = new BruxoSonserina(2L, "Draco Malfoy");

        when(repository.findByNome("Harry Potter")).thenReturn(Optional.of(b1));
        when(repository.findByNome("Draco Malfoy")).thenReturn(Optional.of(b2));

        BatalhaResponseDTO batalha = service.batalhar("Harry Potter", "Draco Malfoy");

        assertNotNull(batalha);
        assertEquals("Harry Potter", batalha.nomeBruxo1());
        assertEquals("Draco Malfoy", batalha.nomeBruxo2());
        assertTrue(batalha.vencedor().equals("Harry Potter") || batalha.vencedor().equals("Draco Malfoy"));
    }

    @Test
    void deveLancarExcecaoQuandoBruxoNaoEncontrado() {
        when(repository.findByNome("Harry")).thenReturn(Optional.empty());
        when(repository.findByNome("Draco")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.batalhar("Harry", "Draco"));
    }
}
