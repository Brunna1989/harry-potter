package com.br.harrypotter.model;

public class BruxoAlvoDumbledore extends Bruxo implements Magia {

    public BruxoAlvoDumbledore() {
        super("Alvo Dumbledore", 115);
    }

    public BruxoAlvoDumbledore(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public String lancarMagia() {
        return "Protego Maxima! 🛡️";
    }
}
