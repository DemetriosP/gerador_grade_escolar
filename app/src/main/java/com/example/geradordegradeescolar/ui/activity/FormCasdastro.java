package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class FormCasdastro extends AppCompatActivity {

    private TextView tvEntrar;
    private EditText etSenha, etConfirmarSenha, etNome, etEmail;
    private Button btCriarConta;
    private String nome, email, senha, confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_form_casdastro);
        iniciarComponentes();
        CadastrarUsuario();
        vaiTelaLogin();
    }

    private void CadastrarUsuario() {

        btCriarConta.setOnClickListener(v -> {

            converteComponentesString();

        });
    }

    private void mensagem(View v, String mensagem) {
        Snackbar snackbar = Snackbar.make(v, mensagem, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private boolean verificaCampos() {
        return !nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !confirmarSenha.isEmpty();
    }

    private void salvarDadosUsuario() {

    }

    private void vaiTelaLogin() {
        tvEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(FormCasdastro.this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }

    private void iniciarComponentes() {
        tvEntrar = findViewById(R.id.textEntrar);
        etSenha = findViewById(R.id.editSenha);
        btCriarConta = findViewById(R.id.btEntrar);
        etConfirmarSenha = findViewById(R.id.editConfirmarSenha);
        etNome = findViewById(R.id.editNome);
        etEmail = findViewById(R.id.editEmail);
    }

    private boolean comprarSenhas() {
        return senha.equals(confirmarSenha);
    }

    private void converteComponentesString() {
        senha = etSenha.getText().toString();
        email = etEmail.getText().toString();
        confirmarSenha = etConfirmarSenha.getText().toString();
        nome = etNome.getText().toString();
    }


}