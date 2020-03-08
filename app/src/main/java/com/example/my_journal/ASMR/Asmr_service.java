package com.example.my_journal.ASMR;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.my_journal.Menu_activity;
import com.example.my_journal.R;

public class Asmr_service extends Service {


    private TextView play_time_asmr, total_time_asmr;

    private SeekBar seekbar_asmr;
    private int current_time = 0, end_time = 0;

    private String choose_asmr;
    private MediaPlayer player;
    boolean music_play_condition;
    private final static String time = "time";
    private int music_position = 0;
    public final String channel_id = "asmr";
    public final String channel_name = "asmr";
    NotificationCompat.Builder builder;
    private String asmr_text;
    private int asmr_icon;


    public Asmr_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        // Activity 와 통신을 할때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 null 처리함
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test_service", "서비스의 onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행


        music_play_condition = intent.getBooleanExtra("music_condition", false);

        // 음악 중복재생 방지위함
        if (player.isPlaying() == false && music_play_condition == true) {
            choose_asmr(intent);
            player.setLooping(true);
            player.start();

            /////// Notification

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);



            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),asmr_icon);

            NotificationChannel notificationChannel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_LOW);

            manager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, channel_id)

                    .setSmallIcon(R.drawable.journal)  //상태표시줄에 보이는 아이콘 모양

                    .setLargeIcon(bitmap)

                    .setContentTitle("ASMR")

                    .setContentText(asmr_text+"가 재생되고 있어요");



            Intent intent_notify = new Intent(this, Menu_activity.class);

            PendingIntent pending = PendingIntent.getActivity(this, 1, intent_notify, PendingIntent.FLAG_UPDATE_CURRENT);


            builder.setContentIntent(pending);

            builder.setAutoCancel(true);

            Notification notification = builder.build();

            manager.notify(0, notification);


            /////// Notification

        } else if (player != null && player.isPlaying() == true) {

            Log.d("test_service", "음악일시정지");
            music_position = player.getCurrentPosition();
            player.pause();

        } else if (player != null && !player.isPlaying()) {

            player.seekTo(music_position);
            player.start();
        }

// START_NOT_STICKY
// If the system kills the service after onStartCommand() returns, do not recreate the service
// This is the safest option to avoid running your service when your application can simply restart any unfinished jobs.
        return START_NOT_STICKY;
    }


    public void choose_asmr(Intent intent) {


        choose_asmr = intent.getStringExtra("service");

        switch (choose_asmr) {

            case "rain":
                player = MediaPlayer.create(this, R.raw.rain1);
                asmr_text = "빗소리";
                asmr_icon = R.drawable.rain;
                break;

            case "small_river":
                player = MediaPlayer.create(this, R.raw.small_river1);
                asmr_text = "시냇물소리";
                asmr_icon = R.drawable.small_river;
                break;

            case "fire":
                player = MediaPlayer.create(this, R.raw.fire1);
                asmr_text = "모닥불소리";
                asmr_icon = R.drawable.fire;
                break;

            case "wave":
                player = MediaPlayer.create(this, R.raw.wave1);
                asmr_text = "파도소리";
                asmr_icon = R.drawable.wave;
                break;
        }


    }


}
