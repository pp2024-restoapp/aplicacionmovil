package com.example.restoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class activity_redes extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private EditText editTextComentario;
    private ImageView imageViewSelectedFile;
    private List<Uri> selectedFiles = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes);

        editTextComentario = findViewById(R.id.editTextComentario);
        imageViewSelectedFile = findViewById(R.id.imageView);

        editTextComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextComentario.setText(""); // Borra el contenido del EditText al hacer clic
            }
        });

        Button btnEnviarComentario = findViewById(R.id.btnEnviarComentario);
        btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtiene el comentario del EditText
                String comentario = editTextComentario.getText().toString();

                if (!comentario.isEmpty()) {
                    // Muestra un mensaje en forma de Toast
                    Toast.makeText(activity_redes.this, "Mensaje Enviado", Toast.LENGTH_SHORT).show();
                    // Borra el contenido del EditText
                    editTextComentario.setText("");
                    // Borra la imagen seleccionada
                    imageViewSelectedFile.setImageResource(0);
                    selectedFiles.clear();
                }
            }
        });

        ImageButton btnSelectFile = findViewById(R.id.btnSelectFile);
        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilePicker();
            }
        });

        ImageButton btnSelectFoto = findViewById(R.id.btnSelectFoto);
        btnSelectFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aquí puedes implementar la lógica para abrir la selección de fotos
            }
        });

        ImageButton btnOpenFacebook = findViewById(R.id.btnOpenFacebook);
        btnOpenFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookProfile("nombre_de_usuario_facebook");
            }
        });

        ImageButton btnOpenWhatsApp = findViewById(R.id.btnOpenWhatsApp);
        btnOpenWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsAppChat("número_de_teléfono");
            }
        });

        ImageButton btnOpenInstagram = findViewById(R.id.btnOpenInstagram);
        btnOpenInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagramProfile("nombre_de_usuario_instagram");
            }
        });
    }

    private void openWhatsAppChat(String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");

            // Define el número de teléfono del destinatario con el prefijo internacional
            String internationalNumber = "+1" + phoneNumber;

            // Agrega el número de teléfono al Intent
            intent.putExtra("jid", internationalNumber + "@s.whatsapp.net");

            // Inicia la conversación en WhatsApp
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            // Maneja excepciones si WhatsApp no está instalado o si ocurre un error
            e.printStackTrace();
            Toast.makeText(this, "No se pudo abrir WhatsApp. Asegúrate de que esté instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFacebookProfile(String facebookUsername) {
        try {
            // Intenta abrir la aplicación de Facebook si está instalada
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + facebookUsername));
            startActivity(intent);
        } catch (Exception e) {
            // Si no está instalada, abre Facebook en el navegador web
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + facebookUsername));
            startActivity(intent);
        }
    }

    private void openInstagramProfile(String instagramUsername) {
        Uri uri = Uri.parse("http://instagram.com/_u/" + instagramUsername);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // Asegúrate de que la aplicación de Instagram esté instalada en el dispositivo
        intent.setPackage("com.instagram.android");

        try {
            startActivity(intent);
        } catch (Exception e) {
            // En caso de que Instagram no esté instalado, abre Instagram en el navegador web
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + instagramUsername)));
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        startActivityForResult(Intent.createChooser(intent, "Seleccionar archivo"), PICK_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                // Aquí puedes trabajar con el archivo seleccionado (por ejemplo, guardar la ruta o cargarlo).
                selectedFiles.add(selectedFileUri);
                imageViewSelectedFile.setImageURI(selectedFiles.get(0)); // Muestra el primer archivo seleccionado en el ImageView
            }
        }
    }
}
