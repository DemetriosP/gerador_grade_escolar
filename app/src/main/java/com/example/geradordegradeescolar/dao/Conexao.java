package com.example.geradordegradeescolar.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Conexao {

    DaoOpenHelper dao;
    SQLiteDatabase conexao;

    public Conexao(Context context){

        try {
            this.dao = new DaoOpenHelper(context);
            this.conexao = dao.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso", Toast.LENGTH_SHORT).show();

        } catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }
    }

    public DaoOpenHelper getDao() {
        return dao;
    }

    public void setDao(DaoOpenHelper dao) {
        this.dao = dao;
    }

    public SQLiteDatabase getConexao() {
        return conexao;
    }

    public void setConexao(SQLiteDatabase conexao) {
        this.conexao = conexao;
    }
}
