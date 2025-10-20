package com.br.harrypotter.dto;

public record BatalhaResponseDTO(
        String bruxo1,
        String magia1,
        String bruxo2,
        String magia2,
        String vencedor
) { }
