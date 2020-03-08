package com.example.my_journal.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.my_journal.Menu_activity;
import com.example.my_journal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Game_mole_result_activity extends AppCompatActivity {


    private static final String SCORE = "score";
    TextView sub_result;
    Button sub_retry;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Game_record_item> game_record;
    Game_record_adapter game_record_adapter;
    TextView game_record_time,game_record_point,text_game_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mole_result);

        game_record = new ArrayList<>();


        game_record.add(0, new Game_record_item(getIntent().getStringExtra("time"), getIntent().getIntExtra("score", -1) + ""));

        Log.i(SCORE,"방금"+getIntent().getStringExtra("time"));


        set_recyclerview();







        sub_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_mole_result_activity.this, Menu_activity.class);
                startActivity(intent);
                finish();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        load_game_record();


    }

    public void load_game_record ()


    {

        String data_game_record = "";

        preferences = getSharedPreferences(SCORE, MODE_PRIVATE);
        data_game_record = preferences.getString(SCORE, "");

        try {

            // Preference 에서 받은 JSON Array String data 를 JSONArray 에 넣는다.

            // 수정 -> modify_save
            // 추가 -> save_json

            JSONArray load_journal_json = new JSONArray(data_game_record);


            for (int i = 0; i < load_journal_json.length(); i++) {
                JSONObject jsonObject = load_journal_json.getJSONObject(i);


                String time = jsonObject.getString("game_time");
                String point = jsonObject.getString("game_point");

                Game_record_item game_record_item = new Game_record_item(time, point);


                game_record.add(i+1, game_record_item);

                game_record_adapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(SCORE,"저장"+getIntent().getStringExtra("time"));

    }


    public void set_recyclerview()

    {

        recyclerView = findViewById(R.id.list_game_result);
        game_record_time = findViewById(R.id.game_record_time);
        game_record_point = findViewById(R.id.game_record_point);
        text_game_record = findViewById(R.id.text_game_record);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        game_record_adapter = new Game_record_adapter(game_record);
        game_record_adapter.notifyDataSetChanged();
        recyclerView.setAdapter(game_record_adapter);

        sub_retry = findViewById(R.id.sub_retry);
    }

    @Override
    protected void onPause() {
        super.onPause();


        preferences = getSharedPreferences(SCORE, MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.commit();

        Game_record_item game_record_item;

        ArrayList<String> result_array = new ArrayList<>();

        for (int i = 0; i < game_record.size(); i++) {

            try {
                game_record_item = game_record.get(i);

                String time = game_record_item.getGame_time();
                String point = game_record_item.getGame_point();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("game_time", time);
                jsonObject.put("game_point", point);


                String json_obj = jsonObject.toString();
                result_array.add(i, json_obj);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result_array.size(); i++) {

            try {

//         JSON OBJ String 값을 JSON OBJ 로 변환
//         오래전에 저장한 것부터 Array 에 저장
//
                JSONObject jsonObject = new JSONObject(result_array.get(i));

//         JSONArray에 JSON OBJ 를 누적하여 저장


                jsonArray.put(jsonObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String save_json_array = "";
        save_json_array = jsonArray.toString();

        preferences = getSharedPreferences(SCORE, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(SCORE, save_json_array);
        editor.commit();


    }
}
