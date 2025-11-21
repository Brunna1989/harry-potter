package com.br.harrypotter.service;

import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.exception.BatalhaInvalidaException;
import com.br.harrypotter.model.Batalha;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.repository.BatalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BruxoService bruxoService;
    private final BatalhaRepository batalhaRepository;
    private final Random random = new Random();

    public BatalhaResponseDTO batalhar(String nome1, String nome2) {

        if (nome1 == null || nome2 == null || nome1.isBlank() || nome2.isBlank()) {
            throw new BatalhaInvalidaException("Nomes dos bruxos não podem ser vazios");
        }

        if (nome1.equals(nome2)) {
            throw new BatalhaInvalidaException("Um bruxo não pode batalhar contra ele mesmo!");
        }

        Bruxo b1 = bruxoService.buscarPorNome(nome1);
        Bruxo b2 = bruxoService.buscarPorNome(nome2);

        int poder1 = calcularPoderAPartirDoFeitico(b1.lancarFeitico()) + random.nextInt(3); // 0..2
        int poder2 = calcularPoderAPartirDoFeitico(b2.lancarFeitico()) + random.nextInt(3);

        Batalha batalha = Batalha.builder()
                .bruxoAtacante(b1)
                .bruxoDefensor(b2)
                .instante(LocalDateTime.now())
                .build();

        String vencedorNome = null;

        if (poder1 == poder2) {
            batalha.setVencedorId(null);
            batalha.setResultado(String.format("Empate: %s (%d) vs %s (%d)", b1.getNome(), poder1, b2.getNome(), poder2));
        } else if (poder1 > poder2) {
            batalha.setVencedorId(b1.getId());
            vencedorNome = b1.getNome();
            batalha.setResultado(String.format("%s venceu (%d x %d) usando: %s", b1.getNome(), poder1, poder2, b1.lancarFeitico()));
        } else {
            batalha.setVencedorId(b2.getId());
            vencedorNome = b2.getNome();
            batalha.setResultado(String.format("%s venceu (%d x %d) usando: %s", b2.getNome(), poder2, poder1, b2.lancarFeitico()));
        }

        batalhaRepository.save(batalha);

        return new BatalhaResponseDTO(
                b1.getNome(), b1.lancarFeitico(),
                b2.getNome(), b2.lancarFeitico(),
                vencedorNome == null ? "Empate" : vencedorNome
        );
    }

    private int calcularPoderAPartirDoFeitico(String feitico) {
        if (feitico == null) return 1;

        String f = feitico.toLowerCase();

        if (f.contains("expelliarmus")) {
            return 10;
        }
        if (f.contains("serpensortia")) {
            return 8;
        }
        if (f.contains("stupefy")) {
            return 9;
        }
        if (f.contains("avada") || f.contains("avada kedavra")) {
            return 100;
        }

        return 6;
    }
}
