package com.example.restoapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_CHANGE = 1;
    private ImageView profileImageView;
    private TextView textViewEmail;
    private TextView textViewNombre;
    private Button botonCerrarSesion;
    private SharedPreferences sharedPreferences;

    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> startPhotoChangeActivity =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String newImageUriString = data.getStringExtra("imageUri");
                                Uri newImageUri = Uri.parse(newImageUriString);
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
        textViewNombre = view.findViewById(R.id.textViewName4);

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
            String userEmail = user.getEmail();
            String userId = user.getUid();

            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query(DatabaseHelper.TABLE_USERS,
                    new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_LASTNAME},
                    DatabaseHelper.COLUMN_UID + "=?",
                    new String[]{userId},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                @SuppressLint("Range") String apellido = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LASTNAME));

                textViewNombre.setText("Nombre: "+ nombre + " " + apellido);
                cursor.close();
            }

            textViewEmail.setText("Email: " + userEmail);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Email", userEmail);
            editor.apply();
        } else {
            Log.e("ProfileFragment", "El usuario no está autenticado");
        }
    }
}
