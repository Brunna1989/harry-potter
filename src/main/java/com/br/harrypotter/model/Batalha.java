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
        @JoinColumn(name = "bruxo1_id")
        private Bruxo bruxo1;

        @ManyToOne(optional = false)
        @JoinColumn(name = "bruxo2_id")
        private Bruxo bruxo2;

        private Long vencedorId;

        private String resultado;


    }

