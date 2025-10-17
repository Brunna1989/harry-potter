package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Sonserina")
@NoArgsConstructor
@SuperBuilder
public class BruxoSonserina extends Bruxo implements Magia {

    public BruxoSonserina(String nome) {
        super(null, nome, "Sonserina");
    }

    @Override
    public String lancarFeitico() {
        return "Serpensortia! - O bruxo da Sonserina lançou seu feitiço!";
    }
}
