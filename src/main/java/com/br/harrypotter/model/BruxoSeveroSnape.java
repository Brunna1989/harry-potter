package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SNAPE")
public class BruxoSeveroSnape extends Bruxo implements Magia {

    public BruxoSeveroSnape() {
        super("Severo Snape", 48);
    }

    public BruxoSeveroSnape(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public String lancarMagia() {
        return "Sectumsempra! ⚔️";
    }
}