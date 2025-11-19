package com.br.harrypotter.service;

import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.exception.BatalhaInvalidaException;
import com.br.harrypotter.model.Bruxo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BruxoService bruxoService;

    public BatalhaResponseDTO batalhar(String nome1, String nome2) {

        if (nome1.equals(nome2)) {
            throw new BatalhaInvalidaException("Um bruxo não pode batalhar contra ele mesmo!");
        }

        Bruxo b1 = bruxoService.buscarPorNome(nome1);
        Bruxo b2 = bruxoService.buscarPorNome(nome2);

        String vencedor = Math.random() > 0.5 ? b1.getNome() : b2.getNome();

        return new BatalhaResponseDTO(
                b1.getNome(), b1.lancarFeitico(),
                b2.getNome(), b2.lancarFeitico(),
                vencedor
        );
    }
}
