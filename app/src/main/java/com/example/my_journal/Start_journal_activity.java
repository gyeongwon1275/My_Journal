package com.example.my_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class Start_journal_activity extends AppCompatActivity {


    // git_hub test
    ImageButton start_journal_menu;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_journal);

        initialize_ad();

        lottieAnimationView = findViewById(R.id.start_journal_image);

        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Start_journal_activity.this, Menu_activity.class);

                startActivity(intent);

                finish();


            }
        });


        start_journal_menu = findViewById(R.id.start_journal_menu);

        start_journal_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Start_journal_activity.this, Menu_activity.class);

                startActivity(intent);

                finish();

            }
        });




    }


    public void initialize_ad() {
        SharedPreferences preferences = getSharedPreferences("confirm_ad", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("confirm_ad", "");
        editor.apply();

    }

//    public void get_debug_hash_key()
//
//    {
//        public String getKeyHash(final Context context) {
//        PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES);
//        if (packageInfo == null)
//            return null;
//​
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//​
//                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//            } catch (NoSuchAlgorithmException e) {
//                Log.w(TAG, "디버그 keyHash" + signature, e);
//            }
//        }
//        return null;
//    }
//
//    }
}
