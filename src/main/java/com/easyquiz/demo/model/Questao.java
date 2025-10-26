package com.easyquiz.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questao")
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    // ENUM virou String
    @Column(name = "dificuldade", nullable = false)
    private String dificuldade;

    // ENUM virou String
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_modificacao")
    private LocalDateTime dataUltimaModificacao;

    @Column(name = "disciplina_id")
    private Integer disciplinaId;

    @Column(name = "criado_por")
    private Integer criadoPor;
}