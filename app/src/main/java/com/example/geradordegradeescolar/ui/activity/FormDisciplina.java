package com.example.geradordegradeescolar.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.google.android.material.textfield.TextInputLayout;

public class FormDisciplina extends AppCompatActivity {

    private AutoCompleteTextView situacao, dia;
    private String[] situacoes, dias;
    private TextInputLayout diaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_disciplina);
        iniciarComponentes();
        preencheDrompDownMenu(situacoes, situacao);
        preencheDrompDownMenu(dias, dia);

        situacao.setOnItemClickListener((parent, view, position, id) -> {
            String diass = (String) parent.getItemAtPosition(position);
            if(diass.equals(situacoes[1])) diaLayout.setVisibility(View.VISIBLE);
            else diaLayout.setVisibility(View.INVISIBLE);
        });

    }

    private void preencheDrompDownMenu(String[] valores, AutoCompleteTextView menu) {
        ArrayAdapter<?> adapter = new ArrayAdapter<>(getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valores);

        menu.setAdapter(adapter);
        menu.setThreshold(1);
    }

    private void iniciarComponentes(){
        situacao = findViewById(R.id.autoCompleteSituacao);
        situacoes = getResources().getStringArray(R.array.situacao);
        dia = findViewById(R.id.autoCompleteDia);
        dias = getResources().getStringArray(R.array.dia);
        diaLayout = findViewById(R.id.editDiaLayoyt);
    }

}