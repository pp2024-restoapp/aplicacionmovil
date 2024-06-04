package com.example.restoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    private  Button btnRegistrar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.nombreEditText);
        etPassword = findViewById(R.id.contrasenaEditText);
        btnLogin = findViewById(R.id.IngresarBtn);

        btnRegistrar = findViewById(R.id.registrarBtn);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toasty.warning(LoginActivity.this, "Por favor, ingrese un nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    // Utiliza la autenticación de Firebase para verificar las credenciales del usuario
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        String uid = user.getUid(); // Obtiene el UID del usuario
                                        Toasty.success(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(LoginActivity.this, DishesActivity.class);
                                        intent.putExtra("USER_UID", uid); // Pasa el UID a la siguiente actividad
                                        startActivity(intent);
                                    } else {
                                        String errorMessage = getLoginErrorMessage(task.getException());
                                        Toasty.error(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        Button olvidasteLinkText = findViewById(R.id.olvidasteLinkText);
        olvidasteLinkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RecoverPassword.class);
                startActivity(intent);
            }
        });


    }

    private String getLoginErrorMessage(Exception exception) {
        if (exception != null) {
            String error = exception.getMessage();
            if (error.contains("There is no user record corresponding to this identifier")) {
                return "No hay ningún usuario que corresponda a este correo electrónico.";
            } else if (error.contains("The password is invalid or the user does not have a password")) {
                return "La contraseña es incorrecta o el usuario no tiene una contraseña.";
            } else if (error.contains("A network error (such as timeout, interrupted connection or unreachable host) has occurred")) {
                return "Se ha producido un error de red. Por favor, intente de nuevo.";
            } else if (error.contains("The email address is badly formatted")) {
                return "El formato del correo electrónico es inválido.";
            } else if (error.contains("The user account has been disabled by an administrator")) {
                return "La cuenta de usuario ha sido deshabilitada por un administrador.";
            }
        }
        return "Error desconocido: " + exception.getMessage();
    }
}
