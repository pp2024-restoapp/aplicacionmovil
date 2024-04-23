package com.example.restoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CHANGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button changeImageButton = findViewById(R.id.change_image_button);

        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea un Intent para abrir la actividad PhotoChangeActivity
                Intent changeImageIntent = new Intent(ProfileActivity.this, PhotoChangeActivity.class);
                startActivityForResult(changeImageIntent, REQUEST_IMAGE_CHANGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CHANGE && resultCode == RESULT_OK && data != null) {
            // Obtiene la URI de la imagen seleccionada desde PhotoChangeActivity
            String selectedImageUriString = data.getStringExtra("imageUri");
            Uri selectedImageUri = Uri.parse(selectedImageUriString);

            // Carga el fragmento ProfileFragment y pasa la URI de la imagen
            loadProfileFragment(selectedImageUri);
        }
    }

    private void loadProfileFragment(Uri selectedImageUri) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fragmentManager.findFragmentByTag("ProfileFragment");

        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, profileFragment, "ProfileFragment")
                    .commit();
        }


    }
}

