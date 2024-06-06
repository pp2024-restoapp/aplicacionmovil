package com.example.restoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioFragment extends Fragment {

    ViewFlipper v_flipper;
    int[] images = {R.drawable.red_bebidas, R.drawable.red_postres, R.drawable.red_ensalada, R.drawable.red_principal, R.drawable.red_hamburguesas};

    private TextView textViewNombre;

    public InicioFragment() {
        // Required empty public constructor
    }

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        textViewNombre = view.findViewById(R.id.textView8); // Obtén el TextView de tu layout

        v_flipper = view.findViewById(R.id.v_flipper);

        for (int image : images) {
            flipperImages(image);
        }

        v_flipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategorizeActivity.class);
                startActivity(intent);
            }
        });

        // CardView for "Cerca" section
        CardView cardViewCerca = view.findViewById(R.id.cardViewCerca);
        cardViewCerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BranchActivity.class);
                startActivity(intent);
            }
        });

        // CardView for "Contactanos" section
        CardView cardViewContactanos = view.findViewById(R.id.cardViewContactanos);
        cardViewContactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), activity_redes.class);
                startActivity(intent);
            }
        });

        // Button to open URL
        Button btnAbrirUrl = view.findViewById(R.id.btnAbrirUrl);
        btnAbrirUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/pp2024-restoapp";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        // Get NavController
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // CardView for "Reservar" section
        CardView cardViewReservar = view.findViewById(R.id.cardViewReservar);
        cardViewReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a fragment_reservas
                navController.navigate(R.id.page_reservas);
            }
        });

        showUserData(); // Llama al método para mostrar los datos del usuario

        return view;
    }

    private void showUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
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

                textViewNombre.setText("¡Hola " + nombre + "!");
                cursor.close();
            } else {
                textViewNombre.setText("Usuario no encontrado");
            }

            db.close();
        } else {
            Log.e("InicioFragment", "El usuario no está autenticado");
        }
    }

    public void flipperImages(int image) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        linearLayoutParams.setMargins(10, 0, 10, 0);
        linearLayout.setLayoutParams(linearLayoutParams);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        imageView.setLayoutParams(imageViewParams);

        linearLayout.addView(imageView);
        v_flipper.addView(linearLayout);
        v_flipper.setAutoStart(true);
        v_flipper.setFlipInterval(3000);

        Animation slideInLeft = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation slideOutRight = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        v_flipper.setInAnimation(slideInLeft);
        v_flipper.setOutAnimation(slideOutRight);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getActivity(), "Haga clic en su plato favorito!", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
