package com.example.geradordegradeescolar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DaoOpenHelper extends SQLiteOpenHelper {


    public DaoOpenHelper(@Nullable Context context) {
        super(context, "TESTE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDLL.getCreateTableDisciplina());
        db.execSQL(ScriptDLL.getCreateTableUsuario());
        db.execSQL(ScriptDLL.getCreateTableReferencia());
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreing_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
