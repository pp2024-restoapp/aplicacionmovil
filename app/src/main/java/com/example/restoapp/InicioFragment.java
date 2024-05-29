package com.example.restoapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class InicioFragment extends Fragment {

    ViewFlipper v_flipper;
    int[] images = {R.drawable.red_bebidas, R.drawable.red_postres, R.drawable.red_ensalada, R.drawable.red_principal, R.drawable.red_hamburguesas};

    public InicioFragment() {

    }

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

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

        ImageButton imageButton3 = view.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BranchActivity.class);

                startActivity(intent);
            }
        });

        ImageButton imageButton4 = view.findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), activity_redes.class);

                startActivity(intent);
            }
        });

        return view;
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