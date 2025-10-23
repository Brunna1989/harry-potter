package com.br.harrypotter.test.model;

import com.br.harrypotter.model.Bruxo;

public class BruxoFixture {

    public static class BruxoFake extends Bruxo {
        public BruxoFake(Long id, String nome, String casa) {
            super(id, nome, casa);
        }

        @Override
        public String lancarFeitico() {
            return "Feitiço lançado com sucesso!";
        }
    }

    public static Bruxo criarBruxoPadrao() {
        return new BruxoFake(1L, "Harry Potter", "Grifinória");
    }

    public static Bruxo criarBruxoPersonalizado(Long id, String nome, String casa) {
        return new BruxoFake(id, nome, casa);
    }
}
