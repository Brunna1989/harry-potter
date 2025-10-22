package test.service;

import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.repository.BruxoRepository;
import com.br.harrypotter.service.BruxoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BruxoServiceTest {

    private BruxoRepository repository;
    private BruxoService service;

    @BeforeEach
    void setup() {
        repository = mock(BruxoRepository.class);
        service = new BruxoService(repository);
    }

    @Test
    void deveCriarBruxoGrifinoriaComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Harry Potter", "Grifinória");
        Bruxo salvo = new BruxoGrifinoria("Harry Potter");
        salvo.setId(1L);

        when(repository.save(any(Bruxo.class))).thenReturn(salvo);

        BruxoResponseDTO resp = service.criarBruxo(dto);

        assertNotNull(resp);
        assertEquals(1L, resp.id());
        assertEquals("Harry Potter", resp.nome());
        assertEquals("Grifinória", resp.casa());
        assertTrue(resp.mensagemFeitico().contains("Expelliarmus"));
        verify(repository, times(1)).save(any(Bruxo.class));
    }

    @Test
    void deveCriarBruxoSonserinaComSucesso() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Draco Malfoy", "Sonserina");
        Bruxo salvo = new BruxoSonserina("Draco Malfoy");
        salvo.setId(2L);

        when(repository.save(any(Bruxo.class))).thenReturn(salvo);

        BruxoResponseDTO resp = service.criarBruxo(dto);

        assertNotNull(resp);
        assertEquals("Draco Malfoy", resp.nome());
        assertEquals("Sonserina", resp.casa());
        assertTrue(resp.mensagemFeitico().contains("Serpensortia"));
    }

    @Test
    void deveLancarErroAoCriarBruxoDeCasaDesconhecida() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Tom Riddle", "Corvinal");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.criarBruxo(dto)
        );

        assertEquals("Casa desconhecida: Corvinal", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void deveListarBruxosComFeitico() {
        List<Bruxo> lista = List.of(new BruxoGrifinoria("Harry"), new BruxoSonserina("Draco"));
        when(repository.findAll()).thenReturn(lista);

        List<BruxoResponseDTO> resp = service.listarBruxos();

        assertEquals(2, resp.size());
        assertTrue(resp.get(0).mensagemFeitico().contains("Expelliarmus"));
        assertTrue(resp.get(1).mensagemFeitico().contains("Serpensortia"));
    }

    @Test
    void deveListarNomesDosBruxos() {
        List<Bruxo> lista = List.of(new BruxoGrifinoria("Harry"), new BruxoSonserina("Draco"));
        when(repository.findAll()).thenReturn(lista);

        List<String> nomes = service.listarNomes();

        assertEquals(List.of("Harry", "Draco"), nomes);
    }

    @Test
    void deveRealizarBatalhaComSucesso() {
        Bruxo harry = new BruxoGrifinoria("Harry");
        Bruxo draco = new BruxoSonserina("Draco");

        when(repository.findAll()).thenReturn(List.of(harry, draco));

        BatalhaResponseDTO resp = service.batalhar("Harry", "Draco");

        assertNotNull(resp);
        assertTrue(List.of("Harry", "Draco").contains(resp.vencedor()));
        assertTrue(resp.magia1().contains("Expelliarmus") || resp.magia2().contains("Serpensortia"));
    }

    @Test
    void deveLancarErroSeNomesForemIguaisNaBatalha() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.batalhar("Harry", "Harry")
        );

        assertEquals("Selecione dois bruxos diferentes para a batalha!", ex.getMessage());
    }

    @Test
    void deveLancarErroSeNomeForNuloNaBatalha() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.batalhar(null, "Draco")
        );

        assertEquals("Selecione dois bruxos diferentes para a batalha!", ex.getMessage());
    }

    @Test
    void deveLancarErroSeBruxo1NaoForEncontrado() {
        Bruxo draco = new BruxoSonserina("Draco");
        when(repository.findAll()).thenReturn(List.of(draco));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.batalhar("Harry", "Draco")
        );

        assertEquals("Bruxo 1 não encontrado: Harry", ex.getMessage());
    }

    @Test
    void deveLancarErroSeBruxo2NaoForEncontrado() {
        Bruxo harry = new BruxoGrifinoria("Harry");
        when(repository.findAll()).thenReturn(List.of(harry));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.batalhar("Harry", "Draco")
        );

        assertEquals("Bruxo 2 não encontrado: Draco", ex.getMessage());
    }
}
