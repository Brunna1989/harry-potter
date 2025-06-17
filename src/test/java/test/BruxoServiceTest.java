package test;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoAlvoDumbledore;
import com.br.harrypotter.model.BruxoSeveroSnape;
import com.br.harrypotter.repository.BruxoRepository;
import com.br.harrypotter.service.BruxoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BruxoServiceTest {

    @Mock
    private BruxoRepository repository;

    @InjectMocks
    private BruxoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarDeveSalvarBruxoDumbledore() {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Alvo Dumbledore");
        dto.setIdade(115);
        dto.setCasa("GRIFINORIA");

        Bruxo bruxo = new BruxoAlvoDumbledore("Alvo Dumbledore", 115);
        bruxo.setId(1L);

        when(repository.save(any(Bruxo.class))).thenReturn(bruxo);

        BruxoResponseDTO response = service.salvar(dto);

        assertEquals("Alvo Dumbledore", response.getNome());
        assertEquals(115, response.getIdade());
        assertEquals("GRIFINORIA", response.getCasa());
        assertNotNull(response.getMagia());
        verify(repository, times(1)).save(any(Bruxo.class));
    }

    @Test
    void salvarDeveSalvarBruxoSnape() {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Severo Snape");
        dto.setIdade(48);
        dto.setCasa("SONSERINA");

        Bruxo bruxo = new BruxoSeveroSnape("Severo Snape", 48);
        bruxo.setId(2L);

        when(repository.save(any(Bruxo.class))).thenReturn(bruxo);

        BruxoResponseDTO response = service.salvar(dto);

        assertEquals("Severo Snape", response.getNome());
        assertEquals(48, response.getIdade());
        assertEquals("SONSERINA", response.getCasa());
        assertNotNull(response.getMagia());
        verify(repository, times(1)).save(any(Bruxo.class));
    }

    @Test
    void salvarDeveLancarExcecaoParaCasaInvalida() {
        BruxoRequestDTO dto = new BruxoRequestDTO();
        dto.setNome("Outro Bruxo");
        dto.setIdade(30);
        dto.setCasa("CORVINAL");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
        assertEquals("Casa inválida.", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void listarTodosDeveRetornarListaDeBruxos() {
        Bruxo bruxo1 = new BruxoAlvoDumbledore("Alvo Dumbledore", 115);
        bruxo1.setId(1L);
        Bruxo bruxo2 = new BruxoSeveroSnape("Severo Snape", 48);
        bruxo2.setId(2L);

        when(repository.findAll()).thenReturn(Arrays.asList(bruxo1, bruxo2));

        List<BruxoResponseDTO> lista = service.listarTodos();

        assertEquals(2, lista.size());
        assertEquals("GRIFINORIA", lista.get(0).getCasa());
        assertEquals("SONSERINA", lista.get(1).getCasa());
        assertNotNull(lista.get(0).getMagia());
        assertNotNull(lista.get(1).getMagia());
        verify(repository, times(1)).findAll();
    }
}