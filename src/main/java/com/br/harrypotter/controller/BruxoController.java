package com.br.harrypotter.controller;

import com.br.harrypotter.dto.BruxoRequestDTO;
import com.br.harrypotter.dto.BruxoResponseDTO;
import com.br.harrypotter.model.Bruxo;
import com.br.harrypotter.service.BruxoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bruxos")
@RequiredArgsConstructor
public class BruxoController {

    private final BruxoService service;

    @PostMapping
    public ResponseEntity<BruxoResponseDTO> criar(@RequestBody BruxoRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<BruxoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BruxoResponseDTO> buscarPorId(@PathVariable Long id) {

        Bruxo bruxo = service.buscarPorId(id);

        BruxoResponseDTO resposta = new BruxoResponseDTO(
                bruxo.getId(),
                bruxo.getNome(),
                bruxo.getCasa(),
                bruxo.lancarFeitico()
        );

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        service.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
