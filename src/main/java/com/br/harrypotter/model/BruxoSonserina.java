package com.br.harrypotter.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Sonserina")
@NoArgsConstructor
public class BruxoSonserina extends Bruxo {

    @Builder
    public BruxoSonserina(Long id, String nome) {
        super(id, nome);
    }

    public BruxoSonserina(String nome) {
        super(null, nome);
    }

    public String getCasa() {
        return "Sonserina";
    }

    @Override
    public String lancarFeitico() {
        return "Serpensortia!";
    }
}
