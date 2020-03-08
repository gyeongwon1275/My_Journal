package com.example.my_journal.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_journal.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Game_mole_activity extends AppCompatActivity {


    TextView game_time;
    TextView count_digda; // 디그다를 잡은 수
    Button game_start;

    ImageView[] digda_img_array = new ImageView[9];
    // 행렬처럼 1행 1열 -> 1_1 로 표시
    int[] digda_imageID = {R.id.digda_1_1, R.id.digda_1_2, R.id.digda_1_3, R.id.digda_2_1, R.id.digda_2_2, R.id.digda_2_3, R.id.digda_3_1, R.id.digda_3_2, R.id.digda_3_3};

    final String TAG_UP = "up"; //태그용
    final String TAG_DOWN = "down";
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mole);

        game_time = findViewById(R.id.time_game);
        count_digda = findViewById(R.id.count_digda);
        game_start = findViewById(R.id.game_start);
        game_start.setVisibility(View.VISIBLE);


        for (int i = 0; i < digda_img_array.length; i++) {

            digda_img_array[i] = findViewById(digda_imageID[i]);
            digda_img_array[i].setImageResource(R.drawable.dig_down);
            // 디그다가 땅 아래에 있다. = tagoff
            // 디그다가 땅 위에 = tagon
            digda_img_array[i].setTag(TAG_DOWN);
            digda_img_array[i].setVisibility(View.GONE);

        }



        for (int i = 0; i < digda_img_array.length; i++) {



            digda_img_array[i].setOnClickListener(new View.OnClickListener() {


                //디그다이미지에 온클릭리스너
                @Override
                public void onClick(View v) {


                    // 클릭을 했을때 디그다가 땅위에 있으면 점수증가, 땅아래 디그다로 이미지 변경, 태그를 땅아래에 있는 것으로 변경
                    if ((v).getTag().toString().equals(TAG_UP)) {


                        Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_LONG).show();
                        count_digda.setText("디그다 : "+String.valueOf(score++));
                        ((ImageView) v).setImageResource(R.drawable.dig_down);
                        v.setTag(TAG_DOWN);


                    } else {

                        // 클릭을 했을때 디그다가 땅아래에 있으면 점수감소, 땅위 디그다로 이미지 변경, 태그를 땅위에 있는 것으로 변경
                        Toast.makeText(getApplicationContext(), "bad", Toast.LENGTH_LONG).show();


                        if (score <= 0) {
                            score = 0;
                            count_digda.setText(String.valueOf(score));
                        } else {


                            count_digda.setText("디그다 : "+String.valueOf(score--));
                        }


                        ((ImageView) v).setImageResource(R.drawable.dig_up);
                        v.setTag(TAG_UP);
                    }
                }
            });
        }


        // 초기설정 : 제한시간 10초 / 0마리

        game_time.setText("제한시간 : 10초");
        count_digda.setText("디그다 : 0");

        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game_start.setVisibility(View.GONE);
                count_digda.setVisibility(View.VISIBLE);
                game_time.setVisibility(View.VISIBLE);

                for (int i = 0; i < digda_img_array.length; i++) {

                    digda_img_array[i].setVisibility(View.VISIBLE);

                }





                Check_time Check_time = new Check_time();
                Thread thread = new Thread(Check_time);
                thread.start();


                for (int i = 0; i < digda_img_array.length; i++) {

                    Digda digda = new Digda(i);

                   Thread thread_digda = new Thread(digda);

                   thread_digda.start();
                }
            }
        });
    }

    // 디그다의 up,down 을 관리하는 스레드로 부터 메세지를 받아 UI의 이미지, 설정을 변화시킨다.

    Handler digda_up_handler = new Handler() {
        // 디그다가 올라왔을때 어떻게 처리할건지

        @Override
        public void handleMessage(Message msg) {
            digda_img_array[msg.arg1].setImageResource(R.drawable.dig_up);
            digda_img_array[msg.arg1].setTag(TAG_UP); //올라오면 ON태그 달아줌
        }
    };

    Handler digda_down_handler = new Handler() {

        // 디그다가 내려갔을때 어떻게 처리할건지
        @Override
        public void handleMessage(Message msg) {
            digda_img_array[msg.arg1].setImageResource(R.drawable.dig_down);
            digda_img_array[msg.arg1].setTag(TAG_DOWN); //내려오면 OFF태그 달아줌

        }
    };

    public class Digda implements Runnable { //디그다 up,down 조정
        int index = 0; //디그다 번호



        Digda(int index) {
            this.index = index;
        }


        // 디그다가 올라가있는 시간 or 내려가있는 시간을 랜덤으로 설정하는 스레드
        // 게임이 끝날때 까지
        @Override
        public void run() {
            while (true) {
                try {

                    Message msg_up = new Message();


                    int down_time = new Random().nextInt(6000) + 1000;
                    Thread.sleep(down_time); //내려가있는 시간 1초~6초 사이에서 랜덤설정

                    msg_up.arg1 = index;
                    digda_up_handler.sendMessage(msg_up);


                    Message msg_down = new Message();
                    int up_time = new Random().nextInt(2000) + 1000;
                    Thread.sleep(up_time); //올라가있는 시간 1초~2초 사이에서 랜덤설정

                    msg_down.arg1 = index;
                    digda_down_handler.sendMessage(msg_down);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 게임시간 스레드에서 메세지를 받아서 실시간으로 표시해준다.
    Handler time_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            game_time.setText("남은시간 : "+msg.arg1 + "초");
        }
    };

    public class Check_time implements Runnable {


        final int MAXTIME = 10; // 게임시간 (초)


        // 게임의 제한시간을 설정하여 시간을 감소시키는 스레드
        // 스레드가 끝나면 게임결과로 데이터를 넘기고 게임 액티비티를 종료시킨다.
        @Override
        public void run() {
            for (int i = MAXTIME; i >= 0; i--) {
                Message msg = new Message();
                msg.arg1 = i;
                time_handler.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm 분 ");
            String time = simpleDateFormat.format(date);

            Intent intent = new Intent(Game_mole_activity.this, Game_mole_result_activity.class);
            intent.putExtra("score", score);
            intent.putExtra("time", time);
            startActivity(intent);
            finish();
        }
    }
}
