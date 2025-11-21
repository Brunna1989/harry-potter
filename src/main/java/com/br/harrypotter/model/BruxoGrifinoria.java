package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@DiscriminatorValue("Grifinoria")
public class BruxoGrifinoria extends Bruxo {

    @Builder
    public BruxoGrifinoria(Long id, String nome) {
        super(id, nome);
    }

    @Override
    public String lancarFeitico() {
        return "Expelliarmus! O bruxo da Grifinoria lançou seu feitiço!";
    }

    @Override
    public String getCasa() {
        return "Grifinoria";
    }
}
