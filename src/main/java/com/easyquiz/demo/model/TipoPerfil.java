package com.easyquiz.demo.model;

public enum TipoPerfil {
    Professor("Professor"),
    Estudante("Estudante");

    private final String descricao;

    TipoPerfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
