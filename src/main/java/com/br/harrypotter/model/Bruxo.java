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

    private String casa;

    public Bruxo(Long id, String nome, String casa) {
        this.id = id;
        this.nome = nome;
        this.casa = casa;
    }

    public Bruxo() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCasa() {
        return casa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String lancarFeitico();

    @Override
    public String toString() {
        return "Bruxo{id=" + id + ", nome='" + nome + "', casa='" + casa + "'}";
    }
}
