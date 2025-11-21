package com.br.harrypotter.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "batalhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Batalha {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "atacante_id")
        private Bruxo bruxoAtacante;

        @ManyToOne(optional = false)
        @JoinColumn(name = "defensor_id")
        private Bruxo bruxoDefensor;

        private Long vencedorId;

        private String resultado;

        private LocalDateTime instante;
    }

