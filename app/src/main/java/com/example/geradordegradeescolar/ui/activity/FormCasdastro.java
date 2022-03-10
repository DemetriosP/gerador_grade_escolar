package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;

public class FormCasdastro extends AppCompatActivity {

    private TextView entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_casdastro);
        iniciarComponentes();

        entrar.setOnClickListener(v -> {
            Intent intent = new Intent(FormCasdastro.this, FormLogin.class);
            startActivity(intent);
            finish();
        });

    }

    private void iniciarComponentes(){
        entrar = findViewById(R.id.textEntrar);
    }
}