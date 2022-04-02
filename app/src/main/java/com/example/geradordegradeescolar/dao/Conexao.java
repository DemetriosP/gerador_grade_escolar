package com.example.geradordegradeescolar.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class Conexao {

    private Conexao(Context context){

        try {
            DaoOpenHelper dao = new DaoOpenHelper(context);
            SQLiteDatabase conexao = dao.getWritableDatabase();
            Toast.makeText(context, "Conexão criada com sucesso", Toast.LENGTH_SHORT).show();

        } catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }
    }
}
