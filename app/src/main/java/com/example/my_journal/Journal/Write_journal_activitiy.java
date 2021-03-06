package com.example.my_journal.Journal;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.my_journal.Assess.Assess_journal_activitiy;
import com.example.my_journal.Habit.Habit_activitiy;
import com.example.my_journal.Hour_record.Hour_record_activitiy;
import com.example.my_journal.Map_activity;
import com.example.my_journal.R;
import com.example.my_journal.Setting_activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Write_journal_activitiy extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NumberPicker.OnValueChangeListener {

    private static final int request_image = 0;
    public static final String JOURNAL_DATA = "journal_data";
    public static final String TODAY_RATING = "today_rating";
    private static final int GALLERY_REQUEST_CODE = 7;
    private static final int JOURNAL = 2;
    private static final String WRITE_JOURNAL = "write_journal_activity";
    private static final int MUSIC_REQUEST_CODE = 867;
    private static final String TIME = "time";
    private static final int MY_CAL_REQ = 23;


    MediaPlayer mediaPlayer = new MediaPlayer();
    ArrayList<String> mood_expression = new ArrayList<>();
    // 다시 재생을 위한 위치를 지정하는 변수
    int music_position = 0;
    boolean music_play_condition;

    private int rating = 0;

    ImageButton Button_number_pick;
    LottieAnimationView calendar, button_image, button_map;
    EditText edit_journal;
    TextView date_today, rating_number, mood_text;

    Uri selected_image, selected_audio;


    // 날짜 설정

    ImageView journal_image, music_play;
    String save_journal_data;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final int choose_day = 9;
    Date date_data = new Date();
    SimpleDateFormat today_input = new SimpleDateFormat("yyyy-MM-dd");
    private String LOG_music = "music";


    private Boolean is_running = true;
    private Handler handler;
    private Thread thread = null;
    String time_result;


    //////seekbar/////

    private TextView play_time_asmr;
    private TextView total_time_asmr;
    private SeekBar seekbar_music;
    private int current_time = 0, end_time = 0;
    private boolean play_condition = true;
    private Handler handler_asmr = new Handler();
    private final static String time = "time";

    //////seekbar/////

    Cursor cursor;

    // Bundle savedInstanceState -> 상태 저장된 번들 데이터가 들어온다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_journal);

        ///////////////////////////////////////////////////////


        time_result = "";
        handler = new Handler() {

            // handler 받는 쪽

            // 메세지 받아서 어떻게 handle(처리) 할건지
            //
            @Override
            public void handleMessage(Message msg) {
                // 시간 format :
                int second = (msg.arg1 / 100) % 60;
                int minute = (msg.arg1 / 100) / 60;

                // %03d = 3자리수 / %02d = 2자리수
                time_result = String.format("%02d 분 : %02d 초", minute, second);

            }
        };

        class Time_thread implements Runnable {
            @Override
            public void run() {
                int i = 0;

                while (true) {
                    while (is_running) { //일시정지를 누르면 멈추도록
                        Message msg = handler.obtainMessage(); // 얻다 메세지
                        msg.arg1 = i++;
                        handler.sendMessage(msg);

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return; // 인터럽트 받을 경우 return됨
                        }
                    }
                }
            }
        }


        Time_thread time_thread = new Time_thread();

        Thread thread = new Thread(time_thread);

        thread.start();


        /////////////////////////////////////////////////////


        connect_view();


        music_play_condition = true;


        rating_number.setText("3");
//        set_mood_expression();
        set_mood();
        String today = today_input.format(date_data);
        date_today.setText(today);

        // 재생버튼 클릭시 음악파일 Uri 를 한번만 받도록 하기 위해 조건변수 설정


        // 저장된 Preferences 받음

        preferences = getSharedPreferences("write_journal", MODE_PRIVATE);

        // json 데이터를 parse 해서 각각 저장

        String write_journal_array = preferences.getString("write_journal_array", "");
        String write_journal_obj = preferences.getString("write_journal_obj", "");


        try {
            JSONArray jsonArray = new JSONArray(write_journal_array);


            for (int i = 0; i < 1; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String text = jsonObject.getString("journal_text");
//                String date = jsonObject.getString("journal_date");
                String image = jsonObject.getString("journal_image");
                String rating = jsonObject.getString("journal_mood");

                edit_journal.setText(text);
//                date_today.setText(date);
                rating_number.setText(rating);
                set_mood();
                journal_image.setImageURI(Uri.parse(image));
                selected_image = Uri.parse(image);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        rating_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number_show();
                set_mood();

            }
        });

        Button_number_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number_show();
                set_mood();

            }
        });


        date_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_date_picker();

            }
        });


        music_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // 재생을 누르면 일시정지 되지 않고 같은 Uri 의 음악파일을 새로재생한다.


                if (mediaPlayer.isPlaying() == false && music_play_condition == true) {
                    music_play.setImageResource(R.drawable.ic_pause_black_24dp);


                    try {

                        mediaPlayer.setDataSource(Write_journal_activitiy.this, selected_audio);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    play_selected_music();

                    // 버튼 클릭시 mediaPlayer.setDataSource() 한번만 실행되도록 변수를 설정함
                    music_play_condition = false;


                    Log.i(LOG_music, "play");

                } else if (mediaPlayer != null && mediaPlayer.isPlaying() == true) {
                    Log.i(LOG_music, "pause");
                    music_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    music_position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                } else if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    Log.i(LOG_music, "resume");
                    music_play.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.seekTo(music_position);
                    play_selected_music();
                }


            }
        });

        seekbar_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {


//                isPlaying = true;
                music_position = seekBar.getProgress(); // 사용자가 움직여놓은 위치
                mediaPlayer.seekTo(music_position);
                play_selected_music();

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getMax() == progress) {

                    mediaPlayer.stop();
                }
            }
        });


        journal_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Write_journal_activitiy.this);
                View view = LayoutInflater.from(Write_journal_activitiy.this)
                        .inflate(R.layout.dialog_image, null, false);
                builder.setView(view);
                ImageView image_dialog = view.findViewById(R.id.image_dialog);

                image_dialog.setImageURI(selected_image);

                final AlertDialog dialog = builder.create();


                image_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Write_journal_activitiy.this, Map_activity.class);
                startActivity(intent);


            }
        });


    }


    public void connect_view() {

        date_today = findViewById(R.id.date_today);
        calendar = findViewById(R.id.button_share);
        edit_journal = findViewById(R.id.edit_journal);
        journal_image = findViewById(R.id.journal_image);
        button_image = findViewById(R.id.button_image);
        rating_number = findViewById(R.id.rating_number);
        mood_text = findViewById(R.id.mood_text);
        Button_number_pick = findViewById(R.id.Button_number_pick);

        music_play = findViewById(R.id.music_play);
        play_time_asmr = findViewById(R.id.play_time_asmr);
        total_time_asmr = findViewById(R.id.total_time_asmr);
        seekbar_music = findViewById(R.id.seekBar_music);
        music_play.setVisibility(View.GONE);

        button_map = findViewById(R.id.button_map);
    }

    public void number_show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Write_journal_activitiy.this);
        View view = LayoutInflater.from(Write_journal_activitiy.this)
                .inflate(R.layout.number_picker, null, false);
        builder.setView(view);

        final NumberPicker numberPicker = view.findViewById(R.id.number_picker);
        Button number_set = view.findViewById(R.id.number_set);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(this);

        final AlertDialog dialog = builder.create();


        number_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = numberPicker.getValue() + "";

                rating_number.setText(num);

                String mood_num = rating_number.getText().toString();

                if (mood_num.equals("1")) {
                    mood_text.setText("매우나쁨");
                } else if (mood_num.equals("2")) {
                    mood_text.setText("나쁨");
                } else if (mood_num.equals("3")) {
                    mood_text.setText("보통");
                } else if (mood_num.equals("4")) {
                    mood_text.setText("좋음");
                } else if (mood_num.equals("5")) {

                    mood_text.setText("매우좋음");
                }


                dialog.dismiss();
            }
        });


        dialog.show();


    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


    }

    private void show_date_picker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,

                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );

        datePickerDialog.show();


    }


    // seekbar, playtime update 메서드
    public void play_selected_music() {

        mediaPlayer.start();

        end_time = mediaPlayer.getDuration(); // 음악 전체 시간

        current_time = mediaPlayer.getCurrentPosition(); // 음악현재 재생위치

        if (play_condition) {
            seekbar_music.setMax(end_time); // seekbar 의 최대치를 음악 전체 시간으로 설정
            play_condition = false; // 최대치 설정을 한번만 하도록
        }

        // 전체재생시간 문자열 설정
        total_time_asmr.setText(
                // 음악의 분표시
                String.format(" / %02d : %02d", TimeUnit.MILLISECONDS.toMinutes(end_time),
                        // 전체 음악시간의 초 표시 ex> 2분 6초 -> 126초
                        // 6초만 표현하기 위해 126초 -120초 한것
                        TimeUnit.MILLISECONDS.toSeconds(end_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end_time))));

        Log.i(time, "TimeUnit.MILLISECONDS.toMinutes(end_time) = " + TimeUnit.MILLISECONDS.toMinutes(end_time));
        Log.i(time, "TimeUnit.MILLISECONDS.toSeconds(end_time) = " + TimeUnit.MILLISECONDS.toSeconds(end_time));
        Log.i(time, "TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(end_time)) = " + TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(end_time)));


        // 현재재생시간 문자열 설정
        play_time_asmr.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(current_time),
                TimeUnit.MILLISECONDS.toSeconds(current_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current_time))));


        seekbar_music.setProgress(current_time);


        handler_asmr.post(update_music_time);


    }

    // seekbar, playtime update 스레드

    private Runnable update_music_time = new Runnable() {
        @Override
        public void run() {
            current_time = mediaPlayer.getCurrentPosition();

            play_time_asmr.setText(String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(current_time),
                    TimeUnit.MILLISECONDS.toSeconds(current_time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current_time))));

            seekbar_music.setProgress(current_time);

            handler_asmr.post(update_music_time);
        }
    };

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // 출력되는 실제 data 가 picker 에서 선택한 데이터와 맞지 않아 조정해줌
        if (year >= 2019) {
            month++;

            if (month > 12) {
                month = 1;
                year++;

            }

        }

        if (month > 0 && month < 10) {
            if (dayOfMonth > 0 && dayOfMonth < 10) {
                String date = year + "-" + "0" + month + "-0" + dayOfMonth;

                date_today.setText(date);

            } else {
                String date = year + "-" + "0" + month + "-" + dayOfMonth;

                date_today.setText(date);

            }

        } else {

            if (dayOfMonth > 0 && dayOfMonth < 10) {
                String date = year + "-" + month + "-0" + dayOfMonth;

                date_today.setText(date);

            } else {
                String date = year + "-" + month + "-" + dayOfMonth;

                date_today.setText(date);

            }
        }


    }


    // 일기 data 저장
    public void save_data(MenuItem item) throws JSONException {


        Toast toast = Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        // 일기 내용, 날짜, 이미지, 기분 데이터를 json 형식으로 변환

        // ex > json_journal 라는 JSON OBJ 에 아래내용이 들어간다.
        // {  "journal_text":"56789",
        // "journal_date":"2020-02-23",
        // "journal_image":"content:\/\/com.android.providers.media.documents\/document\/image%3A20827",
        // "journal_mood":"3"  }

        if (thread != null) {
            thread.interrupt();

        }

        JSONObject json_journal = new JSONObject();


        json_journal.put("journal_text", edit_journal.getText().toString());
        json_journal.put("journal_date", date_today.getText().toString());
        json_journal.put("journal_mood", rating_number.getText().toString());
        json_journal.put("journal_time", time_result);


        if (selected_image != null) {
            json_journal.put("journal_image", selected_image.toString());
        }


        // json 객체에 담긴 데이터를 Array 에 넣음

//      ex> json_journal_array 에 아래내용이 들어감
//      [  {  "journal_text":"56789",
//      "journal_date":"2020-02-23",
//      "journal_image":"content:\/\/com.android.providers.media.documents\/document\/image%3A20827",
//      "journal_mood":""  }   ]

        JSONArray json_journal_array = new JSONArray();

        json_journal_array.put(json_journal);

        // Preferences에 저장 위해 JSON ARRAY 를 String 로 변환


        // JSON OBJ / Array 를 String 로 변환해서 변수에 저장
        String save_json_array = json_journal_array.toString();
        String save_json_obj = json_journal.toString();


        // Preferences 에 저장 /  XML 파일명 : journal_json   /  key : journal

        preferences = getSharedPreferences("write_journal", MODE_PRIVATE);

        editor = preferences.edit();
        editor.putString("time",time_result);
        editor.putString("json_array", save_json_array);
        editor.putString("json_obj", save_json_obj);

        // Uri 값이 있을 때 preferences 에 저장
        if (selected_image != null) {
            editor.putString("write_journal_image", selected_image.toString());
        }

        editor.commit();

        // 일기내용을 인텐트로 일기목록 RecyclerView에 전달
        Intent intent = new Intent();
        intent.putExtra("journal_text", edit_journal.getText().toString());
        intent.putExtra("journal_date", date_today.getText().toString());
        intent.putExtra("journal_mood", rating_number.getText().toString());
        intent.putExtra("journal_time", time_result);
        Log.i(TIME, "" + time_result);
        // // Uri 값이 있을 때 인텐트 전달
        if (selected_image != null) {
            intent.putExtra("journal_image", selected_image.toString());
        } else {
            preferences = getSharedPreferences("write_journal", MODE_PRIVATE);


            intent.putExtra("journal_image", preferences.getString("write_journal_image", ""));

        }

        intent.putExtra("json_array", save_json_array);
        intent.putExtra("json_obj", save_json_obj);
        setResult(Main_journal_activitiy.RESULT_OK, intent);

        time_result = "";


        Intent intent_save = new Intent(this, Save_animation_activity.class);
        startActivity(intent_save);
        finish();


    }

    // onPause() 에 저장기능을 적용한 이유
    // 데이터 입력하다가 다른 액티비티로 전환하는 경우, 기기의 전체 액티비티가 강제종료될 경우를 대비하고자 함
    // onstop 부터 killable 이기 떄문이다.
    // oncreate() 에서 저장한 내용을 다시 불러오게 함
    // This callback is mostly used for saving any persistent state the activity is editing
    // making sure nothing is lost if there are not enough resources to start the new activity without first killing this one.

    @Override
    protected void onPause() {
        super.onPause();
        pause_audio();


    }


    // implicit 인텐트로 부른 사진을 액티비티에서 받는다.

    public void bring_image(View view) {

        Intent intent = new Intent(); // 암시적 인텐트라 인텐트가 어디에서 어디로 갈지 선언하지 않았음
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png", "image/gif"};  // 이미지의 파일 확장자 설정
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT); // intent 의 action 만 설정 하여 다른 앱에 있는 액티비티가 이를 수행하도록 함

//        Use ACTION_OPEN_DOCUMENT if you want your app to have long term,
//        persistent access to documents owned by a document provider.
//        An example would be a photo-editing app that lets users edit images stored in a document provider.

//        java.lang.SecurityException: Permission Denial: reading com.android.providers.media.MediaDocumentsProvider uri cont
        // https://stackoverflow.com/questions/40438537/permission-denied-opening-provider-com-google-android-apps-photos-contentprovi

        // PICK -> OPEN_DOCUMENT 변경으로 해결
        if (intent.resolveActivity(getPackageManager()) != null) {


            // MIME = Multipurpose Internet Mail Extensions : 파일변환
            // 기존 인코딩 방식을 보완하여 만들어진 인코딩방식
            // 데이터가 어떤형식의 데이터인지를 알려준다.
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);


            startActivityForResult(intent, GALLERY_REQUEST_CODE);

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            if (requestCode == GALLERY_REQUEST_CODE) {

                //data.getData returns the content URI for the selected Image
                selected_image = data.getData();
                // URI : Uniform Resource Identifier ( 통합자원식별자 )

//                journal_image.setImageURI(selected_image);

                Glide.with(this).load(selected_image).into(journal_image);

            } else if (requestCode == MUSIC_REQUEST_CODE) {

                selected_audio = data.getData();
                music_play.setVisibility(View.VISIBLE);
                music_play.setImageResource(R.drawable.ic_play_arrow_black_24dp);

            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_journal, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // item -> 터치된 메뉴가 넘어온다.
        int id = item.getItemId();

        if (id == R.id.menu_search) {

            Intent intent = new Intent(this, Hour_record_activitiy.class);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }


        } else if (id == R.id.menu_setting) {

            Intent intent = new Intent(this, Setting_activity.class);


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }


        } else if (id == R.id.menu_assessment) {

            Intent intent = new Intent(this, Assess_journal_activitiy.class);

            intent.putExtra("rating", "4");


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }


        } else if (id == R.id.menu_habit) {
            Intent intent = new Intent(this, Habit_activitiy.class);


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        } else if (id == R.id.menu_hour_report) {
            Intent intent = new Intent(this, Hour_record_activitiy.class);


            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }


        return super.onOptionsItemSelected(item);

    }


    // 생명주기로 인해 config change 발생하면 destroy() 됬다가 다시 create 되어 기존 액티비티의 data 가 사라짐
    // 화면회전시 호출되어 상태를 저장
    // Bundle 에 data 를 담는다.
    // oncreate 에서 Bundle savedInstanceState 를 통해 복원한다.

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(JOURNAL_DATA, save_journal_data);
        outState.putInt(TODAY_RATING, rating);

        super.onSaveInstanceState(outState);
    }


    public void put_music(View view) {


        Intent intent = new Intent(); // 암시적 인텐트라 인텐트가 어디에서 어디로 갈지 선언하지 않았음

        intent.setAction(Intent.ACTION_OPEN_DOCUMENT); // content 를 받는 액션
        intent.setType("audio/*"); // 데이터 타입설정

        if (intent.resolveActivity(getPackageManager()) != null) {


            startActivityForResult(intent, MUSIC_REQUEST_CODE);

        }


    }

    public void share_journal(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);

        String send_journal = edit_journal.getText().toString();

        intent.setData(Uri.parse("mailto:"));
        intent.setType("Hour_record_activitiy/plain");

        // TODO setData? / setType

        intent.putExtra(Intent.EXTRA_TEXT, send_journal);

        Intent chooser = Intent.createChooser(intent, send_journal);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }


    }


    public void pick_number(View view) {

        number_show();
    }

    public void play_music() {


        if (!mediaPlayer.isPlaying() && music_position == 0) {

            Log.i("music", "play");

            try {
                mediaPlayer.setDataSource(this, selected_audio);


            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }


            mediaPlayer.start();
        }
    }


    public void pause_audio() {


        if (mediaPlayer != null) {
            music_position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();

        }

    }


    public void resume_audio() {

        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(music_position);
            mediaPlayer.start();
        }
    }


    public void stop_audio() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            music_position = 0;
        }
    }


    public void set_mood() {


        if (rating_number.getText().toString().equals("1")) {
            mood_text.setText("매우나쁨");

        } else if (rating_number.getText().toString().equals("2")) {
            mood_text.setText("나쁨");

        } else if (rating_number.getText().toString().equals("3")) {
            mood_text.setText("보통");

        } else if (rating_number.getText().toString().equals("4")) {
            mood_text.setText("좋음");

        } else if (rating_number.getText().toString().equals("5")) {
            mood_text.setText("매우좋음");

        }

        if (mood_text.getText().toString().equals("매우나쁨")) {
            rating_number.setText("1");

        } else if (mood_text.getText().toString().equals("나쁨")) {
            rating_number.setText("2");

        } else if (mood_text.getText().toString().equals("보통")) {
            rating_number.setText("3");

        } else if (mood_text.getText().toString().equals("좋음")) {
            rating_number.setText("4");

        } else if (mood_text.getText().toString().equals("매우좋음")) {
            rating_number.setText("5");
        }

    }


}



