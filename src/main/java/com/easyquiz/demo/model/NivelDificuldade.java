package com.easyquiz.demo.model;

public enum NivelDificuldade {
    Fácil("Fácil"),
    Médio("Médio"),
    Difícil("Difícil");

    private final String descricao;

    NivelDificuldade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
