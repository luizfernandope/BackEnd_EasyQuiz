package com.easyquiz.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyquiz.demo.model.OpcaoResposta;
import java.util.List;

public interface OpcaoRespostaRepository extends JpaRepository<OpcaoResposta, Integer> {
    
    // Método para buscar todas as opções de resposta de uma questão específica
    List<OpcaoResposta> findByQuestaoId(Integer questaoId);
}