package com.br.harrypotter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BruxoResponseDTO {
    private Long id;
    private String nome;
    private int idade;
    private String casa;
    private String magia;
}
