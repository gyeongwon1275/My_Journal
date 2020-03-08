package com.example.my_journal.Password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_journal.Journal.Main_journal_activitiy;
import com.example.my_journal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Set_Password_activitiy extends AppCompatActivity {

    EditText password, reinpit_password;
    private String password_number;
    SharedPreferences password_preferences;
    SharedPreferences.Editor password_editor;
    Button password_save;
    TextView set_password_date;


    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm 분 ");
    String time = simpleDateFormat.format(date);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_password);
        password = findViewById(R.id.password);
        reinpit_password = findViewById(R.id.reinpit_password);
        password_save = findViewById(R.id.password_save);


        password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);

        String password_text = password_preferences.getString("password","");










    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_password, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // item -> 터치된 메뉴가 넘어온다.
        int id = item.getItemId();

        if (id == R.id.reset_password) {

        }


        return super.onOptionsItemSelected(item);

    }



    public void password_save(View view) {


            String text_password = password.getText().toString();
            String text_reinpit_password = reinpit_password.getText().toString();


            if(text_password.length()>4)

            {
                Toast.makeText(this,"비밀번호는 4자리 입니다.",Toast.LENGTH_SHORT).show();
                password.setText("");
                reinpit_password.setText("");
            }

            else if(text_password.equals("") || text_reinpit_password.equals("") )

            { // 비밀번호 없을 때

                Toast.makeText(this,"비밀번호가 없습니다.",Toast.LENGTH_SHORT).show();

                password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);
                password_editor = password_preferences.edit();
                password_editor.putString("password", text_password);
                password_editor.commit();

            }

            else if(text_password.equals(text_reinpit_password) ||text_password.length()<4 )

            { // 비밀번호 입력했을 때 저장


                Set<String> set = new HashSet<>();

                set.add(text_password);
                set.add(time);


                password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);
                password_editor = password_preferences.edit();
                password_editor.putString("password", text_password);
                password_editor.putStringSet("password_set", set);



                password_editor.commit();


                Intent intent = new Intent(this, Main_journal_activitiy.class);
                if (intent.resolveActivity(getPackageManager()) != null)

                {
                startActivity(intent);
                }
                finish();

            }


            else{

                Toast.makeText(this,"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();


            }






    }


    public void reset_password(MenuItem item) {

        Toast.makeText(this,"비밀번호가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
        password.setText("");
        reinpit_password.setText("");
        password_preferences = getSharedPreferences("password_preferences", MODE_PRIVATE);
        password_editor = password_preferences.edit();
        password_editor.clear();
        password_editor.commit();


    }

//
//    public void press_number_one(View view) {
//
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "1");
//
//    }
//
//    public void press_number_two(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "2");
//    }
//
//    public void press_number_three(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "3");
//    }
//
//
//    public void press_number_four(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "4");
//
//    }
//
//
//    public void press_number_five(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "5");
//
//
//    }
//
//    public void press_number_six(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "6");
//
//
//    }
//
//    public void press_number_seven(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "7");
//
//
//    }
//
//    public void press_number_eight(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "8");
//
//
//    }
//
//    public void press_number_nine(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "9");
//
//
//    }
//
//
//    public void press_number_zero(View view) {
//
//        String password_text = password.getText().toString();
//
//        password.setText(password_text + "0");
//
//
//    }
//
//
//    public void password_erase(View view) {
//
//
//
//
//        password.setText("");
//
//
//    }


}

