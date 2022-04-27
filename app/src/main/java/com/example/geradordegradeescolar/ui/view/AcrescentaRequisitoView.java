package com.example.geradordegradeescolar.ui.view;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.ui.adapter.AcresentaRequisitoAdapter;

import java.util.List;

public class AcrescentaRequisitoView {

    private final Context contexto;
    private final AcresentaRequisitoAdapter adapter;
    private final DisciplinaDAO disciplinaDAO;

    public AcrescentaRequisitoView(Context contexto, List<Disciplina> requisitos) {
        this.contexto = contexto;
        Conexao conexao = new Conexao(contexto);
        this.disciplinaDAO = new DisciplinaDAO(conexao.getConexao());
        this.adapter = new AcresentaRequisitoAdapter(this.contexto, requisitosValidos(requisitos));
    }

    public List<Disciplina> requisitosValidos(List<Disciplina> requisitos) {
        requisitos.addAll(disciplinaDAO.buscaOndeERequisito(requisitos.get(0).getId()));
        List<Disciplina> todosRequisitos = disciplinaDAO.buscaTodos();

        for (int posicaoRequisitos = 0; posicaoRequisitos < requisitos.size(); posicaoRequisitos++) {
            for (int posicaoTodos = 0; posicaoTodos < todosRequisitos.size(); posicaoTodos++) {
                if (requisitos.get(posicaoRequisitos).getNome().equals(todosRequisitos.get(posicaoTodos).getNome())) {
                    todosRequisitos.remove(posicaoTodos);
                }
            }
        }

        return todosRequisitos;
    }

    public void configuraAdapter(RecyclerView acrescentaRequisito) {
        acrescentaRequisito.setAdapter(adapter);
        acrescentaRequisito.setLayoutManager(new LinearLayoutManager(this.contexto));
    }

    public void acresentaRequisitos(Disciplina disciplina) {
        List<Disciplina> requisitos = adapter.getRequisitosSelecionados();
        disciplinaDAO.insereRequisitosLista(disciplina, requisitos);
    }

}
