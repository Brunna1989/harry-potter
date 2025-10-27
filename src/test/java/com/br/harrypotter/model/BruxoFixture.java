package com.br.harrypotter.model;

import com.br.harrypotter.model.Bruxo;

public class BruxoFixture {

    public static Bruxo criarBruxoPadrao() {
        return new BruxoFake();
    }

    public static class BruxoFake extends Bruxo {
        public BruxoFake() {
            super(1L, "Harry Potter");
        }

        @Override
        public String lancarFeitico() {
            return "Feitiço lançado com sucesso!";
        }

        @Override
        public String toString() {
            return "BruxoFake{id=" + getId() + ", nome='" + getNome() + "', casa='" + getCasa() + "'}";
        }

        @Override
        public String getCasa() {
            return "Grifinória";
        }
    }
}
