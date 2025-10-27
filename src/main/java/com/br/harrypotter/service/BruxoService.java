package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.model.*;
import com.br.harrypotter.repository.BruxoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BruxoService {

    private final BruxoRepository repository;

    @Transactional
    public BruxoResponseDTO criarBruxo(BruxoRequestDTO dto) {
        Bruxo bruxo;

        switch (dto.casa()) {
            case "Grifinória" -> bruxo = new BruxoGrifinoria(null, dto.nome());
            case "Sonserina" -> bruxo = new BruxoSonserina(null, dto.nome());
            default -> throw new IllegalArgumentException("Casa inválida: " + dto.casa());
        }

        Bruxo salvo = repository.save(bruxo);
        return new BruxoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCasa(),
                salvo.lancarFeitico()
        );
    }

    @Transactional(readOnly = true)
    public List<BruxoResponseDTO> listarBruxos() {
        return repository.findAll().stream()
                .map(b -> new BruxoResponseDTO(b.getId(), b.getNome(), b.getCasa(), b.lancarFeitico()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> listarNomes() {
        return repository.findAll().stream()
                .map(Bruxo::getNome)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BatalhaResponseDTO batalhar(String nome1, String nome2) {
        Bruxo b1 = repository.findByNome(nome1)
                .orElseThrow(() -> new EntityNotFoundException("Bruxo não encontrado: " + nome1));
        Bruxo b2 = repository.findByNome(nome2)
                .orElseThrow(() -> new EntityNotFoundException("Bruxo não encontrado: " + nome2));

        String vencedor = Math.random() > 0.5 ? b1.getNome() : b2.getNome();

        return new BatalhaResponseDTO(
                b1.getNome(), b1.lancarFeitico(),
                b2.getNome(), b2.lancarFeitico(),
                vencedor
        );
    }

    @Transactional
    public void deletarTodos() {
        repository.deleteAll();
    }

    @Transactional
    public void deletarPorId(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Bruxo com ID " + id + " não encontrado");
        }
        repository.deleteById(id);
    }
}
