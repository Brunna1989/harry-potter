package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("Sonserina")
public class BruxoSonserina extends Bruxo {

    @Builder
    public BruxoSonserina(Long id, String nome) {
        super(id, nome);
    }

    @Override
    public String lancarFeitico() {
        return "Serpensortia! O bruxo da Sonserina lançou seu feitiço!";
    }

    @Override
    public String getCasa() {
        return "Sonserina";
    }
}
