package com.br.harrypotter.controller;

import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.BruxoAlvoDumbledore;
import com.br.harrypotter.model.BruxoSeveroSnape;
import com.br.harrypotter.model.Magia;
import com.br.harrypotter.service.BruxoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bruxos")
public class BruxoController {

    @Autowired
    private BruxoService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody com.br.harrypotter.dto.BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public List<com.br.harrypotter.dto.BruxoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/batalha")
    public ResponseEntity<Map<String, String>> batalha(
            @RequestParam String bruxo1,
            @RequestParam String bruxo2) {

        Bruxo b1 = criarBruxoPorNome(bruxo1);
        Bruxo b2 = criarBruxoPorNome(bruxo2);

        String magia1 = ((Magia) b1).lancarMagia();
        String magia2 = ((Magia) b2).lancarMagia();
        String vencedor = Math.random() < 0.5 ? b1.getNome() : b2.getNome();

        Map<String, String> resultado = new HashMap<>();
        resultado.put("bruxo1", b1.getNome());
        resultado.put("magia1", magia1);
        resultado.put("bruxo2", b2.getNome());
        resultado.put("magia2", magia2);
        resultado.put("vencedor", vencedor);

        return ResponseEntity.ok(resultado);
    }

    private Bruxo criarBruxoPorNome(String nome) {
        return switch (nome.toLowerCase()) {
            case "alvo dumbledore" -> new BruxoAlvoDumbledore();
            case "severo snape" -> new BruxoSeveroSnape();
            default -> throw new IllegalArgumentException("Bruxo desconhecido: " + nome);
        };
    }
}
