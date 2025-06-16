package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DUMBLEDORE")
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