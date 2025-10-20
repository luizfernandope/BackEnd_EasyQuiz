package com.easyquiz.demo.repository;

import com.easyquiz.demo.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    boolean existsByNome(String nome);
}
