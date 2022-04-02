package com.example.geradordegradeescolar.ui.activity;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.DisciplinaDAO;

public class RecyclerDisciplinaView {

    private final Context contexto;
    private final RecyclerDisciplinaAdapter adapter;
    private final DisciplinaDAO dao;

    public RecyclerDisciplinaView(Context contexto){
        this.contexto = contexto;
        this.adapter = new RecyclerDisciplinaAdapter(this.contexto);
        dao = new DisciplinaDAO();
    }

    public void atualizaDisciplinas(){
        adapter.atualiza(dao.todasDisciplinas());
    }

    public void configuraAdapter(RecyclerView disciplinaRecycler){
        disciplinaRecycler.setAdapter(adapter);
    }

}
