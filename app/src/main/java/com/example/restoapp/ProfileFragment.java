package com.example.restoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_CHANGE = 1;
    private ImageView profileImageView;
    private TextView textViewEmail;
    private Button botonCerrarSesion;
    private SharedPreferences sharedPreferences;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> startPhotoChangeActivity =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                // Obtiene la URI de la imagen seleccionada desde PhotoChangeActivity
                                String newImageUriString = data.getStringExtra("imageUri");
                                Uri newImageUri = Uri.parse(newImageUriString);

                                // Llama al método para actualizar la imagen de perfil
                                updateProfileImage(newImageUri);
                            }
                        }
                    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profile_image_view);
        textViewEmail = view.findViewById(R.id.textViewName2);
        botonCerrarSesion = view.findViewById(R.id.button5);

        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);

        botonCerrarSesion.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        showUserData();

        Button changeImageButton = view.findViewById(R.id.change_image_button);

        changeImageButton.setOnClickListener(v -> {
            Intent changeImageIntent = new Intent(requireContext(), PhotoChangeActivity.class);
            startPhotoChangeActivity.launch(changeImageIntent);
        });

        return view;
    }

    public void updateProfileImage(Uri newImageUri) {
        if (newImageUri != null) {
            profileImageView.setImageURI(newImageUri);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // El usuario está autenticado, por lo que puedes obtener su correo electrónico
            String userEmail = user.getEmail();

            // Establece el correo electrónico en el TextView
            textViewEmail.setText(userEmail);

            // Guarda el correo electrónico en SharedPreferences para su uso posterior
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", userEmail);
            editor.apply();
        } else {
            // El usuario no está autenticado
            // Realiza alguna acción si es necesario
        }
    }
}