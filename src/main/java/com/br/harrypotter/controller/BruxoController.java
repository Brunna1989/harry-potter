package com.br.harrypotter.controller;

import com.br.harrypotter.dto.*;
import com.br.harrypotter.service.BruxoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bruxos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BruxoController {

    private final BruxoService service;

    @PostMapping
    public ResponseEntity<BruxoResponseDTO> criarBruxo(@RequestBody BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.criarBruxo(dto));
    }
}
