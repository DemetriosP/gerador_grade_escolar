package com.example.geradordegradeescolar.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Curso {

    private String nome;
    private int semestres;
    private Map<Integer, ArrayList<Disciplina>> disciplinasPorSemestre = new HashMap<>();

    public Curso(String nome, int semestres) {
        this.nome = nome;
        this.semestres = semestres;
        criarMapa(semestres);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestres() {
        return semestres;
    }

    public void setSemestres(int semestres) {
        this.semestres = semestres;
    }

    public Map<Integer, ArrayList<Disciplina>> getDisciplinasPorSemestre() {
        return disciplinasPorSemestre;
    }

    public void setDisciplinasPorSemestre(Map<Integer, ArrayList<Disciplina>> mapa) {
        this.disciplinasPorSemestre = mapa;
    }

    public void adicionaDisciplinaNoSemestre(int semestre, Disciplina disciplina) {
        Objects.requireNonNull(this.disciplinasPorSemestre.get(semestre)).add(disciplina);
    }

    public void criarMapa(int semestres) {
        for(int semestre = 1; semestre <= semestres; semestre++) {
            this.disciplinasPorSemestre.put(semestre, new ArrayList<>());
        }
    }

    /*
    public static ArrayList<Curso> pesquisaCurso(ArrayList<Curso> cursos, String nome) {

        ArrayList<Curso> cursosPesquisados = new ArrayList<>();

        for(Curso curso:cursos) {
            if(curso.getNome().toLowerCase().contains(nome.toLowerCase()))
                cursosPesquisados.add(curso);
        }

        return cursosPesquisados;
    }
    */

}
