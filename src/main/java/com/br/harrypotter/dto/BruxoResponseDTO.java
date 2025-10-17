package com.br.harrypotter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BruxoResponseDTO {
    private String nome;
    private String casa;
    private String mensagemFeitico;
}
