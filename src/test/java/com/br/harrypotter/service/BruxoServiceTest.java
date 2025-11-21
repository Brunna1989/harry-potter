package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.exception.BruxoNaoEncontradoException;
import com.br.harrypotter.exception.CasaInvalidaException;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.repository.BruxoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void deveCriarBruxoDaGrifinoria() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Harry Potter", "Grifinoria");
        Bruxo bruxo = new BruxoGrifinoria(1L, "Harry Potter");

        when(repository.save(any(Bruxo.class))).thenReturn(bruxo);

        BruxoResponseDTO resposta = service.criar(dto);

        assertEquals(1L, resposta.id());
        assertEquals("Harry Potter", resposta.nome());
        assertEquals("Grifinoria", resposta.casa());
        assertNotNull(resposta.feitico());
    }

    @Test
    void deveCriarBruxoDaSonserina() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Draco Malfoy", "Sonserina");
        Bruxo bruxo = new BruxoSonserina(2L, "Draco Malfoy");

        when(repository.save(any(Bruxo.class))).thenReturn(bruxo);

        BruxoResponseDTO resposta = service.criar(dto);

        assertEquals("Sonserina", resposta.casa());
    }

    @Test
    void deveLancarExcecaoAoCriarComCasaInvalida() {
        BruxoRequestDTO dto = new BruxoRequestDTO("Fulano", "OutraCasa");

        assertThrows(CasaInvalidaException.class, () -> service.criar(dto));
    }

    @Test
    void deveListarTodosOsBruxos() {
        Bruxo b1 = new BruxoGrifinoria(1L, "Harry");
        Bruxo b2 = new BruxoSonserina(2L, "Draco");

        when(repository.findAll()).thenReturn(List.of(b1, b2));

        List<BruxoResponseDTO> lista = service.listar();

        assertEquals(2, lista.size());
        assertEquals("Harry", lista.get(0).nome());
        assertEquals("Draco", lista.get(1).nome());
    }

    @Test
    void deveBuscarBruxoPorNome() {
        Bruxo b = new BruxoGrifinoria(1L, "Harry");

        when(repository.findByNome("Harry")).thenReturn(Optional.of(b));

        Bruxo encontrado = service.buscarPorNome("Harry");

        assertEquals("Harry", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarPorNomeInexistente() {
        when(repository.findByNome("Desconhecido")).thenReturn(Optional.empty());

        assertThrows(BruxoNaoEncontradoException.class,
                () -> service.buscarPorNome("Desconhecido"));
    }

    @Test
    void deveBuscarBruxoPorId() {
        Bruxo b = new BruxoSonserina(1L, "Draco");

        when(repository.findById(1L)).thenReturn(Optional.of(b));

        Bruxo encontrado = service.buscarPorId(1L);

        assertEquals("Draco", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BruxoNaoEncontradoException.class,
                () -> service.buscarPorId(99L));
    }

    @Test
    void deveDeletarBruxoPorId() {
        Bruxo b = new BruxoGrifinoria(1L, "Harry");

        when(repository.findById(1L)).thenReturn(Optional.of(b));

        service.deletar(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(BruxoNaoEncontradoException.class,
                () -> service.deletar(10L));
    }

    @Test
    void deveDeletarTodos() {
        service.deletarTodos();
        verify(repository, times(1)).deleteAll();
    }
}
