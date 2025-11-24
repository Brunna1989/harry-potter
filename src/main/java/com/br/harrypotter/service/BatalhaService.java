package com.br.harrypotter.service;

import com.br.harrypotter.dto.BatalhaRequestDto;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.exception.BatalhaInvalidaException;
import com.br.harrypotter.model.Batalha;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.repository.BatalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BruxoService bruxoService;
    private final BatalhaRepository batalhaRepository;
    private final Random random = new Random();

    public BatalhaResponseDTO batalhar(BatalhaRequestDto dto) {

        if (dto.bruxo1() == null || dto.bruxo2() == null
                || dto.bruxo1().isBlank() || dto.bruxo2().isBlank()) {
            throw new BatalhaInvalidaException("Nomes dos bruxos não podem ser vazios.");
        }

        if (dto.bruxo1().equalsIgnoreCase(dto.bruxo2())) {
            throw new BatalhaInvalidaException("Um bruxo não pode batalhar contra ele mesmo.");
        }

        Bruxo b1 = bruxoService.buscarPorNome(dto.bruxo1());
        Bruxo b2 = bruxoService.buscarPorNome(dto.bruxo2());

        int poder1 = calcularPoder(b1.lancarFeitico()) + random.nextInt(3);
        int poder2 = calcularPoder(b2.lancarFeitico()) + random.nextInt(3);

        Long vencedorId = null;
        String vencedorNome = null;
        String resultadoFinal;

        if (poder1 > poder2) {
            vencedorId = b1.getId();
            vencedorNome = b1.getNome();
            resultadoFinal = b1.getNome() + " venceu usando " + b1.lancarFeitico();
        } else if (poder2 > poder1) {
            vencedorId = b2.getId();
            vencedorNome = b2.getNome();
            resultadoFinal = b2.getNome() + " venceu usando " + b2.lancarFeitico();
        } else {
            resultadoFinal = "Empate entre " + b1.getNome() + " e " + b2.getNome();
        }

        Batalha batalha = Batalha.builder()
                .bruxo1(b1)
                .bruxo2(b2)
                .vencedorId(vencedorId)
                .resultado(resultadoFinal)
                .build();

        batalhaRepository.save(batalha);

        return new BatalhaResponseDTO(
                b1.getId(),
                b1.getNome(),
                b1.lancarFeitico(),
                b2.getNome(),
                b2.lancarFeitico(),
                vencedorNome == null ? "Empate" : vencedorNome
        );
    }

    private int calcularPoder(String feitico) {
        String f = feitico.toLowerCase();

        if (f.contains("expelliarmus")) return 10;
        if (f.contains("serpensortia")) return 8;
        if (f.contains("stupefy")) return 9;
        if (f.contains("avada")) return 100;

        return 6;
    }

    public List<Batalha> listarHistorico() {
        return batalhaRepository.findAll();
    }

    public Batalha buscarPorId(Long id) {
        return batalhaRepository.findById(id)
                .orElseThrow(() -> new BatalhaInvalidaException("Batalha não encontrada: " + id));
    }


}
