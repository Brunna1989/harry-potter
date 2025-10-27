package com.br.harrypotter.test.model;

import com.br.harrypotter.model.Bruxo;

public class BruxoFixture {

    public static Bruxo criarBruxoPadrao() {
        return new BruxoFake();
    }

    public static class BruxoFake extends Bruxo {
        public BruxoFake() {
            super(1L, "Harry Potter", "Grifinória");
        }

        @Override
        public String lancarFeitico() {
            return "Feitiço lançado com sucesso!";
        }

        @Override
        public String toString() {
            return "BruxoFake{id=" + getId() + ", nome='" + getNome() + "', casa='" + getCasa() + "'}";
        }
    }
}
