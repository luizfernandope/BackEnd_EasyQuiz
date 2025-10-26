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
@Table(name = "log_cadastro")
public class LogCadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "admin_id", nullable = false)
    private Integer adminId;

    @Column(name = "professor_id", nullable = false)
    private Integer professorId;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;
}