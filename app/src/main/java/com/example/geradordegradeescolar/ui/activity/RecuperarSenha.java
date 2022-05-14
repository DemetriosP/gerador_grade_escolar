package com.example.geradordegradeescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geradordegradeescolar.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RecuperarSenha extends AppCompatActivity {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private EditText etEmailRec;
    private String emailRec;
    private Button btEnviar;
    private TextView cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        Objects.requireNonNull(getSupportActionBar()).hide();

        etEmailRec = findViewById(R.id.editEmailRec);
        btEnviar = findViewById(R.id.btEnviar);
        cancelar = findViewById(R.id.textCancelar);

        recuperarSenha();
        voltaTelaLogin();
    }

    private void recuperarSenha() {

        btEnviar.setOnClickListener(v -> {

            emailRec = etEmailRec.getText().toString();

            if (emailRec.isEmpty()) {
                Toast.makeText(RecuperarSenha.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            } else {
                auth.sendPasswordResetEmail(emailRec).addOnSuccessListener(unused -> {
                    Toast.makeText(RecuperarSenha.this, "Mensagem enviado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecuperarSenha.this, FormLogin.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e ->
                        Toast.makeText(RecuperarSenha.this, "Não foi possível enviar a mensagem", Toast.LENGTH_SHORT).show());
            }
        });


    }

    private void voltaTelaLogin() {
        cancelar.setOnClickListener(v -> {
            Intent intent = new Intent(RecuperarSenha.this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }
}