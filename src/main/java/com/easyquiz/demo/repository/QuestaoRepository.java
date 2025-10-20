package com.easyquiz.demo.repository;

import com.easyquiz.demo.model.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestaoRepository extends JpaRepository<Questao, Long> {
    // Métodos customizados podem ser adicionados aqui se necessário
}
