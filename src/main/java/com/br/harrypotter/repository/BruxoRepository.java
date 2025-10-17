package com.br.harrypotter.repository;

import com.br.harrypotter.model.Bruxo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BruxoRepository extends JpaRepository<Bruxo, Long> {
    Optional<Bruxo> findByNome(String nome);
}
