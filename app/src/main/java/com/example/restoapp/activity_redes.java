package com.example.restoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class activity_redes extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST_CODE = 1;
    private EditText editTextComentario;

    private ImageView btn_atras;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes);

        editTextComentario = findViewById(R.id.editTextComentario);
        btn_atras = findViewById(R.id.imageButton14);

        editTextComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextComentario.setText("");
            }
        });

        Button btnEnviarComentario = findViewById(R.id.btnEnviarComentario);
        btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String comentario = editTextComentario.getText().toString();

                if (!comentario.isEmpty()) {

                    Toasty.success(activity_redes.this, "¡Mensaje Enviado! Gracias por tu opinion", Toast.LENGTH_SHORT).show();

                    editTextComentario.setText("");
                } else {
                    Toasty.warning(activity_redes.this, "Debe ingresar un comentario", Toast.LENGTH_SHORT).show();
                }
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

        ImageButton btnOpenFacebook = findViewById(R.id.btnOpenFacebook);
        btnOpenFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookProfile("nombre_de_usuario_facebook");
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = editTextComentario.getText().toString();
                if (comentario.isEmpty()) {

                    Intent intent = new Intent(activity_redes.this, DishesActivity.class);
                    startActivity(intent);
                    return;
                }


                showDiscardCommentDialog();
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
            Toasty.error(this, "No se pudo abrir WhatsApp. Asegúrate de que esté instalado.", Toast.LENGTH_SHORT).show();
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

    private void showDiscardCommentDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Descartar Comentario");
        builder.setMessage("¿Está seguro que desea descartar el comentario?");
        builder.setPositiveButton("Descartar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                editTextComentario.setText(""); // Clear comment
                Intent intent = new Intent(activity_redes.this, DishesActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });


        builder.create().show();
    }
}
