package com.br.harrypotter.test.DTOs;

import com.br.harrypotter.dto.BruxoRequestDTO;

public class BruxoFixture {

    public static BruxoRequestDTO criarBruxoPadrao() {
        return new BruxoRequestDTO("Harry Potter", "Grifinória");
    }

    public static BruxoRequestDTO criarBruxoPersonalizado(String nome, String casa) {
        return new BruxoRequestDTO(nome, casa);
    }
}