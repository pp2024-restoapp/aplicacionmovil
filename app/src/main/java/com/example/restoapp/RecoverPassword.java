package com.example.restoapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

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


        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(RecoverPassword.this, "Se ha enviado un correo electrónico de restablecimiento de contraseña", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        if (e instanceof FirebaseAuthInvalidUserException) {

                            Toast.makeText(RecoverPassword.this, "El correo electrónico no está registrado en nuestra base de datos", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(RecoverPassword.this, "Error al enviar el correo electrónico de restablecimiento de contraseña: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RecoverPassword.this, "Se ha enviado un correo electrónico de restablecimiento de contraseña", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(RecoverPassword.this, "Error al enviar el correo electrónico de restablecimiento de contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
