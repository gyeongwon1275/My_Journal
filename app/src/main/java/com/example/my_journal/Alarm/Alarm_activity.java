package com.example.my_journal.Alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.my_journal.R;
import com.example.my_journal.Setting_activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Alarm_activity extends AppCompatActivity {
    private static final String LOCALE = "locale";
    TimePicker picker;
    String am_pm;
    int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        picker = findViewById(R.id.timePicker);


        // 24시간 표시 가능하게 설정
        picker.setIs24HourView(true);

////////////////////////


        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);

        // ms 단위로 시간을 받음
        // Calendar.getInstance().getTimeInMillis() 저장된 값 없으면 현재시간으로 설정
        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());

        // Calander 객체에 받아온 시간을 집어넣음
        Calendar nextNotifyTime = new GregorianCalendar();
        nextNotifyTime.setTimeInMillis(millis);

        // Date 객체가 받을 수 있게 변환해서 Date 객체에 전달
        Date nextDate = nextNotifyTime.getTime();

        // yyyy년 MM월 dd일 EE요일 a hh시 mm분 로 날짜가 표시되도록 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ");
        String date_text = simpleDateFormat.format(nextDate);


///////////////////////////////////////////


        // 이전 설정값으로 TimePicker 초기화

        // 로케일(locale)
        // 여러 나라들은 각자 다른 문화(언어, 날짜, 시간 등)을 갖고 있다.
        // 프로그램의 국제화에 대응하여
        // 사용자로 하여금 프로그램 수행시 로케일이란 것에 의해 사용자가 원하는 환경을 선택할 수 있도록 만든 것을 말한다.
        // ex> 시간, 통화, 날짜 등
        // ex> 한국 : Locale.getDefault() ko_KR

        Date currentTime = nextNotifyTime.getTime();
        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());

        // 시간, 분을 정수자료형으로 변경하여 전달

        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));

        // TimePicker 에 반영

        picker.setHour(pre_hour);
        picker.setMinute(pre_minute);


        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                int hour_24, minute;

                // 사용자가 picker 에서 지정한 값 전달

                hour_24 = picker.getHour();
                minute = picker.getMinute();

                if (hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                } else {
                    hour = hour_24;
                    am_pm = "AM";
                }


                // 현재시간 보다 이전 시간으로 알람 설정할 경우를 대비
                // Calendar.getInstance() -> 현재의 날짜와 시간정보를 가진 Calender 객체를 생성한다.
                // https://hyeonstorage.tistory.com/205
                Calendar calendar = Calendar.getInstance();
                // System.currentTimeMillis() -> 현재시간 구하기
                calendar.setTimeInMillis(System.currentTimeMillis());

                // 사용자가 지정한 시간, 분 Calender 객체에 전달
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);


                if (calendar.before(Calendar.getInstance())) {
                    // 현재시간보다 이전 시간을 알람설정하면 하루 뒤 알람이 설정되도록 한다.
                    calendar.add(Calendar.DATE, 1);
                }

                Date setting_time = calendar.getTime();
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 hh시 mm분 ", Locale.getDefault()).format(setting_time);
                Toast.makeText(getApplicationContext(), date_text + "으로 알람이 설정!", Toast.LENGTH_SHORT).show();

                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", calendar.getTimeInMillis());
                editor.putBoolean("set_alarm", true);
                editor.commit();


                diaryNotification(calendar);


                finish();

            }

        });
    }


    public void diaryNotification(Calendar calendar) {


        Boolean dailyNotify = true; // 알람을 사용하기 위한 조건변수

        // packageManager
        // 안드로이드 시스템에 접근하기 위해 사용
        // retrieving various kinds of information related to the application packages that are currently installed on the device.
        PackageManager pm = this.getPackageManager();

        // componentName : Identifier for a specific application component
        // identifier : 식별자 ( = 어떤 대상을 유일하게 식별 및 구별할 수 있는 이름 )
        // 생성자로 package 와 Component class 명을 받는다.

        // 현재 앱이 아닌 다른 앱의 구성요소를 사용하기 위해 사용한다.
        ComponentName receiver = new ComponentName(this, Alarm_receiver_device_boot.class);
        Intent alarmIntent = new Intent(this, Alarm_receiver.class);
        // Pending 대기중, 미결의
        // PendingIntent -> 대기중인 인텐트
        // 기존 인텐트와 차이점은 예를 들어 액티비티 이동 인텐트 코드를 작성하면 코드를 작성한 곳에서 실행이된다.
        // 반면 Pending Intent 경우 대기하고 있다가 특정조건이 성립하면 코드가 실행된다.

        // ex> 시스템의 NotificationManager 가 현재 앱의 인텐트를 사용
        // 홈화면의 앱 위젯에서 인텐트 실행
        // 알람기능과 같이 특정시점에 인텐트 실행

        // PendingIntent in Developer
        // A description of an Intent and target action to perform with it
        // the returned object can be handed to other applications
        // so that they can perform the action you described on your behalf at a later time.


        // getBroadcast() 이용하여 pendingIntent 통해 broadcast 를 실행한다.
        // ex> getActivity() pendingIntetnt 통해 Activity 실행시킨다.
//
//       getBroadcast (Context context, int requestCode, Intent intent, int flags)
//      context : The Context in which this PendingIntent should perform the BroadCast
//      requestCode : Private request code for the sender
//      intent : The Intent to be broadcast.

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        // 알람은 운영체제가 관리한다. 앱이 종료되어도 알람은 동작한다.
        // 운영체제로 부터 알람기능을 가져온다.
        // getSystemService(Context.ALARM_SERVICE) : 시스템이 가지고 있는 기능 중 알람기능을 가져온다.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {


            if (alarmManager != null) { // 알람매니져가 있으면

                // type: type of alarm
                // triggerAtMillis : time in milliseconds that the alarm should first go off,
                // intervalMillis : interval in milliseconds between subsequent repeats of the alarm
                // operation: Action to perform when the alarm goes off

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);


            }

            // setComponentEnabledSetting
            // 부팅 후 실행되는 BroadCast Receiver 를 사용가능하게 설정
            // Set the enabled setting for a package component (activity, receiver, service, provider).
            // This setting will override any enabled state which may have been set by the component in its manifest.

            // COMPONENT_ENABLED_STATE_ENABLED
            // This component or application has been explictily enabled,
            // regardless of what it has specified in its manifest.

            // DONT_KILL_APP
            // indicate that you don't want to kill the app containing the component.
            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        }


    }
}
