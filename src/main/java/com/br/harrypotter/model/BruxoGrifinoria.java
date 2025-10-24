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
        // Ajustado para incluir a casa (Grifinória) e o nome do bruxo
        return getNome() + " lança o feitiço Expelliarmus pela coragem da casa Grifinória!";
    }

    @Override
    public String toString() {
        return "BruxoGrifinoria{" +
                "nome='" + getNome() + '\'' +
                ", casa='" + getCasa() + '\'' +
                '}';
    }
}
