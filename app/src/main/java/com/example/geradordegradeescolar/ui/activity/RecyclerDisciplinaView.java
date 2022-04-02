package com.example.geradordegradeescolar.ui.activity;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.DisciplinaDAO;

public class RecyclerDisciplinaView {

    private final Context contexto;
    private final RecyclerDisciplinaAdapter adapter;

    public RecyclerDisciplinaView(Context contexto) {
        this.contexto = contexto;
        this.adapter = new RecyclerDisciplinaAdapter(this.contexto);
    }

    public void atualizaDisciplinas() {

    }

    public void configuraAdapter(RecyclerView disciplinaRecycler) {
        disciplinaRecycler.setAdapter(adapter);
    }

}
