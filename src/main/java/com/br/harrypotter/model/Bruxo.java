package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // ou JOINED se quiser uma tabela por tipo
@DiscriminatorColumn(name = "casa", discriminatorType = DiscriminatorType.STRING)
public abstract class Bruxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String casa;

    public String transformarFeitico() {
        return String.format("O bruxo %s é da casa %s!", nome, casa);
    }
}
