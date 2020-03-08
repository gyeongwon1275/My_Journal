package com.example.my_journal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_journal.Alarm.Alarm_activity;
import com.example.my_journal.Password.Set_Password_activitiy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Setting_activity extends AppCompatActivity {

    private static final String TAG = "setting_callback";
    ImageButton imageButton_setting_before;
    CheckBox checkBox_password, checkBox_alarm;
    TextView text_alarm, text_alarm_time;
    private boolean checked_box;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar cal = Calendar.getInstance();
    boolean check_alarm;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH시 mm분");
    String time = simpleDateFormat.format(cal.getTime());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initiailize_view();
        existed_alarm_check();


        checkBox_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_alarm_set();


            }
        });

        text_alarm_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Setting_activity.this, Alarm_activity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void initiailize_view() {
        checkBox_password = findViewById((R.id.checkBox_password));
        checkBox_alarm = findViewById(R.id.checkBox_alarm);
        text_alarm = findViewById(R.id.text_alarm);
        text_alarm_time = findViewById(R.id.text_alarm_time);

    }



    public void check_alarm_set() {


        if (checkBox_alarm.isChecked() == true) {


            text_alarm.setVisibility(View.VISIBLE);
            text_alarm_time.setVisibility(View.VISIBLE);








        } else if (checkBox_alarm.isChecked() == false) {


            text_alarm.setVisibility(View.GONE);
            text_alarm_time.setVisibility(View.GONE);
            preferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
            editor =preferences.edit();
            editor.clear();
            editor.commit();

            Toast.makeText(this,"알람설정이 해제되었습니다.",Toast.LENGTH_SHORT).show();


        }

    }

    public void existed_alarm_check() {



        preferences = getSharedPreferences("daily alarm", MODE_PRIVATE);

        check_alarm = preferences.getBoolean("set_alarm", false);

        if(check_alarm == true)

        {
            checkBox_alarm.setChecked(true);
            text_alarm.setVisibility(View.VISIBLE);
            text_alarm_time.setVisibility(View.VISIBLE);



            long millis = preferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

            // Calander 객체에 받아온 시간을 집어넣음
            Calendar nextNotifyTime = new GregorianCalendar();
            nextNotifyTime.setTimeInMillis(millis);

            // Date 객체가 받을 수 있게 변환해서 Date 객체에 전달
            Date nextDate = nextNotifyTime.getTime();

            // yyyy년 MM월 dd일 EE요일 a hh시 mm분 로 날짜가 표시되도록 설정
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh시 mm분 ");
            String date_text = simpleDateFormat.format(nextDate);

            text_alarm_time.setText(date_text);

        }

        else {

            text_alarm.setVisibility(View.GONE);
            text_alarm_time.setVisibility(View.GONE);
            text_alarm_time.setText(time);
            editor = preferences.edit();
            editor.clear();
            editor.commit();
        }


    }


    public void move_set_password(View view) {

        Intent intent = new Intent(Setting_activity.this, Set_Password_activitiy.class);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
