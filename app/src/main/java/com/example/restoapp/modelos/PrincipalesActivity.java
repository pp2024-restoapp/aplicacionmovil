package com.example.restoapp.modelos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restoapp.CategorizeActivity;
import com.example.restoapp.R;
import java.util.ArrayList;
import java.util.List;

public class PrincipalesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPromos;
    private DrinkAdapter drinkAdapter;
    private List<Promo1> promoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principales);

        recyclerViewPromos = findViewById(R.id.recyclerViewPromos);
        recyclerViewPromos.setLayoutManager(new LinearLayoutManager(this));

        promoList = new ArrayList<>();
        promoList.add(new Promo1(R.drawable.principal1, "Halibut asado a la parrilla!!", "Un clasico de sabor ", "Precio $5500"));
        promoList.add(new Promo1(R.drawable.principales2, "Estofado de ternera bourguignon !!", "Recordas este plato !!", "Precio $5000"));
        promoList.add(new Promo1(R.drawable.principales3, "Filete de salmon con verduras  !! ", "Plato frente al mar ", "Precio $6990"));
        promoList.add(new Promo1(R.drawable.principales4, "Carne de res asada con tomates y hongos", "Probalo te va gustar!!!", "Precio $6990"));

        drinkAdapter = new DrinkAdapter(this, promoList);
        recyclerViewPromos.setAdapter(drinkAdapter);


        View btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalesActivity.this, CategorizeActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class Promo1 {
        private int imageResource;
        private String title;
        private String subtitle;
        private String price;

        public Promo1(int imageResource, String title, String subtitle, String price) {
            this.imageResource = imageResource;
            this.title = title;
            this.subtitle = subtitle;
            this.price = price;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getPrice() {
            return price;
        }
    }

    public static class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {
        private Context context;
        private List<Promo1> drinkList;

        public DrinkAdapter(Context context, List<Promo1> drinkList) {
            this.context = context;
            this.drinkList = drinkList;
        }

        @Override
        public DrinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_item_promo, parent, false);
            return new DrinkViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DrinkViewHolder holder, int position) {
            Promo1 drink = drinkList.get(position);
            holder.imageView.setImageResource(drink.getImageResource());
            holder.tvTitle.setText(drink.getTitle());
            holder.tvSubtitle.setText(drink.getSubtitle());
            holder.tvPrice.setText(drink.getPrice());
        }

        @Override
        public int getItemCount() {
            return drinkList.size();
        }

        public static class DrinkViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvTitle, tvSubtitle, tvPrice;

            public DrinkViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
                tvPrice = itemView.findViewById(R.id.tvPrice);
            }
        }
    }
}

