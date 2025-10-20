package com.easyquiz.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyquiz.demo.model.Disciplina;
import com.easyquiz.demo.repository.DisciplinaRepository;

import org.springframework.web.bind.annotation.RequestBody; // request body import corrigido (swagger só deu certo depois disso)

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    
    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaController(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }
    @GetMapping
    public List<Disciplina> listarDisciplinas() {
        return disciplinaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Disciplina> criarDisciplina(@RequestBody Disciplina disciplina) {
        if(disciplinaRepository.existsByNome(disciplina.getNome())) {
            throw new RuntimeException("Disciplina já cadastrada com nome: " + disciplina.getNome());
        }
        return ResponseEntity.ok(disciplinaRepository.save(disciplina));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> atualizarDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplina) {

        disciplina.setId(id);
        return disciplinaRepository.findById(id)
            .map(existingDisciplina -> {
                existingDisciplina.setNome(disciplina.getNome());
                disciplinaRepository.save(existingDisciplina);
                return ResponseEntity.ok(existingDisciplina);
            })
            .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com id " + id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new RuntimeException("Disciplina não encontrada com id " + id);
        }
        disciplinaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
