package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FormLogin extends AppCompatActivity {

    private TextView cadastrese, esqueceuSenha;
    private EditText etEmail, etSenha;
    private Button btEntrar;
    private String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_form_login);
        iniciarComponentes();
        vaiTelaCadastrese();
        vaiTelaRecuperarSenha();
        entrar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null){
            vaiTelaDisciplina();
        }
    }

    private void entrar() {

        btEntrar.setOnClickListener(v -> {

            converteComponentesString();

            if(email.isEmpty() || senha.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            }else {
                AutenticarUsuario(v);
            }
        });
    }

    private void AutenticarUsuario(View v) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        //progressBar.setVisibility(v.VISIBLE);
                        new Handler().postDelayed(this::vaiTelaDisciplina, 1000);
                    }else{
                        try {
                            throw Objects.requireNonNull(task.getException());
                        }catch (Exception e){
                            Toast.makeText(this, "Login efetuado com sucesso",
                                    Toast.LENGTH_LONG).show();
                            vaiTelaDisciplina();
                        }
                    }
                });

    }

    private void vaiTelaCadastrese() {
        cadastrese.setOnClickListener(v -> {
            Intent intent = new Intent(FormLogin.this, FormCasdastro.class);
            startActivity(intent);
            finish();
        });
    }

    private void vaiTelaDisciplina() {
        Intent intent = new Intent(FormLogin.this, RecyclerDisciplinaActivity.class);
        startActivity(intent);
        finish();
    }

    private void vaiTelaRecuperarSenha(){
        esqueceuSenha.setOnClickListener(v -> {
            Intent intent = new Intent(FormLogin.this, RecuperarSenha.class);
            startActivity(intent);
            finish();
        });

    }

    private void iniciarComponentes(){
        cadastrese = findViewById(R.id.textCadastrese);
        etEmail = findViewById(R.id.editEmail);
        etSenha = findViewById(R.id.editSenha);
        btEntrar = findViewById(R.id.btEntrar);
        esqueceuSenha = findViewById(R.id.textRecpSenha);
    }

    private void converteComponentesString(){
        senha = etSenha.getText().toString();
        email = etEmail.getText().toString();
    }

}