package com.example.geradordegradeescolar.ui.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.ui.adapter.RecyclerDisciplinaAdapter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RecyclerDisciplinaView {

    private final Context contexto;
    private final RecyclerDisciplinaAdapter adapter;

    public Conexao getConexao() {
        return conexao;
    }

    private final Conexao conexao;
    public DisciplinaDAO getDisciplinaDAO() {
        return disciplinaDAO;
    }

    private final DisciplinaDAO disciplinaDAO;

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

    public void removeDisciplina(Disciplina disciplina) {
        disciplinaDAO.excluiDisciplina(disciplina);
        adapter.remove(disciplina);
    }

    public Disciplina buscaDisciplina(int posicao) {
        return adapter.getItem(posicao);
    }

    public void alertaNaoPodeRemover() {
        new AlertDialog.Builder(contexto)
                .setTitle("Ação negada")
                .setMessage("Disciplina não pode ser removida, ela é pré-requisito de outra disciplina.")
                .setNeutralButton("Ok", null)
                .show();
    }

    public boolean eRequisito(Disciplina disciplina) {
        return disciplinaDAO.eRequisito(disciplina);
    }

    public RecyclerDisciplinaAdapter getAdapter() {
        return adapter;
    }


}
