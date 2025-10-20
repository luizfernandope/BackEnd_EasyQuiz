package com.easyquiz.demo.controller;

import com.easyquiz.demo.model.Disciplina;
import com.easyquiz.demo.model.NivelDificuldade;
import com.easyquiz.demo.model.Questao;
import com.easyquiz.demo.model.Usuario;
import com.easyquiz.demo.repository.DisciplinaRepository;
import com.easyquiz.demo.repository.QuestaoRepository;
import com.easyquiz.demo.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoRepository questaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DisciplinaRepository disciplinaRepository;

    public QuestaoController(QuestaoRepository questaoRepository, UsuarioRepository usuarioRepository, DisciplinaRepository disciplinaRepository) {
        this.questaoRepository = questaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    @GetMapping
    public List<Questao> listarQuestoes() {
        return questaoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Questao> cadastrarQuestao(@RequestBody Map<String, Object> questao) {
        Usuario userCriador = usuarioRepository.findById(Long.valueOf(questao.get("id_user").toString()))
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Disciplina disciplina = disciplinaRepository.findById(Long.valueOf(questao.get("id_disciplina").toString()))
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        NivelDificuldade nivelDificuldade = NivelDificuldade.valueOf(questao.get("nivel_dificuldade").toString());

        Questao novaQuestao = new Questao();
        novaQuestao.setEnunciado((String) questao.get("enunciado"));
        novaQuestao.setNivelDificuldade(nivelDificuldade);
        novaQuestao.setTipoPergunta((String) questao.get("tipo_pergunta"));
        novaQuestao.setUsuarioCriador(userCriador);
        novaQuestao.setDisciplina(disciplina);
        novaQuestao.setIsPublica((Boolean) questao.get("is_publica"));

        questaoRepository.save(novaQuestao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaQuestao);
        /*
        {
            "id_user": 2,
            "id_disciplina": 2,
            "nivel_dificuldade": "Difícil",
            "enunciado": "Peerguntinha zé",
            "tipo_pergunta": "Multipla Escolha",
            "is_publica": true
        }
        */ 
    }

   @DeleteMapping("/deleteLogico/{id}")
    public ResponseEntity<Void> deletarViaAtributo(@PathVariable Long id) {
        if (!questaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Questao questao = questaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Questão não encontrada"));
        questao.setIsDeleted(true);
        questaoRepository.save(questao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteDoBanco/{id}")
    public ResponseEntity<Void> deletarQuestao(@PathVariable Long id) {
        if (!questaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        questaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
