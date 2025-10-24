package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "casa", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Bruxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String casa;

    public abstract String lancarFeitico();

    @Override
    public String toString() {
        return "Bruxo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", casa='" + casa + '\'' +
                '}';
    }
}
