package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerDisciplinaActivity extends AppCompatActivity {

    private final RecyclerDisciplinaView recyclerDisciplinaView =
            new RecyclerDisciplinaView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_disciplina);
        setTitle("Disciplinas");

        DisciplinaDAO dao = new DisciplinaDAO();
        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        RecyclerView disciplinasRecycler = findViewById(R.id.disciplina_recycler);


        RecyclerDisciplinaAdapter adapter = new RecyclerDisciplinaAdapter(this, disciplinas);
        disciplinasRecycler.setAdapter(adapter);
        disciplinasRecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    /*

    @Override
    protected void onResume() {
        super.onResume();
        recyclerDisciplinaView.atualizaDisciplinas();
    }

     */

    private void configuraRecycler(){
        RecyclerView disciplinaRecycler = findViewById(R.id.disciplina_recycler);
        recyclerDisciplinaView.configuraAdapter(disciplinaRecycler);
    }

    private void configurarFabNovaDisciplina() {
        FloatingActionButton botaoNovoAluno =
                findViewById(R.id.novaDisciplina);
        abrirFormularioCadastrarDisciplina(botaoNovoAluno);
    }

    private void abrirFormularioCadastrarDisciplina(FloatingActionButton botaoNovoAluno) {
        botaoNovoAluno.setOnClickListener(view -> startActivity(new
                Intent(RecyclerDisciplinaActivity.this, FormDisciplina.class)));
    }

}