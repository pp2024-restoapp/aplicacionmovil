package com.example.restoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecoverPassword extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        etEmail = findViewById(R.id.etEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRecoveryRequest();
            }
        });

        // Agregando la parte del TextView para regresar a la actividad LoginActivity
        TextView tvReturn = findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoverPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void submitRecoveryRequest() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor, introduce tu correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí puedes agregar el código para enviar la solicitud de recuperación de contraseña.
        // Ejemplo: enviar una solicitud a tu servidor o usar Firebase Authentication

        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Se han enviado las instrucciones a tu correo electrónico", Toast.LENGTH_LONG).show();
    }
}

