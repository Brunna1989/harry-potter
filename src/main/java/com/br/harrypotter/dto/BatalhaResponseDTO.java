package com.br.harrypotter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BatalhaResponseDTO {
    private String bruxo1;
    private String magia1;
    private String bruxo2;
    private String magia2;
    private String vencedor;
}
