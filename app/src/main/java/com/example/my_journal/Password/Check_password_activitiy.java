package com.example.my_journal.Password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_journal.R;
import com.example.my_journal.Start_journal_activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Check_password_activitiy extends AppCompatActivity {

    private static final String CHECK = "check_password";
    EditText input_password;
    Button button_password;
    SharedPreferences password_preferences;
    String get_password;
    String take_password;
    String take_date;
    TextView check_password_date;
    ArrayList<String> array_set = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        Set<String> set = new HashSet<>();

        Log.i(CHECK, "sdsdsdf");


        check_password_date = findViewById(R.id.check_password_date);
        password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);
        get_password = password_preferences.getString("password", "");
        set = password_preferences.getStringSet("password_set", null);


        Iterator<String> iterator = set.iterator();

        for (int i = 0; i < set.size(); i++) {
            array_set.add(i, iterator.next());
        }

        Log.i(CHECK, array_set.get(0));
        Log.i(CHECK, array_set.get(1));

        if (array_set.get(0).length() > 4 || array_set.get(1).length() < 4) {
            take_date = array_set.get(0);
            take_password = array_set.get(1);



        }

        else if (array_set.get(0).length() < 4 || array_set.get(1).length() > 4) {
            take_date = array_set.get(1);
            take_password = array_set.get(0);

        }


        check_password_date.setText(take_date);


        input_password = findViewById(R.id.input_password);
        button_password = findViewById(R.id.button_password);


    }

    public void confirm_password(View view) {


        String text_input_password = input_password.getText().toString();

        if (text_input_password.equals(take_password)) { // 비밀번호 일치하면 메뉴로 이동

            Intent intent = new Intent(this, Start_journal_activity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            finish();

        } else {

            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
        }


    }
}


