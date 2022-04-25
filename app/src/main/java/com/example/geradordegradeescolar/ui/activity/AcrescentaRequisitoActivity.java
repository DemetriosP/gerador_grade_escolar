package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.ui.view.AcrescentaRequisitoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AcrescentaRequisitoActivity extends AppCompatActivity {

    private AcrescentaRequisitoView acrescentaRequisitoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acrescenta_requisito);
        setTitle("Acrescentar Requisitos");

        Intent dados = getIntent();
        Disciplina disciplina = (Disciplina) dados.getSerializableExtra("disciplina");
        List<Disciplina> requisitos = new ArrayList<>();
        requisitos.add(disciplina);

        requisitos.addAll(disciplina.getRequisitos());

        acrescentaRequisitoView = new AcrescentaRequisitoView(this, requisitos);
        configuraRecycler();
        configuraFabConfirmaRequisito(disciplina);
    }

    private void configuraRecycler() {
        RecyclerView disciplinaRecycler = findViewById(R.id.acresentarRequisito);
        acrescentaRequisitoView.configuraAdapter(disciplinaRecycler);
    }

    private void configuraFabConfirmaRequisito(Disciplina disciplina) {
        FloatingActionButton botaoConfirmaRequisito = findViewById(R.id.confirmaRequisito);
        botaoConfirmaRequisito.setOnClickListener(v -> {
            acrescentaRequisitoView.acresentaRequisitos(disciplina);
            finish();
        });

    }

}