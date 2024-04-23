package com.example.restoapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class DishesActivity extends AppCompatActivity {
    //ViewFlipper v_flipper;

    //int[] images = {R.drawable.peruana,R.drawable.sushi,R.drawable.pollo};


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        // Verificar si el nombre de usuario no es nulo
        if (nombreUsuario != null) {
            // Asignar el nombre de usuario al TextView
            TextView tvUsername = findViewById(R.id.textView8);
            tvUsername.setText("Hola, " + nombreUsuario);
        }
//       v_flipper = findViewById(R.id.v_flipper);
//
//        for (int image : images) {
//            flipperImages(image);
//        }



        boolean showThirdFragment = getIntent().getBooleanExtra("showThirdFragment", false);
        if (showThirdFragment) {
            // Obtén el controlador de navegación
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

            if (navHostFragment != null) {
                NavController navController = navHostFragment.getNavController();

                // Navega al tercer fragmento
                navController.navigate(R.id.page_perfil);
            }
        }


        setupNavigation();

    }

    private void setupNavigation(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navHostFragment.getNavController()
        );
    }


//    public void flipperImages(int image) {
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(image);
//        v_flipper.addView(imageView);
//        v_flipper.setAutoStart(true);
//
//        v_flipper.setFlipInterval(3000);
//        v_flipper.setAutoStart(true);
//
//        Animation slideInLeft = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
//        Animation slideOutRight = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
//        v_flipper.setInAnimation(slideInLeft);
//        v_flipper.setOutAnimation(slideOutRight);
//    }
}
