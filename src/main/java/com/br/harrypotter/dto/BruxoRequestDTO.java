package com.br.harrypotter.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BruxoRequestDTO {
    private String nome;
    private String casa;
}
