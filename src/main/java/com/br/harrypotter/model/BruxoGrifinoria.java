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
        super(id, nome);
    }

    public BruxoGrifinoria(String nome) {
        super(null, nome);
    }

    @Override
    public String lancarFeitico() {
        return "Expelliarmus! Bruxo: " + getNome() + ", Casa: Grifinória";
    }

    @Override
    public String toString() {
        return "BruxoGrifinoria{id=" + getId() + ", nome='" + getNome() + "', casa='Grifinória'}";
    }

    public String getCasa() {
        return "Grifinória";
    }
}