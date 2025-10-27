package com.br.harrypotter.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "casa")
public abstract class Bruxo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Bruxo(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Bruxo() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String lancarFeitico();
    public abstract String getCasa();

    @Override
    public String toString() {
        return "Bruxo{id=" + id + ", nome='" + nome + "'}";
    }
}
