package com.example.my_journal.Password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.my_journal.Start_journal_activity;

public class Confirm_password_activitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //비밀번호를 확인하는 액티비티를 따로 만든 이유
        // 메인액티비티에서  비밀번호를 입력, 확인하는 액티비티로 이동하고  다시 메인엑티비티로 오게 구성하면
        // oncreate() 에 액티비티 이동 인텐트가 있어서 계속해서 비밀번호를 입력하는 과정을 반복하게 됨
        // 이에 따로 레이아웃을 표시하지 않는 액티비티를 만들어 비밀번호 관련 액티비티로 이동하는 인텐트를 구성함


        SharedPreferences password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);
        String password_text = password_preferences.getString("password", "");




        // 비밀번호 입력기능을 oncreate 에 넣은 이유
        // 처음 앱을 시작했을때만 사용자가 비밀번호를 입력하게 하고자 하였다.
        // on Start(), onresume() 에 넣으면 액티비티 앞으로 다른 액티비티가 온 후
        // 다시 액티비티로 돌아올때 마다 비밀번호를 설정해야만 하기 때문에 넣지 않았다.


        if (password_text.equals("")) {
            Intent intent = new Intent(this, Start_journal_activity.class);
            startActivity(intent);
            finish();

        }



        else {

            Intent intent = new Intent(this, Check_password_activitiy.class);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

            finish();

        }


    }
}
