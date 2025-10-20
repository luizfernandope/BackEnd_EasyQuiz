package com.easyquiz.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "opcao_resposta_questao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoRespostaQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_questao", nullable = false)
    private Questao questao;

    @Column(name = "texto_opcao", nullable = false, length = 500)
    private String textoOpcao;

    @Column(name = "is_correta", nullable = false)
    private Boolean isCorreta = false;
}
