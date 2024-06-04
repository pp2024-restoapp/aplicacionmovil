package com.example.restoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class PhotoChangeActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private ImageView imageselect;
    private Uri selectedImageUri;
    private int selectedImageResource;
    private ImageView btn_atras;
    private Button confirmarBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        imageselect = findViewById(R.id.imageselect);

        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView6 = findViewById(R.id.imageView6);
        ImageView imageView7 = findViewById(R.id.imageView7);
        ImageView imageView8 = findViewById(R.id.imageView8);
        ImageView imageView9 = findViewById(R.id.imageView9);
        ImageView imageView10 = findViewById(R.id.imageView10);

        btn_atras = findViewById(R.id.imageButton2);
        confirmarBtn = findViewById(R.id.confirmarBtn);

        // Configura los OnClickListener para los ImageView
        imageView3.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image1);
            selectedImageResource = R.drawable.image1;
        });
        imageView4.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image2);
            selectedImageResource = R.drawable.image2;
        });
        imageView5.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image3);
            selectedImageResource = R.drawable.image3;
        });
        imageView6.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image4);
            selectedImageResource = R.drawable.image4;
        });
        imageView7.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image5);
            selectedImageResource = R.drawable.image5;
        });
        imageView8.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image6);
            selectedImageResource = R.drawable.image6;
        });
        imageView9.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image8);
            selectedImageResource = R.drawable.image8;
        });
        imageView10.setOnClickListener(v -> {
            imageselect.setImageResource(R.drawable.image9);
            selectedImageResource = R.drawable.image9;
        });

        ImageButton selectImageBtn = findViewById(R.id.selectImageBtn);
        selectImageBtn.setOnClickListener(v -> {
            // Abre la galería para seleccionar una imagen.
            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickImage, REQUEST_IMAGE_PICK);
        });

        confirmarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                if (selectedImageUri != null) {
                    // Envía la URI de la imagen de regreso a la actividad anterior (ProfileFragment)
                    returnIntent.putExtra("imageUri", selectedImageUri.toString());
                } else if (selectedImageResource != 0) {
                    // Envía el recurso de la imagen seleccionada a la actividad anterior (ProfileFragment)
                    returnIntent.putExtra("imageResource", selectedImageResource);
                }
                setResult(RESULT_OK, returnIntent);
                finish(); // Cierra la actividad actual
            }
        });

        btn_atras.setOnClickListener(v -> {
            Intent intent = new Intent(PhotoChangeActivity.this, ProfileFragment.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                try {
                    // Utiliza el método MediaStore.Images.Media.getBitmap para decodificar la imagen
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                    // Establece la imagen decodificada en el ImageView "imageselect"
                    imageselect.setImageBitmap(selectedImage);
                    selectedImageResource = 0; // Reset selectedImageResource
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

