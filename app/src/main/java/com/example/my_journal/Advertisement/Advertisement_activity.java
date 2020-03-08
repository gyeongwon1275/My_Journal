package com.example.my_journal.Advertisement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.my_journal.R;

public class Advertisement_activity extends AppCompatActivity {

    ProgressBar time_progressbar;
    ImageView advertisement_image;
    VideoView videoView;
    TextView before_skip;
    Button ad_skip;
    AsyncTask_progressbar asyncTask;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // 버튼이 가려져 있다가 15초 지나면 등장
    // 버튼을 누르면 스레드 종료, 액티비티 종료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        initialize_view();


        int ad_time_sec = 30;
        asyncTask = new AsyncTask_progressbar(ad_time_sec, time_progressbar, advertisement_image, ad_skip, before_skip, this);
        asyncTask.execute();

        play_video();


        ad_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stop_video();
                asyncTask.cancel(true);

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();


        preferences = getSharedPreferences("confirm_ad",MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("confirm_ad","watch_ad");
        editor.commit();


    }

    public void play_video() {
        videoView = findViewById(R.id.testVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ad_video);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }

    public void stop_video() {
        videoView = findViewById(R.id.testVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ad_video);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.stopPlayback();
    }


    public void initialize_view() {
        time_progressbar = findViewById(R.id.time_progressbar);
        advertisement_image = findViewById(R.id.advertisement_image);
        ad_skip = findViewById(R.id.ad_skip);
        before_skip = findViewById(R.id.before_skip);
    }


}
