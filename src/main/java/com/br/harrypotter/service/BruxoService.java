package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.model.Magia;
import com.br.harrypotter.repository.BruxoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BruxoService {

    private final BruxoRepository bruxoRepository;
    private final Random random = new Random();

    public BruxoService(BruxoRepository bruxoRepository) {
        this.bruxoRepository = bruxoRepository;
    }

    public BruxoResponseDTO criarBruxo(BruxoRequestDTO dto) {
        Bruxo novo = criarEntidade(dto);
        Bruxo salvo = bruxoRepository.save(novo);
        return new BruxoResponseDTO(salvo.getId(), salvo.getNome(), salvo.getCasa(), ((Magia) salvo).lancarFeitico());
    }

    public List<BruxoResponseDTO> listarBruxos() {
        return bruxoRepository.findAll()
                .stream()
                .map(b -> new BruxoResponseDTO(b.getId(), b.getNome(), b.getCasa(), ((Magia) b).lancarFeitico()))
                .toList();
    }

    public List<String> listarNomes() {
        return bruxoRepository.findAll()
                .stream()
                .map(Bruxo::getNome)
                .toList();
    }

    public BatalhaResponseDTO batalhar(String nome1, String nome2) {
        if (nome1 == null || nome2 == null || nome1.equalsIgnoreCase(nome2)) {
            throw new IllegalArgumentException("Selecione dois bruxos diferentes para a batalha!");
        }

        List<Bruxo> todos = bruxoRepository.findAll();

        Bruxo b1 = todos.stream()
                .filter(b -> b.getNome().equalsIgnoreCase(nome1))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bruxo 1 não encontrado: " + nome1));

        Bruxo b2 = todos.stream()
                .filter(b -> b.getNome().equalsIgnoreCase(nome2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bruxo 2 não encontrado: " + nome2));

        String magia1 = ((Magia) b1).lancarFeitico();
        String magia2 = ((Magia) b2).lancarFeitico();

        Bruxo vencedor = random.nextBoolean() ? b1 : b2;

        return new BatalhaResponseDTO(b1.getNome(), magia1, b2.getNome(), magia2, vencedor.getNome());
    }

    private Bruxo criarEntidade(BruxoRequestDTO dto) {
        String casa = dto.casa() == null ? "" : dto.casa().trim().toLowerCase();
        return switch (casa) {
            case "grifinória", "grifinoria" -> new BruxoGrifinoria(dto.nome());
            case "sonserina" -> new BruxoSonserina(dto.nome());
            default -> throw new IllegalArgumentException("Casa desconhecida: " + dto.casa());
        };
    }
}
