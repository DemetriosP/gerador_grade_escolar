package com.example.geradordegradeescolar.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    private SQLiteDatabase conexao;

    public DisciplinaDAO(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }

    public void inserir(Disciplina disciplina) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", disciplina.getNome());
        contentValues.put("SITUACAO", disciplina.getSituacao());
        contentValues.put("DIA_SEMANA", disciplina.getDiaSemana());
        contentValues.put("HORARIO_IN", disciplina.getHorarioIn());
        contentValues.put("HORARIO_FN", disciplina.getHorarioFn());

        conexao.insertOrThrow("DISCIPLINA", null, contentValues);

    }

    public void alterar(Disciplina disciplina) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("SITUACAO", disciplina.getSituacao());
        contentValues.put("DIA_SEMANA", disciplina.getDiaSemana());
        contentValues.put("HORARIO_IN", disciplina.getHorarioIn());
        contentValues.put("HORARIO_FN", disciplina.getHorarioFn());

        conexao.update("DISCIPLINA", contentValues, "NOME = ?", new String[]{disciplina.getNome()});

    }

    public void excluir(Disciplina disciplina) {

        String[] parametros = new String[1];
        parametros[0] = disciplina.getNome();

        conexao.delete("DISCIPLINA", "NOME = ?", parametros);

    }

    public List<Disciplina> buscaTodos() {

        List<Disciplina> disciplinas = new ArrayList<>();

        String sql = "SELECT NOME, SITUACAO, DIA_SEMANA, HORARIO_IN, HORARIO_FN " +
                    "FROM DISCIPLINA ";


        Cursor resultado = conexao.rawQuery(sql, null);

        if(resultado.getCount() > 0){

            resultado.moveToFirst();

            do{

                Disciplina disciplina = new Disciplina();
                disciplina.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                disciplina.setSituacao(resultado.getString(resultado.getColumnIndexOrThrow("SITUACAO")));
                disciplina.setDiaSemana(resultado.getString(resultado.getColumnIndexOrThrow("DIA_SEMANA")));
                disciplina.setHorarioIn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_IN")));
                disciplina.setHorarioFn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_FN")));

                disciplina.setRequisitos(new ArrayList<>());

                disciplinas.add(disciplina);

            }while(resultado.moveToNext());

        }

        return disciplinas;
    }

}
