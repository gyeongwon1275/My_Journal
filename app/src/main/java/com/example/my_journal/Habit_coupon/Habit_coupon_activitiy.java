package com.example.my_journal.Habit_coupon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.my_journal.R;

import java.util.ArrayList;

public class Habit_coupon_activitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_coupon);
        Intent intent = getIntent();

        int coupon_number = Integer.parseInt(intent.getStringExtra("coupon_number"));

        RecyclerView habit_coupon = findViewById(R.id.recyclerview_habit_coupon);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,5) ;
        habit_coupon.setLayoutManager(layoutManager);


        ArrayList <Integer> habit_coupon_data = new ArrayList<>();

        final Habit_coupon_adapter adapter = new Habit_coupon_adapter(habit_coupon_data,this);

        habit_coupon.setAdapter(adapter);

for (int i =1; i<=coupon_number; i++ )

{

    habit_coupon_data.add(i);


}









    }
}
