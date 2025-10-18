package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.service.BruxoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bruxos")
@CrossOrigin(origins = "*")
public class BruxoController {

    private final BruxoService bruxoService;

    public BruxoController(BruxoService bruxoService) {
        this.bruxoService = bruxoService;
    }

    @PostMapping
    public ResponseEntity<BruxoResponseDTO> cadastrar(@RequestBody BruxoRequestDTO dto) {
        return ResponseEntity.ok(bruxoService.cadastrarBruxo(dto));
    }

    @GetMapping
    public ResponseEntity<List<BruxoResponseDTO>> listar() {
        return ResponseEntity.ok(bruxoService.listarBruxos());
    }

    @GetMapping("/listar-nomes")
    public ResponseEntity<List<String>> listarNomes() {
        return ResponseEntity.ok(bruxoService.listarNomes());
    }

    @GetMapping("/batalha")
    public ResponseEntity<?> batalha(@RequestParam String bruxo1, @RequestParam String bruxo2) {
        try {
            Map<String, String> resultado = bruxoService.batalhar(bruxo1, bruxo2);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
