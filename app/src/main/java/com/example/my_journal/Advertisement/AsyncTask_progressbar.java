package com.example.my_journal.Advertisement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.my_journal.Journal.Main_journal_activitiy;
import com.example.my_journal.Journal.Write_journal_activitiy;
import com.example.my_journal.R;
import com.example.my_journal.Setting_activity;

import java.util.Random;

public class AsyncTask_progressbar extends AsyncTask<Void, Integer, Boolean> {

    // AsyncTask<A,B,C>
    // A -> doInBackground 에서 사용할 변수의 자료형
    // execute 시 전달되는 값
    // B -> onProgressUpdate 에서 사용할 변수의 자료형
    // 진행상황을 업데이트 할때 사용되는 값
    // C -> onPostExecute() 에서 사용할 변수의 자료형
    // Async 끝난후 결과값

    int value;
    ProgressBar progressBar;
    ImageView imageView;
    Button ad_skip;
    Context context;
    TextView before_skip;

    ImageView advertisement_image;
    private String Async = "async";

    public AsyncTask_progressbar(int value, ProgressBar progressBar, ImageView imageView, Button ad_skip, TextView before_skip, Context context) {
        this.value = value;
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.ad_skip = ad_skip;
        this.before_skip = before_skip;
        this.context = context;

    }

    @Override
    protected Boolean doInBackground(Void... voids) {


        // 설정한 시간동안 1초당 1만큼 progressbar 변화시킴
        // !isCancelled() -> 취소메서드가 호출되지 않았을 경우에만 실행
        for (int i = 0; i <= value && !isCancelled(); i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i); // onProgressUpdate 호출 onProgressUpdate 에서 UI 변경

        }

        return true;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ad_skip.setVisibility(View.GONE);
        before_skip.setVisibility(View.GONE);
        progressBar.setMax(value);
        progressBar.setProgress(0);


    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        Intent intent = new Intent(context, Main_journal_activitiy.class);
        context.startActivity(intent);

        ((Activity) context).finish();

        super.onPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {


        progressBar.setProgress(values[0].intValue());


        set_adimage(values[0].intValue());


        if (values[0].intValue() < 5) {


            before_skip.setVisibility(View.VISIBLE);
            int sec = 5 - values[0].intValue();
            before_skip.setText(sec + "초후 Skip>>");


        } else if (values[0].intValue() <= 10 && values[0].intValue() >= 5) {
            before_skip.setVisibility(View.GONE);
            ad_skip.setVisibility(View.VISIBLE);


            super.onProgressUpdate(values);
        }

    }


        public void set_adimage (int val)
        {



            switch (val) {
                case 0:
                    imageView.setImageResource(R.drawable.ad1);

                    break;

                case 3:
                    imageView.setImageResource(R.drawable.ad2);

                    break;
                case 6:
                    imageView.setImageResource(R.drawable.ad3);

                    break;
                case 9:
                    imageView.setImageResource(R.drawable.ad4);

                    break;
                case 12:
                    imageView.setImageResource(R.drawable.ad5);

                    break;
                case 15:
                    imageView.setImageResource(R.drawable.ad6);

                    break;
                case 18:
                    imageView.setImageResource(R.drawable.ad7);

                    break;


            }

        }

        @Override
        protected void onCancelled (Boolean aBoolean){

            super.onCancelled(aBoolean);
            Intent intent = new Intent(context, Setting_activity.class);
            context.startActivity(intent);
            ((Activity) context).finish();


        }


    }

