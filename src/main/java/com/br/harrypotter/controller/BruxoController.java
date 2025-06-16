package com.br.harrypotter.controller;

import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.model.Magia;
import com.br.harrypotter.repository.BruxoRepository;
import com.br.harrypotter.service.BruxoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bruxos")
public class BruxoController {

    @Autowired
    private BruxoService service;

    @Autowired
    private BruxoRepository repository;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody com.br.harrypotter.dto.BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    @GetMapping
    public List<com.br.harrypotter.dto.BruxoResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/batalha")
    public ResponseEntity<?> batalha(
            @RequestParam String bruxo1,
            @RequestParam String bruxo2) {

        try {
            // Remove acentos e espaços extras dos nomes
            String nome1 = normalizar(bruxo1);
            String nome2 = normalizar(bruxo2);

            Bruxo b1 = repository.findAll().stream()
                    .filter(b -> normalizar(b.getNome()).equals(nome1))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Bruxo não encontrado: " + bruxo1));
            Bruxo b2 = repository.findAll().stream()
                    .filter(b -> normalizar(b.getNome()).equals(nome2))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Bruxo não encontrado: " + bruxo2));

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
        } catch (Exception e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    private String normalizar(String texto) {
        if (texto == null) return "";
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }
}