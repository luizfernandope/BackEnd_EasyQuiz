package com.easyquiz.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_perfil", nullable = false)
    private TipoPerfil tipoPerfil;

    @CreationTimestamp
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    // Relacionamentos
    @OneToMany(mappedBy = "usuarioCriador", cascade = CascadeType.ALL)
    private List<Questao> questoesCriadas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AvaliacaoUserQuestao> avaliacoes;

    @OneToMany(mappedBy = "usuarioGerador", cascade = CascadeType.ALL)
    private List<Prova> provasGeradas;
}
