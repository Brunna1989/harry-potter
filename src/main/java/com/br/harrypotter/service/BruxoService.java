package com.br.harrypotter.service;

import com.br.harrypotter.dto.*;
import com.br.harrypotter.model.*;
import org.springframework.stereotype.Service;

@Service
public class BruxoService {

    public BruxoResponseDTO criarBruxo(BruxoRequestDTO dto) {
        Bruxo bruxo;

        switch (dto.getCasa().toLowerCase()) {
            case "grifinória":
                bruxo = new BruxoGrifinoria(dto.getNome());
                break;
            case "sonserina":
                bruxo = new BruxoSonserina(dto.getNome());
                break;
            default:
                throw new IllegalArgumentException("Casa desconhecida: " + dto.getCasa());
        }

        return BruxoResponseDTO.builder()
                .nome(bruxo.getNome())
                .casa(bruxo.getCasa())
                .mensagemFeitico(((Magia) bruxo).lancarFeitico())
                .build();
    }
}
