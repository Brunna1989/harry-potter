package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "casa", discriminatorType = DiscriminatorType.STRING)
@Table(name = "bruxos")
public abstract class Bruxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(name = "casa", insertable = false, updatable = false)
    private String casa;

    private String descricao;

    public Bruxo(Long id, String nome, String casa) {
        this.id = id;
        this.nome = nome;
        this.casa = casa;
    }

    public String transformarFeitico() {
        return String.format("O bruxo %s é da casa %s!", nome, casa);
    }
}
