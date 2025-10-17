package com.br.harrypotter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BruxoResponseDTO {
    private String nome;
    private String casa;
    private String mensagemFeitico;
}
