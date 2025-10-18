package com.br.harrypotter.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "casa", discriminatorType = DiscriminatorType.STRING)
public abstract class Bruxo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "casa", insertable = false, updatable = false)
    private String casa;

    public Bruxo() {
    }

    public Bruxo(Long id, String nome, String casa) {
        this.id = id;
        this.nome = nome;
        this.casa = casa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

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
