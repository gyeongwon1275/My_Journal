package com.example.my_journal.ASMR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.my_journal.R;

import java.util.concurrent.TimeUnit;

public class Asmr_activity extends AppCompatActivity {
    private static final String ASMR = "asmr";

//ASMR : 청각을 중심으로 하는 감각적 자극에의해 나타나는
// 심리적 안정감이나 쾌감 따위의 감각적 경험


    private boolean musicBound = true;


    private ImageButton btn_pause_asmr, btn_play_asmr;
    private MediaPlayer Player;
    private TextView play_time_asmr, total_time_asmr;
    private ImageView image_asmr;
    private SeekBar seekbar_asmr;
    private int current_time = 0, end_time = 0;
    private boolean play_condition = true;
    private boolean play_image_condition = true;
    private String choose_asmr;
    private Handler handler_asmr = new Handler();
    boolean music_play_condition;
    private final static String time = "time";
    private Intent intent;
    String music_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asmr);
        initialize_view();
        intent = new Intent(this, Asmr_service.class);
        choose_asmr();


        music_play_condition = true;


        btn_play_asmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_asmr_service();
                music_play_condition = false;
                button_change();

            }
        });


    }


    public void button_change() {


        // 재생중일 때
        if (play_image_condition == true) {

            btn_play_asmr.setImageResource(R.drawable.ic_pause_black_24dp);
            play_image_condition = false;

        } else {

            btn_play_asmr.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            play_image_condition = true;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

//        pause_asmr();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(intent);
    }

    // seekbar, playtime update
    private Runnable update_asmr_time = new Runnable() {
        @Override
        public void run() {
            current_time = Player.getCurrentPosition();

            play_time_asmr.setText(String.format("%02d : %02d ", TimeUnit.MILLISECONDS.toMinutes(current_time),
                    TimeUnit.MILLISECONDS.toSeconds(current_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current_time))));

            seekbar_asmr.setProgress(current_time);

            handler_asmr.post(update_asmr_time);
        }
    };


    public void start_asmr_service() {


        intent.putExtra("service", choose_asmr);
        intent.putExtra("music_condition", music_play_condition);

        startService(intent);
    }

//    public void stop_asmr_service() {
//        intent = new Intent(this, Asmr_service.class);
//       stopService(intent);
//    }

    public void choose_asmr() {

        choose_asmr = getIntent().getStringExtra("ASMR");

        Log.i("choose", choose_asmr);

        switch (choose_asmr) {

            case "rain":

                image_asmr.setImageResource(R.drawable.rain);
                break;

            case "small_river":

                image_asmr.setImageResource(R.drawable.small_river);
                break;

            case "fire":

                image_asmr.setImageResource(R.drawable.fire);
                break;

            case "wave":

                image_asmr.setImageResource(R.drawable.wave);
                break;
        }


    }

    public void play_asmr() {

        Player.start();

        end_time = Player.getDuration(); // 음악 전체 시간

        current_time = Player.getCurrentPosition(); // 음악현재 재생위치

        if (play_condition) {
            seekbar_asmr.setMax(end_time); // seekbar 의 최대치를 음악 전체 시간으로 설정
            play_condition = false; // 최대치 설정을 한번만 하도록
        }

        // 전체재생시간 문자열 설정
        total_time_asmr.setText(
                // 음악의 분표시
                String.format("/ %02d : %02d ", TimeUnit.MILLISECONDS.toMinutes(end_time),
                        // 전체 음악시간의 초 표시 ex> 2분 6초 -> 126초
                        // 6초만 표현하기 위해 126초 -120초 한것
                        TimeUnit.MILLISECONDS.toSeconds(end_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end_time))));

        Log.i(time, "TimeUnit.MILLISECONDS.toMinutes(end_time) = " + TimeUnit.MILLISECONDS.toMinutes(end_time));
        Log.i(time, "TimeUnit.MILLISECONDS.toSeconds(end_time) = " + TimeUnit.MILLISECONDS.toSeconds(end_time));
        Log.i(time, "TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(end_time)) = " + TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end_time)));


        // 현재재생시간 문자열 설정
        play_time_asmr.setText(String.format("%02d : %02d ", TimeUnit.MILLISECONDS.toMinutes(current_time),
                TimeUnit.MILLISECONDS.toSeconds(current_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current_time))));


        seekbar_asmr.setProgress(current_time);


        handler_asmr.post(update_asmr_time);

        btn_pause_asmr.setEnabled(true);
        btn_play_asmr.setEnabled(false);


    }

    public void pause_asmr() {

//        Player.pause();
//        btn_pause_asmr.setEnabled(false);
//        btn_play_asmr.setEnabled(true);
//        Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();

    }

    public void initialize_view() {


        btn_play_asmr = findViewById(R.id.btn_play_asmr);
        image_asmr = findViewById(R.id.image_asmr);


    }


}

