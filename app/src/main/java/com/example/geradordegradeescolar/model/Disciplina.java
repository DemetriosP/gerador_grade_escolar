package com.example.geradordegradeescolar.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Disciplina implements Serializable {

    private int id;
    private String nome;
    private String diaSemana;
    private String horarioIn;
    private String horarioFn;
    private String situacao;
    private List<Disciplina> requisitos;

    public Disciplina(String nome, String situacao) {
        this.nome = nome;
        this.situacao = situacao;
    }

    public Disciplina(String nome, String situacao, String diaSemana, String horarioIn,
                      String horarioFn) {
        this.nome = nome;
        this.situacao = situacao;
        this.diaSemana = diaSemana;
        this.horarioIn = horarioIn;
        this.horarioFn = horarioFn;
    }

    public Disciplina(int id, String nome, String situacao, String diaSemana, String horarioIn,
                      String horarioFn) {
        this.id = id;
        this.nome = nome;
        this.situacao = situacao;
        this.diaSemana = diaSemana;
        this.horarioIn = horarioIn;
        this.horarioFn = horarioFn;
    }

    public Disciplina() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorarioIn() {
        return horarioIn;
    }

    public void setHorarioIn(String horarioIn) {
        this.horarioIn = horarioIn;
    }

    public String getHorarioFn() {
        return horarioFn;
    }

    public void setHorarioFn(String horarioFn) {
        this.horarioFn = horarioFn;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public List<Disciplina> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<Disciplina> requisitos) {
        this.requisitos = requisitos;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + "\nSituação: " + situacao;
    }

    public int diaDaSemanaParaInt(){

        Map<String, Integer> diasDaSemana = new HashMap<>();

        diasDaSemana.put("Segunda", 1);
        diasDaSemana.put("Terça", 2);
        diasDaSemana.put("Quarta", 3);
        diasDaSemana.put("Quinta", 4);
        diasDaSemana.put("Sexta", 5);

        return diasDaSemana.get(this.diaSemana);

    }

}
