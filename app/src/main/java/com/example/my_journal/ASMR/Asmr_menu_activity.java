package com.example.my_journal.ASMR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.my_journal.R;

public class Asmr_menu_activity extends AppCompatActivity {
    ImageView small_river, fire, wave, rain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asmr_activity);


        initialize_view();

        small_river.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Asmr_menu_activity.this, Asmr_activity.class);
                intent.putExtra("ASMR", "small_river");
                startActivity(intent);


            }
        });

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Asmr_menu_activity.this, Asmr_activity.class);
                intent.putExtra("ASMR", "fire");
                startActivity(intent);


            }
        });

        rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Asmr_menu_activity.this, Asmr_activity.class);
                intent.putExtra("ASMR", "rain");
                startActivity(intent);


            }
        });

        wave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Asmr_menu_activity.this, Asmr_activity.class);
                intent.putExtra("ASMR", "wave");
                startActivity(intent);


            }
        });


    }


    public void initialize_view() {


        small_river = findViewById(R.id.small_river);
        fire = findViewById(R.id.fire);
        wave = findViewById(R.id.wave);
        rain = findViewById(R.id.rain);


    }
}
