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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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

            if (verificaCampos()) {
                if (comprarSenhas()) cadastrarUsuarioFirebase(v);
                else mensagem(v, "As senhas não correpondem");
            } else mensagem(v, "Preencha todos os campos!");
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

    private void cadastrarUsuarioFirebase(View v) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mensagem(v, "Usuário cadastrado");
                        vaiTelaRecyclerDisciplina();
                    } else {
                        String erro;
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erro = "Digite uma senha com no mínimo 6 caracteres";
                        } catch (FirebaseAuthUserCollisionException e) {
                            erro = "Esta conta já foi cadastrada";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erro = "E-mail inválido";
                        } catch (Exception e) {
                            erro = "Erro ao cadastrar usuário";
                        }
                        mensagem(v, erro);
                    }
                });
    }

    private void vaiTelaLogin() {
        tvEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(FormCasdastro.this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }

    private void vaiTelaRecyclerDisciplina() {
        Intent intent = new Intent(FormCasdastro.this, RecyclerDisciplinaActivity.class);
        startActivity(intent);
        finish();
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