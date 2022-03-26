package com.example.geradordegradeescolar.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;

import java.util.ArrayList;

public class ExibeDisciplina extends AppCompatActivity {

    RecyclerView disciplinasRecycler;
    ArrayList<Disciplina> disciplinas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_disciplina);
        iniciarComponentes();

        disciplinas.add(new Disciplina("Linguagem de Programação I", "Concluído"));
        disciplinas.add(new Disciplina("Linguagem de Programação II", "Pendente", "Quarta", 1700, 2000));

        RecyclerAdapter adapter = new RecyclerAdapter(this, disciplinas);
        disciplinasRecycler.setAdapter(adapter);
        disciplinasRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void iniciarComponentes(){
        disciplinasRecycler = findViewById(R.id.disciplina_recycler);
    }
}