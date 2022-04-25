package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.ui.view.RecyclerDisciplinaView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class RecyclerDisciplinaActivity extends AppCompatActivity {

    private RecyclerDisciplinaView recyclerDisciplinaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_disciplina);
        setTitle("Disciplinas");
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

        switch (item.getItemId()) {
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
                if (recyclerDisciplinaView.eRequisito(recyclerDisciplinaView.buscaDisciplina(item.getGroupId()))) {
                    recyclerDisciplinaView.alertaNaoPodeRemover();
                } else recyclerDisciplinaView.confirmaRemocao(item);


                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MenuItem gradeItem = menu.findItem(R.id.gerarGrade);
        MenuItem sairItem = menu.findItem(R.id.sair);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerDisciplinaView.getAdapter().getFilter().filter(newText);
                return false;
            }
        });

        gradeItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(RecyclerDisciplinaActivity.this, RecyclerGradeActivity.class);
            startActivity(intent);
            return false;
        });

        sairItem.setOnMenuItemClickListener(item -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(RecyclerDisciplinaActivity.this, FormLogin.class);
            startActivity(intent);
            finish();
            return false;
        });

        return true;
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