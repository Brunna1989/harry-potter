package com.br.harrypotter.service;

import com.br.harrypotter.dto.BatalhaRequestDto;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.exception.BatalhaInvalidaException;
import com.br.harrypotter.model.Batalha;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.repository.BatalhaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BatalhaServiceTest {

    private BruxoService bruxoService;
    private BatalhaRepository batalhaRepository;
    private BatalhaService batalhaService;

    @BeforeEach
    void setup() {
        bruxoService = mock(BruxoService.class);
        batalhaRepository = mock(BatalhaRepository.class);
        batalhaService = new BatalhaService(bruxoService, batalhaRepository);
    }

    @Test
    void deveLancarExcecaoQuandoBruxo1Vazio() {
        BatalhaRequestDto dto = new BatalhaRequestDto("", "Harry");

        assertThrows(BatalhaInvalidaException.class,
                () -> batalhaService.batalhar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoBruxo2Vazio() {
        BatalhaRequestDto dto = new BatalhaRequestDto("Harry", "");

        assertThrows(BatalhaInvalidaException.class,
                () -> batalhaService.batalhar(dto));
    }

    @Test
    void deveLancarExcecaoQuandoBruxosForemIguais() {
        BatalhaRequestDto dto = new BatalhaRequestDto("Harry", "Harry");

        assertThrows(BatalhaInvalidaException.class,
                () -> batalhaService.batalhar(dto));
    }

    @Test
    void deveGerarVitoriaDoBruxo1() {

        Bruxo b1 = new BruxoGrifinoria(1L, "Harry");
        Bruxo b2 = new BruxoSonserina(2L, "Draco");

        when(bruxoService.buscarPorNome("Harry")).thenReturn(b1);
        when(bruxoService.buscarPorNome("Draco")).thenReturn(b2);

        BatalhaRequestDto dto = new BatalhaRequestDto("Harry", "Draco");

        BatalhaResponseDTO resposta = batalhaService.batalhar(dto);

        assertEquals("Expelliarmus! O bruxo da Grifinoria lançou seu feitiço!",
                resposta.feiticoBruxo1());

        assertEquals("Serpensortia! O bruxo da Sonserina lançou seu feitiço!",
                resposta.feiticoBruxo2());

        assertEquals("Harry", resposta.vencedor());
    }

    @Test
    void deveGerarVitoriaDoBruxo2() {
        Bruxo b1 = new BruxoSonserina(1L, "Draco");
        Bruxo b2 = new BruxoGrifinoria(2L, "Harry");

        when(bruxoService.buscarPorNome("Draco")).thenReturn(b1);
        when(bruxoService.buscarPorNome("Harry")).thenReturn(b2);

        BatalhaRequestDto dto = new BatalhaRequestDto("Draco", "Harry");

        BatalhaResponseDTO resposta = batalhaService.batalhar(dto);

        assertEquals("Harry", resposta.vencedor());
    }

    @Test
    void deveListarHistoricoDeBatalhas() {
        Batalha b = Batalha.builder().id(1L).resultado("Harry venceu").build();
        when(batalhaRepository.findAll()).thenReturn(List.of(b));

        List<Batalha> lista = batalhaService.listarHistorico();

        assertEquals(1, lista.size());
    }

    @Test
    void deveBuscarBatalhaPorId() {
        Batalha b = Batalha.builder().id(1L).resultado("teste").build();
        when(batalhaRepository.findById(1L)).thenReturn(Optional.of(b));

        Batalha encontrado = batalhaService.buscarPorId(1L);

        assertEquals(1L, encontrado.getId());
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarBatalhaPorId() {
        when(batalhaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BatalhaInvalidaException.class,
                () -> batalhaService.buscarPorId(99L));
    }
}
