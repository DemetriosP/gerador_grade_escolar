package com.example.geradordegradeescolar.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormDisciplina extends AppCompatActivity {

    private AutoCompleteTextView autoSituacao, autoDia;
    private EditText etHoraIni, etHoraFim, etNome;
    private String[] situacoes, dias;
    private String horaIni, horaFim, nome, situacao, dia;
    private TextInputLayout diaLayout, horaIniLayout, horaFinLayout;
    private Button btSalvar;
    final private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cadastrar Disciplina");
        setContentView(R.layout.activity_form_disciplina);
        iniciarComponentes();
        preencheDrompDownMenu(situacoes, autoSituacao);
        preencheDrompDownMenu(dias, autoDia);
        configuraOnClinkDrop();
        cadastrarDisciplina();
    }

    private void cadastrarDisciplina() {

        btSalvar.setOnClickListener(v -> {

            converteComponenteString();

            if (nome == null || nome.isEmpty() || situacao == null || situacao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else if (situacao.equals("Concluído")) {
                Toast.makeText(this, "Dados Salvos", Toast.LENGTH_LONG).show();
                cadastrarDisciplinaCompleta();
            } else if (situacao.equals("Pendente")) {
                if (horaIni.isEmpty() || horaFim.isEmpty() || dia == null || dia.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Dados Salvos", Toast.LENGTH_LONG).show();
                    cadastrarDisciplinaPendente();
                }
            }
        });
    }

    private void cadastrarDisciplinaPendente() {

        Map<String, Object> disciplinas = new HashMap<>();
        disciplinas.put("nome", nome);
        disciplinas.put("situacao", situacao);
        disciplinas.put("horaIni", horaIni);
        disciplinas.put("horaFim", horaFim);

        String usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference referencia = db.collection("Disciplinas").document(usuarioID);

        referencia.set(disciplinas).addOnSuccessListener(aVoid ->
                Log.d("db", "Disciplina Salva")).addOnFailureListener
                (e -> Log.d("db_error", "Erro ao salvar os dados" + e));
    }

    private void cadastrarDisciplinaCompleta() {

        Map<String, Object> disciplinas = new HashMap<>();
        disciplinas.put("nome", nome);
        disciplinas.put("situacao", situacao);

        String usuarioID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference referencia = db.collection("Disciplinas").document(usuarioID);

        referencia.set(disciplinas).addOnSuccessListener(aVoid ->
                Log.d("db", "Disciplina Salva")).addOnFailureListener
                (e -> Log.d("db_error", "Erro ao salvar os dados" + e));

    }

    private void configuraOnClinkDrop() {
        autoSituacao.setOnItemClickListener((parent, view, position, id) -> {
            String x = (String) parent.getItemAtPosition(position);
            if (x.equals(situacoes[1])) {
                diaLayout.setVisibility(View.VISIBLE);
                horaIniLayout.setVisibility(View.VISIBLE);
                horaFinLayout.setVisibility(View.VISIBLE);
            } else {
                diaLayout.setVisibility(View.INVISIBLE);
                horaIniLayout.setVisibility(View.INVISIBLE);
                horaFinLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void preencheDrompDownMenu(String[] valores, AutoCompleteTextView menu) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valores);

        menu.setAdapter(adapter);
        menu.setThreshold(1);
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