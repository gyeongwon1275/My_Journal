package com.example.my_journal.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class Alarm_receiver_device_boot extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        // 안드로이드 폰은 부팅이 완료되면 android.intent.action.BOOT_COMPLETED 를 BroadCast 한다.


        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            // boot: 장치를 사용할 수 있도록 준비하는 것
            // on device boot complete, reset the alarm
            // 부팅이 완료되었을 때 SharedPreference 에 저장한 값을 불러와서 알람을 재설정한다.
            Intent alarmIntent = new Intent(context, Alarm_receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


            SharedPreferences sharedPreferences = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE);
            long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());


            Calendar current_calendar = Calendar.getInstance();
            Calendar nextNotifyTime = new GregorianCalendar();
            nextNotifyTime.setTimeInMillis(sharedPreferences.getLong("nextNotifyTime", millis));

            if (current_calendar.after(nextNotifyTime)) {
                nextNotifyTime.add(Calendar.DATE, 1);
            }

            Date currentDateTime = nextNotifyTime.getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
            Toast.makeText(context.getApplicationContext(), "다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();


            if (manager != null) {
                manager.setRepeating(AlarmManager.RTC_WAKEUP, nextNotifyTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }
}