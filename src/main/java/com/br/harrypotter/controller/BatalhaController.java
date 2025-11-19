package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BatalhaResponseDTO;
import com.br.harrypotter.service.BatalhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batalhas")
@RequiredArgsConstructor
public class BatalhaController {

    private final BatalhaService service;

    @GetMapping
    public ResponseEntity<BatalhaResponseDTO> batalhar(
            @RequestParam String bruxo1,
            @RequestParam String bruxo2
    ) {
        return ResponseEntity.ok(service.batalhar(bruxo1, bruxo2));
    }
}
