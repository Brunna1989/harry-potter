package com.br.harrypotter.repository;

import com.br.harrypotter.model.Bruxo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BruxoRepository extends JpaRepository<Bruxo, Long> { }
