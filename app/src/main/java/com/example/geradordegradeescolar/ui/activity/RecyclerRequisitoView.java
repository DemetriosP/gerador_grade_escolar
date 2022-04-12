package com.example.geradordegradeescolar.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.List;

public class RecyclerRequisitoView {

    private Context contexto;
    private RecyclerRequisitoAdapter adapter;
    private DisciplinaDAO disciplinaDAO;
    private Conexao conexao;

    public RecyclerRequisitoView(Context contexto, List<Disciplina> requisitos){
        this.contexto = contexto;
        this.conexao = new Conexao(contexto);
        this.disciplinaDAO = new DisciplinaDAO(conexao.getConexao());
        this.adapter = new RecyclerRequisitoAdapter(this.contexto, requisitos);
    }

    /*
    public void atualizaRequisito() {
        adapter.atualiza(disciplinaDAO.buscaTodos());
    }
     */

    public void configuraAdapter(RecyclerView requisitoRecycler) {
        requisitoRecycler.setAdapter(adapter);
        requisitoRecycler.setLayoutManager(new LinearLayoutManager(this.contexto));
    }

    /*

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

     */

    /*
    public void removeRequisito(Disciplina requisito){
        disciplinaDAO.excluirRequisito(requisito);
        adapter.remove(requisito);
    }

     */

    public Disciplina buscaRequisito(int posicao){
        return adapter.getItem(posicao);
    }


}
