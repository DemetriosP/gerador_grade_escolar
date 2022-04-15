package com.example.geradordegradeescolar.ui.view;

import android.content.Context;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.ui.adapter.RecyclerRequisitoAdapter;

import java.util.List;

public class RecyclerRequisitoView {

    private final Context contexto;
    private final RecyclerRequisitoAdapter adapter;
    private final DisciplinaDAO disciplinaDAO;

    public RecyclerRequisitoView(Context contexto, List<Disciplina> requisitos) {
        this.contexto = contexto;
        Conexao conexao = new Conexao(contexto);
        this.disciplinaDAO = new DisciplinaDAO(conexao.getConexao());
        this.adapter = new RecyclerRequisitoAdapter(this.contexto, requisitos);
    }

    public void atualizaRequisito(Disciplina disciplina) {
        adapter.atualiza(disciplinaDAO.buscaRequisitoDisciplina(disciplina.getId()));
    }

    public void configuraAdapter(RecyclerView requisitoRecycler) {
        requisitoRecycler.setAdapter(adapter);
        requisitoRecycler.setLayoutManager(new LinearLayoutManager(this.contexto));
    }

    public void confirmaRemocao(MenuItem item) {
        new AlertDialog.Builder(contexto)
                .setTitle("Remover requisito")
                .setMessage("Tem certeza que quer remover o requisito?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    Disciplina requisitoEscolhida = adapter.getItem(item.getGroupId());
                    removeRequisito(requisitoEscolhida);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void removeRequisito(Disciplina requisito) {
        disciplinaDAO.excluirRequisito(requisito);
        adapter.remove(requisito);
    }

    public RecyclerRequisitoAdapter getAdapter() {
        return adapter;
    }

}
