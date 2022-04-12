package com.example.geradordegradeescolar.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;

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

    public void confirmaRemocao(MenuItem item) {
        new AlertDialog.Builder(contexto)
                .setTitle("Remover disciplina")
                .setMessage("Tem certeza que quer remover a disciplina?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    Disciplina disciplinaEscolhida = adapter.getItem(item.getGroupId());
                    removeDisciplina(disciplinaEscolhida);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void removeDisciplina(Disciplina disciplina){
        disciplinaDAO.excluir(disciplina);
        adapter.remove(disciplina);
    }

    public Disciplina buscaDisciplina(int posicao){
        return adapter.getItem(posicao);
    }

}
