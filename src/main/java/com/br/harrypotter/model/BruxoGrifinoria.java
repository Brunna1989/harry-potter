package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Grifinória")
@NoArgsConstructor
@SuperBuilder
public class BruxoGrifinoria extends Bruxo implements Magia {

    public BruxoGrifinoria(String nome) {
        super(null, nome, "Grifinória");
    }

    @Override
    public String lancarFeitico() {
        return "Expelliarmus! - O bruxo da Grifinória lançou seu feitiço!";
    }
}
