package com.example.geradordegradeescolar.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.model.Disciplina;
import com.example.geradordegradeescolar.ui.view.RecyclerDisciplinaView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RecyclerDisciplinaActivity extends AppCompatActivity {

    private RecyclerDisciplinaView recyclerDisciplinaView;
    private ActivityResultLauncher<Intent> csvDisciplinaLaucher;
    private ActivityResultLauncher<Intent> csvPreRequisitoLaucher;
    private Animation fabOpen, fabClose, fromBottom, toBotton;
    FloatingActionButton botaoDisciplinaArquivo, botaoRequisitoArquivo, botaoGerarGrade, botaoPrincipal, botaoNovaDisciplina;
    boolean isOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_disciplina);
        setTitle("Disciplinas");

        botaoDisciplinaArquivo = findViewById(R.id.fabDisciplinaArquivo);
        botaoRequisitoArquivo = findViewById(R.id.fabRequisitoArquivo);
        botaoGerarGrade = findViewById(R.id.fabGerarGrade);
        botaoPrincipal = findViewById(R.id.abrirOpcoes);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBotton = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        botaoPrincipal.setOnClickListener(v -> habilitaOpcoes());

        botaoDisciplinaArquivo.setOnClickListener(v -> {
            Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
            fileintent.addCategory(Intent.CATEGORY_OPENABLE);
            fileintent.setType("text/*");
            habilitaOpcoes();
            try {
                csvDisciplinaLaucher.launch(fileintent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(RecyclerDisciplinaActivity.this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        });

        botaoRequisitoArquivo.setOnClickListener(v -> {
            Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
            fileintent.addCategory(Intent.CATEGORY_OPENABLE);
            fileintent.setType("text/*");
            habilitaOpcoes();
            try {
                csvPreRequisitoLaucher.launch(fileintent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(RecyclerDisciplinaActivity.this, "ERRO", Toast.LENGTH_SHORT).show();
            }
        });

        botaoGerarGrade.setOnClickListener(v -> {
            Intent intent = new Intent(RecyclerDisciplinaActivity.this, RecyclerGradeActivity.class);
            startActivity(intent);
            habilitaOpcoes();
        });

        recyclerDisciplinaView = new RecyclerDisciplinaView(this);
        configurarFabNovaDisciplina();
        configuraRecycler();

        csvDisciplinaLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();

                        assert data != null;
                        Uri uri = data.getData();

                        try {

                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            bufferedReader.readLine();
                            String line;

                            while ((line = bufferedReader.readLine()) != null) {

                                Log.e("line", line);
                                String[] str = line.split(";", 1);

                                Disciplina disciplina;

                                disciplina = new Disciplina(str[0], "À Cursar");

                                recyclerDisciplinaView.getDisciplinaDAO().inserir(disciplina);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

        csvPreRequisitoLaucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();

                        assert data != null;
                        Uri uri = data.getData();

                        try {

                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            bufferedReader.readLine();
                            String line;

                            Disciplina disciplina;
                            Disciplina requisito;

                            while ((line = bufferedReader.readLine()) != null) {

                                Log.e("line", line);
                                String[] str = line.split(";", 2);

                                disciplina = recyclerDisciplinaView.getDisciplinaDAO().buscaDisciplinaPorNome(str[0]);
                                requisito = recyclerDisciplinaView.getDisciplinaDAO().buscaDisciplinaPorNome(str[1]);
                                recyclerDisciplinaView.getDisciplinaDAO().insereRequisitos(disciplina, requisito);

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                });
    }

    private void habilitaOpcoes() {
        animacaoFab(isOpen);
        setVisibilidade(isOpen);
        isOpen = !isOpen;
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
        botaoNovaDisciplina = findViewById(R.id.fabNovaDisciplina);
        abrirFormularioCadastrarDisciplina(botaoNovaDisciplina);

    }

    private void animacaoFab(boolean isOpen){

        if(!isOpen){
            botaoPrincipal.startAnimation(fabOpen);
            botaoDisciplinaArquivo.startAnimation(fromBottom);
            botaoRequisitoArquivo.startAnimation(fromBottom);
            botaoNovaDisciplina.startAnimation(fromBottom);
            botaoGerarGrade.startAnimation(fromBottom);
        } else {
            botaoPrincipal.startAnimation(fabClose);
            botaoDisciplinaArquivo.startAnimation(toBotton);
            botaoRequisitoArquivo.startAnimation(toBotton);
            botaoNovaDisciplina.startAnimation(toBotton);
            botaoGerarGrade.startAnimation(toBotton);
        }
    }

    private void setVisibilidade(boolean isOpen){

        if(!isOpen){
            botaoDisciplinaArquivo.setVisibility(View.VISIBLE);
            botaoRequisitoArquivo.setVisibility(View.VISIBLE);
            botaoNovaDisciplina.setVisibility(View.VISIBLE);
            botaoGerarGrade.setVisibility(View.VISIBLE);
        }else{
            botaoDisciplinaArquivo.setVisibility(View.INVISIBLE);
            botaoRequisitoArquivo.setVisibility(View.INVISIBLE);
            botaoNovaDisciplina.setVisibility(View.INVISIBLE);
            botaoGerarGrade.setVisibility(View.INVISIBLE);
        }

    }

    private void abrirFormularioCadastrarDisciplina(FloatingActionButton botaoNovaDisciplina) {
        botaoNovaDisciplina.setOnClickListener(v -> {
            startActivity(new Intent(RecyclerDisciplinaActivity.this, FormDisciplina.class));
            habilitaOpcoes();
        });
    }

}