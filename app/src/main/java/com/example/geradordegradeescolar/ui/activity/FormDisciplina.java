package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.google.android.material.textfield.TextInputLayout;

public class FormDisciplina extends AppCompatActivity {

    private AutoCompleteTextView autoSituacao, autoDia, autoPeriodo;
    private EditText etNome;
    private String[] situacoes, dias, periodos;
    private String nome, situacao, dia, periodo;
    private TextInputLayout diaLayout, periodoLayout;
    private Button btSalvar;
    private Disciplina disciplina;
    private DisciplinaDAO disciplinaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_disciplina);
        Conexao conexao = new Conexao(this);
        disciplinaDao = new DisciplinaDAO(conexao.getConexao());
        iniciarComponentes();
        preencheDrompDownMenu(situacoes, autoSituacao);
        preencheDrompDownMenu(dias, autoDia);
        preencheDrompDownMenu(periodos, autoPeriodo);
        configuraOnClinkDrop();
        cadastrarDisciplina();
        carregaDisciplina();
    }

    private void cadastrarDisciplina() {

        Intent dados = getIntent();

        btSalvar.setOnClickListener(v -> {

            converteComponenteString();

            if (nome == null || nome.isEmpty() || situacao == null || situacao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else if (situacao.equals("Cursado") || situacao.equals("À Cursar")) {
                if (dados.hasExtra("disciplina")) {
                    disciplina.setNome(nome);
                    disciplina.setSituacao(situacao);
                    disciplina.setDiaSemana(null);
                    disciplina.setPeriodo(null);
                    disciplinaDao.alterar(disciplina);
                    finish();
                } else {
                    if (disciplinaDao.temCadastro(nome)) {
                        Toast.makeText(this, "Disciplina já foi cadastrada", Toast.LENGTH_SHORT).show();
                    } else {
                        disciplina = new Disciplina(nome, situacao);
                        disciplinaDao.inserir(disciplina);
                        finish();
                    }
                }

            } else if (situacao.equals("Em Curso")) {
                if (periodo.isEmpty() || dia == null || dia.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (dados.hasExtra("disciplina")) {
                        disciplina.setNome(nome);
                        disciplina.setSituacao(situacao);
                        disciplina.setDiaSemana(dia);
                        disciplina.setPeriodo(periodo);
                        disciplinaDao.alterar(disciplina);
                        finish();
                    } else {
                        if (disciplinaDao.temCadastro(nome)) {
                            Toast.makeText(this, "Disciplina já foi cadastrada", Toast.LENGTH_SHORT).show();
                        } else {
                            disciplina = new Disciplina(nome, situacao, dia, periodo);
                            disciplinaDao.inserir(disciplina);
                            finish();
                        }
                    }
                }
            }
        });
    }

    private void configuraOnClinkDrop() {
        autoSituacao.setOnItemClickListener((parent, view, position, id) -> {
            String x = (String) parent.getItemAtPosition(position);
            if (x.equals(situacoes[2])) {
                alteraVisbilidadeCampos(View.VISIBLE);
            } else {
                alteraVisbilidadeCampos(View.GONE);
            }
        });
    }

    private void alteraVisbilidadeCampos(int visible) {
        diaLayout.setVisibility(visible);
        periodoLayout.setVisibility(visible);
    }

    private void preencheDrompDownMenu(String[] valores, AutoCompleteTextView menu) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valores);

        menu.setAdapter(adapter);
        menu.setThreshold(1);
    }

    private void carregaDisciplina() {
        Intent dados = getIntent();
        if (dados.hasExtra("disciplina")) {
            setTitle("Editar Disciplina");
            disciplina = (Disciplina) dados.getSerializableExtra("disciplina");
            preencheCampos();
        } else {
            setTitle("Cadastrar Disciplina");
            disciplina = new Disciplina();
        }
    }

    private void preencheCampos() {
        etNome.setText(disciplina.getNome());
        autoSituacao.setText(disciplina.getSituacao(), false);

        if (disciplina.getSituacao().equals(situacoes[2])) {
            alteraVisbilidadeCampos(View.VISIBLE);
            autoDia.setText(disciplina.getDiaSemana(), false);
            autoPeriodo.setText(String.valueOf(disciplina.getPeriodo()));
        }

    }

    private void iniciarComponentes() {
        autoSituacao = findViewById(R.id.autoCompleteSituacao);
        situacoes = getResources().getStringArray(R.array.situacao);
        autoDia = findViewById(R.id.autoCompleteDia);
        dias = getResources().getStringArray(R.array.dia);
        diaLayout = findViewById(R.id.editDiaLayoyt);
        periodos = getResources().getStringArray(R.array.periodo);
        autoPeriodo = findViewById(R.id.autoCompletePeriodo);
        periodoLayout = findViewById(R.id.editPeriodoLayoyt);
        btSalvar = findViewById(R.id.btSalvar);
        etNome = findViewById(R.id.editNome);
    }

    private void converteComponenteString() {
        periodo = autoPeriodo.getText().toString();
        nome = etNome.getText().toString();
        dia = autoDia.getText().toString();
        situacao = autoSituacao.getText().toString();
    }

}