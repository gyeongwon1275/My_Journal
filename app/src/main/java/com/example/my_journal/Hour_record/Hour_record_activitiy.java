package com.example.my_journal.Hour_record;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.my_journal.Journal.Journal_item;
import com.example.my_journal.Journal.Write_journal_activitiy;
import com.example.my_journal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Hour_record_activitiy extends AppCompatActivity {


    private static final String TAG = "touch";
    Date date = new Date();
    SimpleDateFormat input_date = new SimpleDateFormat("yyyy-MM-dd");
    String date_text = input_date.format(date);
    private static final String PAUSE = "pause_save";

    ImageButton add_journal;
    TextView main_rating_number, journal_title_date, hour_record_item_text, hour_record_item_time;
    EditText password;
    Button button_add, button_modify, button_delete;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String pause_save;
    /////////////////////

    TextView hour_record_time;
    private static Handler mHandler;

    ArrayList<Journal_item> items_journal;
    ////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hour_record);





        items_journal = new ArrayList<>();


        preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        pause_save = preferences.getString(PAUSE, "");













////////////////////////handler Runnable 전달 ///////////////////////////////

//        mHandler = new Handler(); // 수신측에 핸들러 생성
//
//
//        final Runnable runnable = new Runnable() {
//            // 수신측에서 받아서 실행할 코드 작성
//
//
//            @Override
//            public void run() {
//                Calendar cal = Calendar.getInstance();
//
//                SimpleDateFormat sdf = new SimpleDateFormat("HH시 mm분 ss초");
//                String strTime = sdf.format(cal.getTime());
//
//                hour_record_time = findViewById(R.id.hour_record_time);
//                hour_record_time.setText(strTime);
//            }
//
//
//        };
//
//
//        class NewRunnable implements Runnable {
//            @Override
//            public void run() {
//                while (true) {
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    // runnable 보내는 클래스 작성
//
//                    mHandler.post(runnable);
//                }
//            }
//        }


        // runnable 보내는 클래스 객체 생성

//        NewRunnable nr = new NewRunnable();
//        Thread t = new Thread(nr);
//        t.start();

////////////////////////handler Runnable 전달 ///////////////////////////////


        // adapter 로 전달할 리스트 생성
//        final ArrayList<Hour_record_item> hour_record_items = new ArrayList<>();


        // 어댑터 생성 리스트 인자로 전달
        Hour_record_adapter hour_record_adapter = new Hour_record_adapter(items_journal);

        // Listview 객체생성 후 레이아웃의 Listview 와 연결
        ListView listview_hour_record = findViewById(R.id.listview_hour_record);

        // Listview 객체에 어댑터를 연결
        listview_hour_record.setAdapter(hour_record_adapter);


        try {

            // Preference 에서 받은 JSON Array String data 를 JSONArray 에 넣는다.

            // 수정 -> modify_save
            // 추가 -> save_json

            JSONArray load_journal_json = new JSONArray(pause_save);


            for (int i = 0; i < load_journal_json.length(); i++) {
                JSONObject jsonObject = load_journal_json.getJSONObject(i);


                String text = jsonObject.getString("journal_text");
                String date = jsonObject.getString("journal_date");
                String mood = jsonObject.getString("journal_mood");
                String image = jsonObject.getString("journal_image");
                String time  = jsonObject.getString("journal_time");

                Journal_item journal_item = new Journal_item(text, date, mood, Uri.parse(image),time);
                items_journal.add(i, journal_item);

                hour_record_adapter.notifyDataSetChanged();


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//        button_modify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                {
//
//                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Hour_record_activitiy.this);
//                    View view = LayoutInflater.from(Hour_record_activitiy.this)
//                            .inflate(R.layout.hour_record_edit_box, null, false);
//                    builder.setView(view);
//
//                    Button button_submit = view.findViewById(R.id.hour_record_submit);
//
//                    final EditText edit_time = view.findViewById(R.id.edit_time);
//                    final EditText edit_memo = view.findViewById(R.id.hour_record_memo);
//
//
//                    // final 을 붙이는 이유 //
//                    // 변수는 arraylist 는 생성과 동시에 상수화 하여 사라지지 않고 지속해서 다른 메서드가 사용할 수 있다.
//                    // Oncreate에 선언한 변수들은 지역변수로서 Oncreate function이 종료하면 사라지게 된다.
//                    // 그 경우 Onclick 이나 OnTouch와 같이 click 이나 Touch 할 때 마다 불리는 함수에서는 지역변수를 접근할 수가 없다
//                    // final 로 설정하여 Oncreate function이 종료해도 사라지지 않도록 할 수 있다.
//
//
//                    final androidx.appcompat.app.AlertDialog dialog = builder.create();
//
//                    button_submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            String time = edit_time.getText().toString();
//                            String memo = edit_memo.getText().toString();
//
//                            hour_record_items.set(listview_hour_record.getCheckedItemPosition(), new Hour_record_item(time, memo));
//                            hour_record_adapter.notifyDataSetChanged();
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//
//                }
//            }
//        });
//
//
//        button_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                {
//
//                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Hour_record_activitiy.this);
//                    View view = LayoutInflater.from(Hour_record_activitiy.this)
//                            .inflate(R.layout.hour_record_edit_box, null, false);
//                    builder.setView(view);
//
//                    Button button_submit = view.findViewById(R.id.hour_record_submit);
//                    final EditText edit_time = view.findViewById(R.id.edit_time);
//                    final EditText edit_memo = view.findViewById(R.id.hour_record_memo);
//
//
//                    // final 을 붙이는 이유 //
//                    // 변수는 arraylist 는 생성과 동시에 상수화 하여 사라지지 않고 지속해서 다른 메서드가 사용할 수 있다.
//                    // Oncreate에 선언한 변수들은 지역변수로서 Oncreate function이 종료하면 사라지게 된다.
//                    // 그 경우 Onclick 이나 OnTouch와 같이 click 이나 Touch 할 때 마다 불리는 함수에서는 지역변수를 접근할 수가 없다
//                    // final 로 설정하여 Oncreate function이 종료해도 사라지지 않도록 할 수 있다.
//
//
//                    final androidx.appcompat.app.AlertDialog dialog = builder.create();
//
//                    button_submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//
//                            String time = edit_time.getText().toString();
//                            String memo = edit_memo.getText().toString();
//
//                            hour_record_items.add(0, new Hour_record_item(time, memo));
//                            hour_record_adapter.notifyDataSetChanged();
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//
//                }
//
//
//            }
//        });
//
//        button_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int position = listview_hour_record.getCheckedItemPosition();
//
//                if (position != ListView.INVALID_POSITION) {
//                    Log.i(TAG, "touch_delete");
//
//                    hour_record_items.remove(position);
//                    listview_hour_record.clearChoices();
//                    hour_record_adapter.notifyDataSetChanged();
//
//
//                }
//
//
//            }
//        });

    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    public void move_write_journal(View view) {

        Intent intent = new Intent(Hour_record_activitiy.this, Write_journal_activitiy.class);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


}
