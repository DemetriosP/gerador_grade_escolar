package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;

public class FormCasdastro extends AppCompatActivity {

    private TextView entrar;
    private EditText senha, confirmarSenha, nome, email;
    private Button criarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_casdastro);
        iniciarComponentes();
        vaiTelaLogin();

    }

    private void vaiTelaLogin() {
        entrar.setOnClickListener(v -> {
            Intent intent = new Intent(FormCasdastro.this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }

    private void iniciarComponentes() {
        entrar = findViewById(R.id.textEntrar);
        senha = findViewById(R.id.editSenha);
        criarConta = findViewById(R.id.btCadastrese);
        confirmarSenha = findViewById(R.id.editConfirmarSenha);
        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
    }

    private boolean comprarSenhas(EditText senha, EditText confirmarSenha) {
        return senha.equals(confirmarSenha);
    }

}