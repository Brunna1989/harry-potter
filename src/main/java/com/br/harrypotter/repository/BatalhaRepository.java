package com.br.harrypotter.repository;

import com.br.harrypotter.model.Batalha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatalhaRepository extends JpaRepository<Batalha,Long> {
}
