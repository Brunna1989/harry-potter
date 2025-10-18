package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Grifinória")
public class BruxoGrifinoria extends Bruxo implements Magia {

    public BruxoGrifinoria() {
        super();
    }

    public BruxoGrifinoria(String nome) {
        super(null, nome, "Grifinória");
    }

    @Override
    public String lancarFeitico() {
        return "Expelliarmus! — O bruxo da Grifinória lançou seu feitiço!";
    }
}
