package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_bruxo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bruxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int idade;

    public Bruxo(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
}