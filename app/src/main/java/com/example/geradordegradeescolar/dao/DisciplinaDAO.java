package com.example.geradordegradeescolar.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;
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
        contentValues.put("NOME", disciplina.getNome());
        contentValues.put("SITUACAO", disciplina.getSituacao());
        contentValues.put("DIA_SEMANA", disciplina.getDiaSemana());
        contentValues.put("HORARIO_IN", disciplina.getHorarioIn());
        contentValues.put("HORARIO_FN", disciplina.getHorarioFn());

        conexao.update("DISCIPLINA", contentValues, "ID = ?", new String[]{String.valueOf(disciplina.getId())});

    }

    public void excluiDisciplina(Disciplina disciplina) {

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(disciplina.getId());

        if (temRequisito(disciplina.getId())) conexao.delete("PRE_REQUISITO",
                "ID_DISCIPLINA = ?", parametros);

        conexao.delete("DISCIPLINA", "ID = ?", parametros);
    }

    public List<Disciplina> buscaTodos() {

        List<Disciplina> disciplinas = new ArrayList<>();

        String sql = "SELECT * FROM DISCIPLINA";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Disciplina disciplina = new Disciplina();
                disciplina.setId(resultado.getInt(resultado.getColumnIndexOrThrow("ID")));
                disciplina.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                disciplina.setSituacao(resultado.getString(resultado.getColumnIndexOrThrow("SITUACAO")));
                disciplina.setDiaSemana(resultado.getString(resultado.getColumnIndexOrThrow("DIA_SEMANA")));
                disciplina.setHorarioIn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_IN")));
                disciplina.setHorarioFn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_FN")));

                if (temRequisito(resultado.getInt(resultado.getColumnIndexOrThrow("ID")))) {
                    disciplina.setRequisitos(buscaRequisitoDisciplina(resultado.getInt(resultado.getColumnIndexOrThrow("ID"))));
                } else disciplina.setRequisitos(new ArrayList<>());

                disciplinas.add(disciplina);

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplinas;
    }

    public List<Disciplina> buscaRequisitoDisciplina(int idDisciplina) {

        List<Disciplina> requisitos = new ArrayList<>();

        String sql = "SELECT ID_DISCIPLINA_REQUISITO FROM DISCIPLINA\n" +
                "INNER JOIN PRE_REQUISITO ON DISCIPLINA.ID = PRE_REQUISITO.ID_DISCIPLINA\n" +
                "WHERE ID_DISCIPLINA = '" + idDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            Disciplina disciplina;

            do {
                int id = resultado.getInt(resultado.getColumnIndexOrThrow("ID_DISCIPLINA_REQUISITO"));
                disciplina = buscaDisciplinaPorId(id);
                requisitos.add(disciplina);
            } while (resultado.moveToNext());

        }

        resultado.close();
        return requisitos;
    }

    public Disciplina buscaDisciplinaPorId(int idDisciplina) {

        Disciplina disciplina = new Disciplina();

        String sql = "SELECT * FROM DISCIPLINA WHERE ID = '" + idDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                disciplina.setId(resultado.getInt(resultado.getColumnIndexOrThrow("ID")));
                disciplina.setNome(resultado.getString(resultado.getColumnIndexOrThrow("NOME")));
                disciplina.setSituacao(resultado.getString(resultado.getColumnIndexOrThrow("SITUACAO")));
                disciplina.setDiaSemana(resultado.getString(resultado.getColumnIndexOrThrow("DIA_SEMANA")));
                disciplina.setHorarioIn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_IN")));
                disciplina.setHorarioFn(resultado.getString(resultado.getColumnIndexOrThrow("HORARIO_FN")));

                if (temRequisito(resultado.getInt(resultado.getColumnIndexOrThrow("ID")))) {
                    disciplina.setRequisitos(buscaRequisitoDisciplina(resultado.getInt(resultado.getColumnIndexOrThrow("ID"))));
                } else disciplina.setRequisitos(new ArrayList<>());

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplina;

    }

    public boolean temRequisito(int idDisciplina) {

        String sql = "SELECT ID_DISCIPLINA_REQUISITO\n" +
                "FROM DISCIPLINA\n" +
                "INNER JOIN PRE_REQUISITO ON DISCIPLINA.ID = PRE_REQUISITO.ID_DISCIPLINA\n" +
                "WHERE ID = '" + idDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;

    }

    public void insereRequisitos(Disciplina disciplina, List<Disciplina> requisitos) {

        ContentValues contentValues = new ContentValues();

        for (Disciplina requisito : requisitos) {
            contentValues.put("ID_DISCIPLINA", disciplina.getId());
            contentValues.put("ID_DISCIPLINA_REQUISITO", requisito.getId());
            conexao.insertOrThrow("PRE_REQUISITO", null, contentValues);
        }

    }

    public void excluirRequisito(Disciplina requisito) {

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(requisito.getId());

        conexao.delete("PRE_REQUISITO", "ID_DISCIPLINA_REQUISITO = ?", parametros);

    }

    public boolean eRequisito(Disciplina disciplina) {

        String sql = "SELECT * FROM PRE_REQUISITO\n" +
                "WHERE ID_DISCIPLINA_REQUISITO = '" + disciplina.getId() + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;
    }

    public List<Disciplina> buscaOndeERequisito(int idDisciplina) {

        List<Disciplina> disciplinaRequisito = new ArrayList<>();

        String sql = "SELECT * FROM PRE_REQUISITO\n" +
                "WHERE ID_DISCIPLINA_REQUISITO = '" + idDisciplina + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        if (resultado.getCount() > 0) {

            resultado.moveToFirst();

            do {

                Disciplina disciplina;
                disciplina = buscaDisciplinaPorId(resultado.getInt(resultado.
                        getColumnIndexOrThrow("ID_DISCIPLINA")));
                disciplinaRequisito.add(disciplina);

            } while (resultado.moveToNext());

        }

        resultado.close();
        return disciplinaRequisito;

    }

    public boolean temCadastro(String disciplinaNome){

        String sql = "SELECT * FROM DISCIPLINA\n" +
                "WHERE NOME = '" + disciplinaNome + "'";

        Cursor resultado = conexao.rawQuery(sql, null);

        return resultado.getCount() > 0;

    }
}
