package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BatalhaRequestDto;
import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.model.Batalha;
import com.br.harrypotter.service.BatalhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batalhas")
@RequiredArgsConstructor
public class BatalhaController {

    private final BatalhaService service;

    @PostMapping
    public ResponseEntity<BatalhaResponseDTO> batalhar(@RequestBody BatalhaRequestDto dto) {
        return ResponseEntity.ok(service.batalhar(dto));
    }

    @GetMapping("/historico")
    public ResponseEntity<List<Batalha>> listarHistorico() {
        return ResponseEntity.ok(service.listarHistorico());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Batalha> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
