package com.br.harrypotter.dto;

public record BatalhaResponseDTO(
        String nomeBruxo1,
        String feiticoBruxo1,
        String nomeBruxo2,
        String feiticoBruxo2,
        String vencedor
) {
    @Override
    public String toString() {
        return String.format(
                "Batalha entre %s (%s) e %s (%s) - Vencedor: %s",
                nomeBruxo1, feiticoBruxo1, nomeBruxo2, feiticoBruxo2, vencedor
        );
    }
}
