package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.exception.BruxoNaoEncontradoException;
import com.br.harrypotter.exception.CasaInvalidaException;
import com.br.harrypotter.model.*;
import com.br.harrypotter.repository.BruxoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BruxoService {

    private final BruxoRepository repository;

    public BruxoResponseDTO criar(BruxoRequestDTO dto) {

        Bruxo bruxo = switch (dto.casa()) {
            case "Grifinória" -> new BruxoGrifinoria(null, dto.nome());
            case "Sonserina" -> new BruxoSonserina(null, dto.nome());
            default -> throw new CasaInvalidaException(dto.casa());
        };

        Bruxo salvo = repository.save(bruxo);

        return new BruxoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getCasa(),
                salvo.lancarFeitico()
        );
    }

    public List<BruxoResponseDTO> listar() {
        return repository.findAll().stream()
                .map(b -> new BruxoResponseDTO(
                        b.getId(), b.getNome(), b.getCasa(), b.lancarFeitico()
                ))
                .toList();
    }

    public Bruxo buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .orElseThrow(() -> new BruxoNaoEncontradoException("Bruxo não encontrado: " + nome));
    }

    public void deletar(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new BruxoNaoEncontradoException("Bruxo com ID " + id + " não existe"));
        repository.deleteById(id);
    }

    public void deletarTodos() {
        repository.deleteAll();
    }
}
