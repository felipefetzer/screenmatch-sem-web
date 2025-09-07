package com.felipe.Tarefa001.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tarefa {

    @JsonProperty
    private String descricao;

    @JsonProperty
    private boolean concluida;

    @JsonProperty
    private String pessoaResponsavel;

    public Tarefa() {
    }

    public Tarefa(boolean concluida, String pessoaResponsavel, String descricao) {
        this.concluida = concluida;
        this.pessoaResponsavel = pessoaResponsavel;
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        return "Desc: " + descricao
                + "\nEstá Concluída? " + (concluida ? "Sim" : "Não")
                + "\nResponsável: " + pessoaResponsavel;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPessoaResponsavel() {
        return pessoaResponsavel;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public void setPessoaResponsavel(String pessoaResponsavel) {
        this.pessoaResponsavel = pessoaResponsavel;
    }
}
