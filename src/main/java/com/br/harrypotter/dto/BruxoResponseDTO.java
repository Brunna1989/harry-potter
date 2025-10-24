package com.br.harrypotter.dto;

public record BruxoResponseDTO(
        Long id,
        String nome,
        String casa,
        String feitico
) {
    @Override
    public String toString() {
        return String.format("Bruxo{id=%d, nome='%s', casa='%s', feitico='%s'}",
                id, nome, casa, feitico);
    }
}
