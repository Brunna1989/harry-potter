package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Sonserina")
public class BruxoSonserina extends Bruxo implements Magia {

    public BruxoSonserina() {
        super();
    }

    public BruxoSonserina(String nome) {
        super(null, nome, "Sonserina");
    }

    @Override
    public String lancarFeitico() {
        return "Serpensortia! — O bruxo da Sonserina lançou seu feitiço!";
    }
}