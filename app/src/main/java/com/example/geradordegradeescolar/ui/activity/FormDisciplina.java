package com.example.geradordegradeescolar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.example.geradordegradeescolar.dao.Conexao;
import com.example.geradordegradeescolar.dao.DaoOpenHelper;
import com.example.geradordegradeescolar.dao.DisciplinaDAO;
import com.example.geradordegradeescolar.model.Disciplina;
import com.google.android.material.textfield.TextInputLayout;

public class FormDisciplina extends AppCompatActivity {

    private AutoCompleteTextView autoSituacao, autoDia;
    private EditText etHoraIni, etHoraFim, etNome;
    private String[] situacoes, dias;
    private String horaIni, horaFim, nome, situacao, dia;
    private TextInputLayout diaLayout, horaIniLayout, horaFinLayout;
    private Button btSalvar;
    private Disciplina disciplina;
    private DisciplinaDAO disciplinaDao;

    private SQLiteDatabase conexao;
    private DaoOpenHelper daoOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_disciplina);
        conexao();
        //Conexao conexao = new Conexao(this);
        //disciplinaDao = new DisciplinaDAO(conexao.getConexao());
        iniciarComponentes();
        preencheDrompDownMenu(situacoes, autoSituacao);
        preencheDrompDownMenu(dias, autoDia);
        configuraOnClinkDrop();
        cadastrarDisciplina();
        carregaDisciplina();


        //DisciplinaDAO disciplinaDao = new DisciplinaDAO(conexao);

    }



    public void conexao(){

        try {
            this.daoOpenHelper = new DaoOpenHelper(this);
            this.conexao = daoOpenHelper.getWritableDatabase();
            Toast.makeText(this, "Conexão criada com sucesso", Toast.LENGTH_SHORT).show();

            disciplinaDao = new DisciplinaDAO(conexao);

        } catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();

        }
    }



    private void cadastrarDisciplina() {

        Intent dados = getIntent();

        btSalvar.setOnClickListener(v -> {

            converteComponenteString();

            if (nome == null || nome.isEmpty() || situacao == null || situacao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else if (situacao.equals("Concluído")) {
                if(dados.hasExtra("disciplina")){
                    disciplina.setNome(nome);
                    disciplina.setSituacao(situacao);
                    disciplina.setDiaSemana(null);
                    disciplina.setHorarioIn(null);
                    disciplina.setHorarioFn(null);
                    disciplinaDao.alterar(disciplina);
                }else {
                    disciplina = new Disciplina(nome, situacao);
                    disciplinaDao.inserir(disciplina);
                    Toast.makeText(this, "DADOS SALVOS", Toast.LENGTH_LONG).show();
                }
                finish();
            } else if (situacao.equals("Pendente")) {
                if (horaIni.isEmpty() || horaFim.isEmpty() || dia == null || dia.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(dados.hasExtra("disciplina")){
                        disciplina.setNome(nome);
                        disciplina.setSituacao(situacao);
                        disciplina.setDiaSemana(dia);
                        disciplina.setHorarioIn(horaIni);
                        disciplina.setHorarioFn(horaFim);
                        disciplinaDao.alterar(disciplina);
                    }else {
                        disciplina = new Disciplina(nome, situacao, dia, horaIni, horaFim);
                        disciplinaDao.inserir(disciplina);
                        Toast.makeText(this, "DADOS SALVOS", Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        });
    }

    private void configuraOnClinkDrop() {
        autoSituacao.setOnItemClickListener((parent, view, position, id) -> {
            String x = (String) parent.getItemAtPosition(position);
            if (x.equals(situacoes[1])) {
                alteraVisbilidadeCampos(View.VISIBLE);
            } else {
                alteraVisbilidadeCampos(View.INVISIBLE);
            }
        });
    }

    private void alteraVisbilidadeCampos(int visible) {
        diaLayout.setVisibility(visible);
        horaIniLayout.setVisibility(visible);
        horaFinLayout.setVisibility(visible);
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

        if (disciplina.getSituacao().equals(situacoes[1])) {
            alteraVisbilidadeCampos(View.VISIBLE);
            autoDia.setText(disciplina.getDiaSemana(), false);
            etHoraIni.setText(String.valueOf(disciplina.getHorarioIn()));
            etHoraFim.setText(String.valueOf(disciplina.getHorarioFn()));
        }

    }

    private void iniciarComponentes() {
        autoSituacao = findViewById(R.id.autoCompleteSituacao);
        situacoes = getResources().getStringArray(R.array.situacao);
        autoDia = findViewById(R.id.autoCompleteDia);
        dias = getResources().getStringArray(R.array.dia);
        diaLayout = findViewById(R.id.editDiaLayoyt);
        etHoraIni = findViewById(R.id.editHoraIn);
        horaIniLayout = findViewById(R.id.editHInitLayoyt);
        etHoraFim = findViewById(R.id.editHoraFim);
        horaFinLayout = findViewById(R.id.editHFinLayoyt);
        btSalvar = findViewById(R.id.btSalvar);
        etNome = findViewById(R.id.editNome);
    }

    private void converteComponenteString() {
        horaIni = etHoraIni.getText().toString();
        horaFim = etHoraFim.getText().toString();
        nome = etNome.getText().toString();
        dia = autoDia.getText().toString();
        situacao = autoSituacao.getText().toString();
    }

}