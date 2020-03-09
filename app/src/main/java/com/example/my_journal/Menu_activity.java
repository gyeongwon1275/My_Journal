package com.example.my_journal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.my_journal.ASMR.Asmr_menu_activity;
import com.example.my_journal.Advertisement.Confirm_ad_activity;
import com.example.my_journal.Chart.Line_chart_activity;
import com.example.my_journal.Game.Game_mole_activity;
import com.example.my_journal.Journal.Main_journal_activitiy;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Menu_activity extends AppCompatActivity {

    private static final String TEST = "test";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 918;
    Button menu_game, menu_list, menu_statistics, menu_setting, menu_asmr;
    private static final int JOURNAL = 2;

    ImageView menu_image;
    TextView menu_text_time, image_taken_date, image_taken_location;
    private Handler mHandler;
    private Handler test_handler;
    String test = "";
    private int bring_image = 88;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initialize_view();
        set_main_image();


        //////////


///////////////////////////////////////AsyncTask 적용///////////////////////////////////////////////

//        AsyncTask_progressbar asyncTask = new AsyncTask_progressbar((TextView)findViewById(R.id.textView7));
//        asyncTask.execute();

///////////////////////////////////////AsyncTask 적용///////////////////////////////////////////////


/////////////////////////////////////////////time_handler message////////////////////////////////


        // 핸들러 안에 실제 수행할 작업내용이 있다.

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {

                    case 0:
                        Calendar cal = Calendar.getInstance();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM 월 dd 일 / HH:mm:ss");
                        String time = simpleDateFormat.format(cal.getTime());

                        menu_text_time = findViewById(R.id.menu_text_time);
                        menu_text_time.setText(time);
                        break;

                }


            }
        };


        class NewRunnable implements Runnable {


            @Override
            public void run() {

                while (true) {

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message message = mHandler.obtainMessage();
                    message.what = 0;

                    mHandler.sendMessage(message);
                }
            }


        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();


        test_handler = new Handler();

        Runnable runnable = new NewRunnable() {
            @Override
            public void run() {

                String test = "dddddd";

                Log.i(TEST, test + "llll");
            }

        };

        test_handler.post(runnable);

        Log.i(TEST, test + "llll");


/////////////////////////////////////////////time_handler message////////////////////////////////

        menu_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 사용자가 직접 권한을 설정할 수 있도록 한다.
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    return;
                }


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, bring_image);

            }
        });

        menu_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu_activity.this, Game_mole_activity.class);

                startActivity(intent);


            }
        });


        menu_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu_activity.this, Main_journal_activitiy.class);

                startActivity(intent);


            }
        });

        menu_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu_activity.this, Line_chart_activity.class);

                startActivity(intent);


            }
        });

        menu_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu_activity.this, Confirm_ad_activity.class);

                startActivity(intent);


            }
        });

        menu_asmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu_activity.this, Asmr_menu_activity.class);

                startActivity(intent);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == bring_image) {
                Uri selected_image = data.getData();

                // 커서에서 행단위로 가져온 이미지에 대한정보는 ID, 이름, 파일형식, 데이터 등 여러가지가 있는데
                // 거기서 특정 데이터 열 만 가져와서 사용하겠다.
                String[] file_path_column = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.TITLE};

                Cursor cursor = getContentResolver().query(selected_image, file_path_column, null, null, null);


                // Cursor.moveToFirst(); -> Cursor를 첫번째 행으로 이동
                // 사진 1개 가져왔기 때문에 첫번째 행으로 이동
                cursor.moveToFirst();

                // index : (색인) 책 속에 다루어진 중요한 단어나 용어를 독자가 쉽게 찾을 수 있도록 페이지를 밝혀 벌여 놓은 것
                // getColumnIndex () 데이터를 추출하고자 하는 열의 정보를 ( 몇번째 열인지 )  가져온다.
                int index_image_path = cursor.getColumnIndex(file_path_column[0]);
                int index_image_name = cursor.getColumnIndex(file_path_column[1]);


                // 인자로 보낸 열의 data 를 가지고 온다.
                String image_path = cursor.getString(index_image_path);

                // 파일 경로를 통해 사진찍은 날짜를 사진아래에 삽입함

                File file = new File(image_path);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 / HH시 mm분");

                String image_date = formatter.format(file.lastModified());

                image_taken_date.setText(image_date);

                // 위도, 경도 받아서 주소로 변환
                try {
                    ExifInterface exifInterface = new ExifInterface(image_path);

                    double[] gps = exifInterface.getLatLong();

                    double latitude = gps[0];
                    double longitude = gps[1];
                    LatLng latLng = new LatLng(latitude, longitude);

                    Map_activity map_activity = new Map_activity();

                    address = map_activity.getCurrentAddress(latLng);
                    address = address.replace("대한민국 ","");
                    image_taken_location.setText(address);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ///


                // cursor 사용이 끝나 종료한다.
                cursor.close();

                menu_image.setImageURI(Uri.parse(image_path));


                SharedPreferences preferences = getSharedPreferences("main_image", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("save_main_image", image_path);
                editor.putString("save_image_date", image_date);
                editor.putString("save_image_location",address);
                editor.commit();


            }
        }
    }

    public void set_main_image() {

        SharedPreferences preferences = getSharedPreferences("main_image", MODE_PRIVATE);
        menu_image.setImageURI(Uri.parse(preferences.getString("save_main_image", "")));
        image_taken_date.setText(preferences.getString("save_image_date", ""));
        image_taken_location.setText(preferences.getString("save_image_location",""));

    }

    public void initialize_view() {
        menu_list = findViewById(R.id.menu_list);
        menu_statistics = findViewById(R.id.menu_statistics);
        menu_setting = findViewById(R.id.menu_setting);
        menu_game = findViewById(R.id.menu_game);
        menu_asmr = findViewById(R.id.menu_ASMR);
        menu_image = findViewById(R.id.menu_image);
        image_taken_date = findViewById(R.id.image_taken_date);
        image_taken_location = findViewById(R.id.image_taken_location);

    }

}

