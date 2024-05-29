package com.example.restoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class InicioFragment extends Fragment {

    ViewFlipper v_flipper;
    int[] images = {R.drawable.peruana, R.drawable.sushi, R.drawable.pollo};

    public InicioFragment() {
        // Constructor por defecto requerido.
    }

    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        v_flipper = view.findViewById(R.id.v_flipper);

        // Agregar imágenes al ViewFlipper
        for (int image : images) {
            flipperImages(image);
        }
        ImageButton imageButton3 = view.findViewById(R.id.imageButton3);

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para abrir la actividad SucursalesActivity
                Intent intent = new Intent(getActivity(), BranchActivity.class);

                // Inicia la actividad SucursalesActivity
                startActivity(intent);
            }
        });

        ImageButton imageButton4 = view.findViewById(R.id.imageButton4);

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un Intent para abrir la actividad ActivityRedes
                Intent intent = new Intent(getActivity(), activity_redes.class);

                // Inicia la actividad ActivityRedes
                startActivity(intent);
            }
        });

        // inicio { visitar restoApp web }

        // Encuentra el botón por su ID
        Button btnAbrirUrl = view.findViewById(R.id.btnAbrirUrl);

        // Configuramos el evento OnClickListener
        btnAbrirUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL que deseamos abrir
                String url = "https://github.com/pp2024-restoapp";

                // Crea un Intent con la URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Inicia la actividad del navegador
                startActivity(intent);
            }
        });

        // fin { visitar restoApp web }
        return view;
    }

    public void flipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(image);
        v_flipper.addView(imageView);
        v_flipper.setAutoStart(true);

        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);

        Animation slideInLeft = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation slideOutRight = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        v_flipper.setInAnimation(slideInLeft);
        v_flipper.setOutAnimation(slideOutRight);
    }
}
