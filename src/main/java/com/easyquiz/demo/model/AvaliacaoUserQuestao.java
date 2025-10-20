package com.easyquiz.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_user_questao",
       uniqueConstraints = @UniqueConstraint(name = "uk_usuario_questao", 
                                            columnNames = {"id_usuario", "id_questao"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoUserQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_questao", nullable = false)
    private Questao questao;

    @Column(nullable = false)
    private Integer nota; // Valor entre 0 e 5

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @CreationTimestamp
    @Column(name = "data_avaliacao", updatable = false)
    private LocalDateTime dataAvaliacao;
}
