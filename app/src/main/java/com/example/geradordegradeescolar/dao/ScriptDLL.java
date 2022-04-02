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
                "   NOME VARCHAR (250) NOT NULL PRIMARY KEY, " +
                "   DIA_SEMANA VARCHAR (250) NOT NULL, " +
                "   HORARIO_IN VARCHAR (250) NOT NULL, " +
                "   HORARIO_FN VARCHAR (250) NOT NULL, ";

    }

    public static String getCreateTableReferencia(){

        return "CREATE TABLE IF NOT EXISTS PRE_REQUISITO( " +
                "DISCIPLINA_NOME VARCHAR(250) NOT NULL, " +
                "DISCIPLINA_ID INTEGER NOT NULL, " +
                "FOREIGN KEY (DISCIPLINA_NOME) REFERENCES DISCIPLINA(NOME), " +
                "FOREIGN KEY (DISCIPLINA_ID) REFERENCES DISCIPLINA(ROWID), " +
                "PRIMARY KEY (DISCIPLINA_NOME, DISCIPLINA_ID) )";

    }


}
