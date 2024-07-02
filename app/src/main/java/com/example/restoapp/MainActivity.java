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

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etName;
    private EditText etLastname;
    private Button btnRegister;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        etUsername = findViewById(R.id.correoEditText);
        etPassword = findViewById(R.id.contrasenaEditText);
        etConfirmPassword = findViewById(R.id.contrasenaConfirmaEditText);
        etName = findViewById(R.id.nombreEditText);
        etLastname = findViewById(R.id.apellidoEditText);
        btnRegister = findViewById(R.id.IngresarBtn);
        btnLogin = findViewById(R.id.registrarBtn);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String name = etName.getText().toString().trim();
                String lastname = etLastname.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || lastname.isEmpty()) {
                    Toasty.warning(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT, true).show();
                } else if (!password.equals(confirmPassword)) {
                    Toasty.error(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    // Utiliza la autenticación de Firebase para registrar el usuario
                    mAuth.createUserWithEmailAndPassword(username, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toasty.success(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                        // Almacenar el nombre y el apellido en SQLite
                                        if (user != null) {
                                            dbHelper.addUser(user.getUid(), name, lastname);
                                        }

                                        // Redirigir al usuario a la actividad principal o a donde necesites
                                        Intent intent = new Intent(MainActivity.this, DishesActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String errorMessage = getErrorMessage(task.getException());
                                        Toasty.error(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private String getErrorMessage(Exception exception) {
        if (exception != null) {
            String error = exception.getMessage();
            if (error.contains("The email address is badly formatted")) {
                return "El formato del correo electrónico es inválido.";
            } else if (error.contains("The email address is already in use by another account")) {
                return "El correo electrónico ya está en uso por otra cuenta.";
            } else if (error.contains("The given password is invalid")) {
                return "La contraseña es inválida, debe tener al menos 6 caracteres.";
            } else if (error.contains("The email address is already in use")) {
                return "El correo electrónico ya está en uso.";
            } else if (error.contains("Password should be at least 6 characters")) {
                return "La contraseña debe tener al menos 6 caracteres.";
            }
        }
        return "Error desconocido: " + exception.getMessage();
    }
}
