package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Grifinória")
@NoArgsConstructor
public class BruxoGrifinoria extends Bruxo {

    @Builder
    public BruxoGrifinoria(Long id, String nome) {
        super(id, nome, "Grifinória");
    }

    public BruxoGrifinoria(String nome) {
        super(null, nome, "Grifinória");
    }

    @Override
    public String lancarFeitico() {
        return "Expelliarmus! Bruxo: " + getNome() + ", Casa: " + getCasa();
    }

    @Override
    public String toString() {
        return "BruxoGrifinoria{id=" + getId() + ", nome='" + getNome() + "', casa='" + getCasa() + "'}";
    }
}
