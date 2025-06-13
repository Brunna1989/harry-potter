package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.model.*;
import com.br.harrypotter.repository.BruxoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BruxoService {

    @Autowired
    private BruxoRepository repository;

    public BruxoResponseDTO salvar(BruxoRequestDTO dto) {
        Bruxo bruxo;

        switch (dto.getCasa().toUpperCase()) {
            case "GRIFINORIA" -> bruxo = new BruxoAlvoDumbledore(dto.getNome(), dto.getIdade());
            case "SONSERINA" -> bruxo = new BruxoSeveroSnape(dto.getNome(), dto.getIdade());
            default -> throw new IllegalArgumentException("Casa inválida.");
        }

        Bruxo salvo = repository.save(bruxo);
        return converterParaDTO(salvo);
    }

    public List<BruxoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    private BruxoResponseDTO converterParaDTO(Bruxo bruxo) {
        BruxoResponseDTO dto = new BruxoResponseDTO();
        dto.setId(bruxo.getId());
        dto.setNome(bruxo.getNome());
        dto.setIdade(bruxo.getIdade());
        dto.setCasa(bruxo instanceof BruxoAlvoDumbledore ? "GRIFINORIA" : "SONSERINA");
        dto.setMagia(((Magia) bruxo).lancarMagia());
        return dto;
    }
}
