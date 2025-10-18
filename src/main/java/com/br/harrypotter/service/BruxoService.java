package com.br.harrypotter.service;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoGrifinoria;
import com.br.harrypotter.model.BruxoSonserina;
import com.br.harrypotter.repository.BruxoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class BruxoService {

    private final BruxoRepository bruxoRepository;
    private final Random random = new Random();

    public BruxoService(BruxoRepository bruxoRepository) {
        this.bruxoRepository = bruxoRepository;
    }

    public BruxoResponseDTO cadastrarBruxo(BruxoRequestDTO dto) {
        Bruxo novoBruxo = criarBruxo(dto);

        if (novoBruxo == null) {
            throw new IllegalArgumentException("Casa inválida! Use Grifinória ou Sonserina.");
        }

        Bruxo bruxoSalvo = bruxoRepository.save(novoBruxo);

        return new BruxoResponseDTO(
                bruxoSalvo.getId(),
                bruxoSalvo.getNome(),
                bruxoSalvo.getCasa(),
                bruxoSalvo.lancarFeitico()
        );
    }

    public List<BruxoResponseDTO> listarBruxos() {
        return bruxoRepository.findAll().stream()
                .map(b -> new BruxoResponseDTO(b.getId(), b.getNome(), b.getCasa(), b.lancarFeitico()))
                .toList();
    }

    public List<String> listarNomes() {
        return bruxoRepository.findAll()
                .stream()
                .map(Bruxo::getNome)
                .toList();
    }

    public Map<String, String> batalhar(String nome1, String nome2) {
        if (nome1.equalsIgnoreCase(nome2)) {
            throw new IllegalArgumentException("Selecione dois bruxos diferentes para a batalha!");
        }

        Bruxo bruxo1 = bruxoRepository.findAll().stream()
                .filter(b -> b.getNome().equalsIgnoreCase(nome1))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bruxo 1 não encontrado: " + nome1));

        Bruxo bruxo2 = bruxoRepository.findAll().stream()
                .filter(b -> b.getNome().equalsIgnoreCase(nome2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Bruxo 2 não encontrado: " + nome2));

        String magia1 = bruxo1.lancarFeitico();
        String magia2 = bruxo2.lancarFeitico();

        Bruxo vencedor = random.nextBoolean() ? bruxo1 : bruxo2;

        return Map.of(
                "bruxo1", bruxo1.getNome(),
                "magia1", magia1,
                "bruxo2", bruxo2.getNome(),
                "magia2", magia2,
                "vencedor", vencedor.getNome()
        );
    }

    private Bruxo criarBruxo(BruxoRequestDTO dto) {
        String casa = dto.casa().trim().toLowerCase();

        return switch (casa) {
            case "grifinória", "grifinoria" -> new BruxoGrifinoria(dto.nome());
            case "sonserina" -> new BruxoSonserina(dto.nome());
            default -> null;
        };
    }
}
