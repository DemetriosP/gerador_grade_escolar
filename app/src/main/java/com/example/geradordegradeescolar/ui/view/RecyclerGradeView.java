package com.example.geradordegradeescolar.ui.view;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.model.Grade;
import com.example.geradordegradeescolar.ui.adapter.RecyclerGradeAdapter;

import java.util.List;

public class RecyclerGradeView {

    private final Context contexto;
    private final RecyclerGradeAdapter adapter;
    private final DisciplinaDAO disciplinaDAO;
    private final Grade grade;
    private List<Disciplina> gradeGerada;

    public RecyclerGradeView(Context contexto) {
        this.contexto = contexto;
        Conexao conexao = new Conexao(contexto);
        this.disciplinaDAO = new DisciplinaDAO(conexao.getConexao());
        this.grade = new Grade();
        gradeGerada = grade.geraGrade(disciplinaDAO.buscaTodos(), contexto);
        this.adapter = new RecyclerGradeAdapter(this.contexto, gradeGerada);
    }

    public void configuraAdapter(RecyclerView gradeRecycler) {
        gradeRecycler.setAdapter(adapter);
        gradeRecycler.setLayoutManager(new LinearLayoutManager(this.contexto));
    }




}
