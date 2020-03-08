package com.example.my_journal.Advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.my_journal.Setting_activity;

public class Confirm_ad_activity extends AppCompatActivity {

    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ad_check();




    }


    public void ad_check()

    {
        preferences = getSharedPreferences("confirm_ad",MODE_PRIVATE);
        String check_ad = preferences.getString("confirm_ad","");


        if(check_ad.equals(""))

        {
            Intent intent = new Intent(this,Advertisement_activity.class);
            startActivity(intent);

            finish();


        }

        else {

            Intent intent = new Intent(this, Setting_activity.class);
            startActivity(intent);

            finish();

        }

    }

}


