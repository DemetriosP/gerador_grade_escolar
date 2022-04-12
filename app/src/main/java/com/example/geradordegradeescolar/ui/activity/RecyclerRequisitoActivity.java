package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecyclerRequisitoActivity extends AppCompatActivity {

    private RecyclerRequisitoView recyclerRequisitoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_requisito);
        setTitle("Requisitos");

        Intent dados = getIntent();
        Disciplina disciplina = (Disciplina) dados.getSerializableExtra("disciplina");
        List<Disciplina> requisitos = disciplina.getRequisitos();

        recyclerRequisitoView = new RecyclerRequisitoView(this, requisitos);
        configuraFabAcrescentaRequisito();
        configuraRecycler();
    }
    /*

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            recyclerRequisitoView.confirmaRemocao(item);
            return true;
        }
        return super.onContextItemSelected(item);
    }

     */

    private void configuraRecycler() {
        RecyclerView disciplinaRecycler = findViewById(R.id.recyclerRequisito);
        recyclerRequisitoView.configuraAdapter(disciplinaRecycler);
    }

    private void configuraFabAcrescentaRequisito() {
        FloatingActionButton botaoAcrescentaRequisito = findViewById(R.id.acresentarRequisito);
        abreAcrescentarDisciplina(botaoAcrescentaRequisito);
    }

    private void abreAcrescentarDisciplina(FloatingActionButton botaoAcrescentaRequisito) {
        botaoAcrescentaRequisito.setOnClickListener(v -> startActivity(new Intent(
                RecyclerRequisitoActivity.this, AcrescentaRequisito.class)));
    }


}