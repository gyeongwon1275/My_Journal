package com.example.my_journal.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.my_journal.Menu_activity;
import com.example.my_journal.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Alarm_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // You cannot launch a popup dialog in your implementation of onReceive().
        // 방송을 받았을 때 어떤 행동을 할것인지 명시

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, Menu_activity.class);

        // FLAG_ACTIVITY_CLEAR_TOP
        // 호출하는 Activity가 스택에 있을 경우, 해당 Activity를 최상위로 올리면서, 그 위에 있던 Activity들을 모두 삭제하는 Flag
        // FLAG_ACTIVITY_SINGLE_TOP
        // 호출되는 Activity가 최상위에 있을 경우 해당 Activity를 다시 생성하지 않고, 있던 Activity를 다시 사용
        // https://medium.com/@logishudson0218/intent-flag%EC%97%90-%EB%8C%80%ED%95%9C-%EC%9D%B4%ED%95%B4-d8c91ddd3bfc
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        // OREO API 26 이상에서는 채널 필요 2017.08월 출시
        // Notification Channel을 통해 Notification을 여러가지 용도로 나누어서 관리할 수 있게 만들어 줍니다.
        // Channel : 어떤 일을 이루는 방법이나 정보가 전달되는 경로

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


            builder.setSmallIcon(R.drawable.journal); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

        // 채널 생성 -> ID, 이름, 중요도 설정
            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }


                 // setAutoCancel : 알람터치시 자동으로 삭제할 건지 설정
                  builder.setAutoCancel(true)
                // setDefaults  알람시 진동, 사운드 설정
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                // 알람시간
                .setWhen(System.currentTimeMillis())
                // 알람발생시 잠시동안 나오는 텍스트
                .setTicker("{Time to watch some cool stuff!}")
                          // 상태바 드래그시 보이는 타이틀
                .setContentTitle("일기알람")
                          // 상태바 드래그시 보이는 서브타이틀
                .setContentText("일기쓸 시간입니다. ")
                .setContentInfo("INFO")
                          // 터치시 인텐트 실행
                .setContentIntent(pendingI);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1, builder.build());

            Calendar nextNotifyTime = Calendar.getInstance();

            // 내일 같은 시간으로 알람시간 결정
            nextNotifyTime.add(Calendar.DATE, 1);

            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.commit();

//            Date currentDateTime = nextNotifyTime.getTime();
//            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
//            Toast.makeText(context.getApplicationContext(),"다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
        }
    }
    }
