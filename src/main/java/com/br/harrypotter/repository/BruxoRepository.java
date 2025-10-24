package com.br.harrypotter.repository;

import com.br.harrypotter.model.Bruxo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BruxoRepository extends JpaRepository<Bruxo, Long> {
    Optional<Bruxo> findByNome(String nome);
}
