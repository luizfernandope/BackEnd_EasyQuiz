package com.easyquiz.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Questao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_criador", nullable = false)
    private Usuario usuarioCriador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_disciplina", nullable = false)
    private Disciplina disciplina;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Column(name = "tipo_pergunta", nullable = false)
    private String tipoPergunta;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_dificuldade", nullable = false)
    private NivelDificuldade nivelDificuldade;

    @Column(name = "is_publica", nullable = false)
    private Boolean isPublica = false;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_ultima_modificacao")
    private LocalDateTime dataUltimaModificacao;

    // Relacionamentos
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcaoRespostaQuestao> opcoesResposta;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<AvaliacaoUserQuestao> avaliacoes;

    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL)
    private List<ProvaQuestao> provasQuestoes;
}
