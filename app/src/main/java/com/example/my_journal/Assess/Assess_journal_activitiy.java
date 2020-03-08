package com.example.my_journal.Assess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.my_journal.Journal.Journal_item;
import com.example.my_journal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Assess_journal_activitiy extends AppCompatActivity {


    RecyclerView assess_view;
    List<Assess_journal_item> items_journal;
    String pause_save;
    private static final String PAUSE = "pause_save";
    Assess_journal_adapter assess_journal_adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess_journal);


        Intent intent = getIntent();

        pause_save = "";

        Log.i(PAUSE, "pause_save : onCreate1" + pause_save);

        preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        pause_save = preferences.getString(PAUSE, "");


        // 리사이클러뷰 객체 생성 , 레이아웃에 만든 view 와 연결

        assess_view = findViewById(R.id.recyclerview_assess);



        // 리사이클러뷰 레이아웃매니저 설정

       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);



        assess_view.setLayoutManager(layoutManager);

        // 어댑터에 들어갈 아이템 리스트 생성

         items_journal = new ArrayList<>();

        // 어댑터에 아이템 데이터 전달

        assess_journal_adapter = new Assess_journal_adapter(items_journal);

        // 리사이클러뷰 객체에 어댑터 적용
        assess_view.setAdapter(assess_journal_adapter);

        try {
            // Preference 에서 받은 JSON Array String data 를 JSONArray 에 넣는다.

            // 수정 -> modify_save
            // 추가 -> save_json

            JSONArray load_journal_json = new JSONArray(pause_save);


            for (int i = 0; i < load_journal_json.length(); i++) {
                JSONObject jsonObject = load_journal_json.getJSONObject(i);



                String date = jsonObject.getString("journal_date");
                String mood = jsonObject.getString("journal_mood");


                Assess_journal_item journal_item = new Assess_journal_item(date, mood);
                items_journal.add(i, journal_item);

                assess_journal_adapter.notifyDataSetChanged();


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }





    }



}
