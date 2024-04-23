package com.example.restoapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BranchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);

        // URL que deseas abrir en la actividad
        String url = "https://www.google.com/maps/d/u/0/edit?mid=1CtUFDKkgK0F_GZMAQwi4kOsket5NQQs&ll=47.99940151587796%2C0.1934000170272876&z=18";

        // Crea un Intent con la acción ACTION_VIEW y la URL como datos
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Inicia la actividad que puede manejar el intent (generalmente, un navegador web o la aplicación de Google Maps)
        startActivity(intent);
    }
}