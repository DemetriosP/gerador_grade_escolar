package com.example.geradordegradeescolar.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;

public class Disciplina implements Serializable {

    private String nome;
    private String diaSemana;
    private String horarioIn;
    private String horarioFn;
    private String situacao;
    private ArrayList<Disciplina> preRequisitos;

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

    @NonNull
    @Override
    public String toString() {
        return nome + "\nSituação: " + situacao;
    }

}
