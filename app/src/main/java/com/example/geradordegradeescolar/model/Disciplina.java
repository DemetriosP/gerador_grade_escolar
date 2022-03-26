package com.example.geradordegradeescolar.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Disciplina implements Serializable {

    private String nome;
    private String diaSemana;
    private int horarioIn;
    private int horarioFn;
    private String situacao;
    private ArrayList<Disciplina> preRequisitos;

    public Disciplina(String nome, String situacao){
        this.nome = nome;
        this.situacao = situacao;
    }

    public Disciplina(String nome, String situacao, String diaSemana, int horarioIn, int horarioFn) {
        this.nome = nome;
        this.situacao = situacao;
        this.diaSemana = diaSemana;
        this.horarioIn = horarioIn;
        this.horarioFn = horarioFn;
    }

    public Disciplina() {
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

    public int getHorarioIn() {
        return horarioIn;
    }

    public void setHorarioIn(int horarioIn) {
        this.horarioIn = horarioIn;
    }

    public int getHorarioFn() {
        return horarioFn;
    }

    public void setHorarioFn(int horarioFn) {
        this.horarioFn = horarioFn;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + "\nSituação: " + situacao;
    }
}
