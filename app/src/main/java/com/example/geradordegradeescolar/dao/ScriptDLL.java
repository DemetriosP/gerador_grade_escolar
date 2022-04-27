package com.example.geradordegradeescolar.dao;

public class ScriptDLL {

    public static String getCreateTableUsuario(){

        return "CREATE TABLE IF NOT EXISTS USUARIO( " +
                "   EMAIL VARCHAR (250) NOT NULL PRIMARY KEY, " +
                "   NOME VARCHAR (250) NOT NULL, " +
                "   SENHA VARCHAR (250) NOT NULL ) ";
    }

    public static String getCreateTableDisciplina(){

        return "CREATE TABLE IF NOT EXISTS DISCIPLINA( " +
                "ID INTEGER PRIMARY KEY, " +
                "NOME VARCHAR (250) NOT NULL UNIQUE, " +
                "SITUACAO VARCHAR (250) NOT NULL, " +
                "DIA_SEMANA VARCHAR (250), " +
                "PERIODO VARCHAR (250) )";

    }

    public static String getCreateTableReferencia(){

        return "CREATE TABLE IF NOT EXISTS PRE_REQUISITO( " +
                "ID_DISCIPLINA INTEGER NOT NULL, " +
                "ID_DISCIPLINA_REQUISITO INTEGER NOT NULL, " +
                "FOREIGN KEY (ID_DISCIPLINA) REFERENCES DISCIPLINA(ID), " +
                "FOREIGN KEY (ID_DISCIPLINA_REQUISITO) REFERENCES DISCIPLINA(ID), " +
                "PRIMARY KEY (ID_DISCIPLINA, ID_DISCIPLINA_REQUISITO) )";

    }



}
