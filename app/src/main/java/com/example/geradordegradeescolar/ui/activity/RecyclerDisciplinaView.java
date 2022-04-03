package com.example.geradordegradeescolar.ui.activity;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;

public class RecyclerDisciplinaView {

    private Context contexto;
    private RecyclerDisciplinaAdapter adapter;
    private DisciplinaDAO disciplinaDAO;
    private Conexao conexao;

    public RecyclerDisciplinaView(Context contexto) {
        this.contexto = contexto;
        this.conexao = new Conexao(contexto);
        this.disciplinaDAO = new DisciplinaDAO(conexao.getConexao());
        this.adapter = new RecyclerDisciplinaAdapter(this.contexto, this.disciplinaDAO.buscaTodos());
    }

    public void atualizaDisciplinas() {
        adapter.atualiza(disciplinaDAO.buscaTodos());
    }

    public void configuraAdapter(RecyclerView disciplinaRecycler) {
        disciplinaRecycler.setAdapter(adapter);
        disciplinaRecycler.setLayoutManager(new LinearLayoutManager(this.contexto));
    }

}
