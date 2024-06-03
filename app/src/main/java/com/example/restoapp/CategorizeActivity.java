package com.example.restoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restoapp.modelos.BebidasActivity;
import com.example.restoapp.modelos.EnsaladasActivity;
import com.example.restoapp.modelos.EntradaActivity;
import com.example.restoapp.modelos.HamburguesasActivity;
import com.example.restoapp.modelos.LomosActivity;
import com.example.restoapp.modelos.PastasActivity;
import com.example.restoapp.modelos.PizzasActivity;
import com.example.restoapp.modelos.PostresActivity;
import com.example.restoapp.modelos.PrincipalesActivity;
import com.example.restoapp.modelos.PromosActivity;

public class CategorizeActivity extends AppCompatActivity {

    private ImageButton btnAtras;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,imageView7, imageView8, imageView9, imageView10; // Agregar referencias para imageView2 y imageView3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorize);

        // Inicialización de los elementos de la vista
        btnAtras = findViewById(R.id.imageButton14);
        imageView1 = findViewById(R.id.imageView1); // Asignar imageView1 desde el layout
        imageView2 = findViewById(R.id.imageView2); // Asignar imageView2 desde el layout
        imageView3 = findViewById(R.id.imageView3); // Asignar imageView3 desde el layout
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6); // Asignar imageView1 desde el layout
        imageView7 = findViewById(R.id.imageView7); // Asignar imageView2 desde el layout
        imageView8 = findViewById(R.id.imageView8); // Asignar imageView3 desde el layout
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        // Configuración del OnClickListener para el botón de retroceso
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorizeActivity.this, DishesActivity.class);
                startActivity(intent);
            }
        });

        // Configuración del OnClickListener para imageView1
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView1 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, PromosActivity.class);
                startActivity(intent);
            }
        });

        // Configuración del OnClickListener para imageView2
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView2 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, BebidasActivity.class);
                startActivity(intent);
            }
        });

        // Configuración del OnClickListener para imageView3
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, EntradaActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, PostresActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, PastasActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, EnsaladasActivity.class);
                startActivity(intent);
            }
        });
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, PrincipalesActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, PizzasActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3

        // Configuración del OnClickListener para imageView3
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, HamburguesasActivity.class);
                startActivity(intent);
            }
        });
        // Configuración del OnClickListener para imageView3
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar PromosActivity cuando imageView3 es clickeado
                Intent intent = new Intent(CategorizeActivity.this, LomosActivity.class);
                startActivity(intent);
            }
        });
    }
}