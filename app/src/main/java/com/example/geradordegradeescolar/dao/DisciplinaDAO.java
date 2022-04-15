package com.example.geradordegradeescolar.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisciplinaDAO {

    private final SQLiteDatabase conexao;

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

    public void excluiDisciplina(Disciplina disciplina) {

        String[] parametros = new String[1];
        parametros[0] = disciplina.getNome();

        if (temRequisito(disciplina.getNome())) conexao.delete("PRE_REQUISITO",
                "DISCIPLINA_NOME = ?", parametros);

        conexao.delete("DISCIPLINA", "NOME = ?", parametros);
    }

    public List<Disciplina> buscaTodos() {

        List<Disciplina> disciplinas = new ArrayList<>();

        String sql = "SELECT * FROM DISCIPLINA";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Disciplina disciplina = new Disciplina();
                disciplina.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                disciplina.setSituacao(resultado.getString(resultado.getColumnIndexOrThrow("SITUACAO")));
                disciplina.setDiaSemana(resultado.getString(resultado.getColumnIndexOrThrow("DIA_SEMANA")));
                disciplina.setHorarioIn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_IN")));
                disciplina.setHorarioFn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_FN")));

                if (temRequisito(resultado.getString(resultado.getColumnIndexOrThrow("NOME")))) {
                    disciplina.setRequisitos(buscaRequisitoDisciplina(resultado.getString(resultado.getColumnIndexOrThrow("NOME"))));
                } else disciplina.setRequisitos(new ArrayList<>());

                disciplinas.add(disciplina);

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplinas;
    }

    public List<Disciplina> buscaRequisitoDisciplina(String nomeDisciplina) {

        List<Disciplina> requisitos = new ArrayList<>();

        String sql = "SELECT DISCIPLINA_NOME_REQUISITO FROM DISCIPLINA\n" +
                "INNER JOIN PRE_REQUISITO ON DISCIPLINA.NOME = PRE_REQUISITO.DISCIPLINA_NOME\n" +
                "WHERE NOME = '" + nomeDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            Disciplina disciplina;

            do {
                String nome = resultado.getString(resultado.getColumnIndexOrThrow("DISCIPLINA_NOME_REQUISITO"));
                disciplina = buscaDisciplinaPorNome(nome);
                requisitos.add(disciplina);
            } while (resultado.moveToNext());

        }

        resultado.close();
        return requisitos;
    }

    public Disciplina buscaDisciplinaPorNome(String nomeDisciplina) {

        Disciplina disciplina = new Disciplina();

        String sql = "SELECT * FROM DISCIPLINA WHERE NOME = '" + nomeDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                disciplina.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                disciplina.setSituacao(resultado.getString(resultado.getColumnIndexOrThrow("SITUACAO")));
                disciplina.setDiaSemana(resultado.getString(resultado.getColumnIndexOrThrow("DIA_SEMANA")));
                disciplina.setHorarioIn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_IN")));
                disciplina.setHorarioFn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_FN")));

                if (temRequisito(resultado.getString(resultado.getColumnIndexOrThrow("NOME")))) {
                    disciplina.setRequisitos(buscaRequisitoDisciplina(resultado.getString(resultado.getColumnIndexOrThrow("NOME"))));
                } else disciplina.setRequisitos(new ArrayList<>());

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplina;

    }

    public boolean temRequisito(String nome) {

        String sql = "SELECT DISCIPLINA_NOME_REQUISITO\n" +
                "FROM DISCIPLINA\n" +
                "INNER JOIN PRE_REQUISITO ON DISCIPLINA.NOME = PRE_REQUISITO.DISCIPLINA_NOME\n" +
                "WHERE NOME = '" + nome + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;

    }

    public void insereRequisitos(Disciplina disciplina, List<Disciplina> requisitos) {

        ContentValues contentValues = new ContentValues();

        for (Disciplina requisito : requisitos) {
            contentValues.put("DISCIPLINA_NOME", disciplina.getNome());
            contentValues.put("DISCIPLINA_NOME_REQUISITO", requisito.getNome());
            conexao.insertOrThrow("PRE_REQUISITO", null, contentValues);
        }

    }

    public void excluirRequisito(Disciplina requisito) {

        String[] parametros = new String[1];
        parametros[0] = requisito.getNome();

        conexao.delete("PRE_REQUISITO", "DISCIPLINA_NOME_REQUISITO = ?", parametros);

    }

    public boolean eRequisito(Disciplina disciplina) {

        String sql = "SELECT * FROM PRE_REQUISITO\n" +
                "WHERE DISCIPLINA_NOME_REQUISITO = '" + disciplina.getNome() + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;
    }

    public List<Disciplina> buscaOndeERequisito(String nomeDisciplina) {

        List<Disciplina> disciplinaRequisito = new ArrayList<>();

        String sql = "SELECT * FROM PRE_REQUISITO\n" +
                "WHERE DISCIPLINA_NOME_REQUISITO = '" + nomeDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Disciplina disciplina;
                disciplina = buscaDisciplinaPorNome(resultado.getString(resultado.
                        getColumnIndexOrThrow("DISCIPLINA_NOME")));
                disciplinaRequisito.add(disciplina);

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplinaRequisito;

    }

    public boolean temCadastro(String nomeDisciplina){

        String sql = "SELECT * FROM DISCIPLINA\n" +
                "WHERE NOME = '" + nomeDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;

    }
}
