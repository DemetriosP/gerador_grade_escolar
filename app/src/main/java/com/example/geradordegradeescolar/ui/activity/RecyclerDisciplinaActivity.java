package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecyclerDisciplinaActivity extends AppCompatActivity {

    private RecyclerDisciplinaView recyclerDisciplinaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_disciplina);
        setTitle("Disciplinas" );
        recyclerDisciplinaView = new RecyclerDisciplinaView(this);
        configurarFabNovaDisciplina();
        configuraRecycler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerDisciplinaView.atualizaDisciplinas();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 1:
                Intent intent = new Intent(RecyclerDisciplinaActivity.this, RecyclerRequisitoActivity.class);
                intent.putExtra("disciplina", recyclerDisciplinaView.buscaDisciplina(item.getGroupId()));
                startActivity(intent);
                return true;
            case 2:
                intent = new Intent(RecyclerDisciplinaActivity.this, FormDisciplina.class);
                intent.putExtra("disciplina", recyclerDisciplinaView.buscaDisciplina(item.getGroupId()));
                startActivity(intent);
                return true;
            case 3:
                recyclerDisciplinaView.confirmaRemocao(item);
            return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void configuraRecycler() {
        RecyclerView disciplinaRecycler = findViewById(R.id.recyclerDisciplina);
        recyclerDisciplinaView.configuraAdapter(disciplinaRecycler);
    }

    private void configurarFabNovaDisciplina() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.novaDisciplina);
        abrirFormularioCadastrarDisciplina(botaoNovoAluno);
    }

    private void abrirFormularioCadastrarDisciplina(FloatingActionButton botaoNovaDisciplina) {
        botaoNovaDisciplina.setOnClickListener(view -> startActivity(new
                Intent(RecyclerDisciplinaActivity.this, FormDisciplina.class)));
    }

}