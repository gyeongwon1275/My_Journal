package com.example.my_journal.Journal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.my_journal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// implements 리사이클러뷰 클릭이벤트
// implements 리사이클러뷰 필터
public class Main_journal_activitiy extends AppCompatActivity implements Journal_adapter.on_journal_listener {


    private static final String MAIN_JOURNAL = "main_journal_activity";
    private static final int JOURNAL = 2;
    private static final int MODIFY_JOURNAL = 47;
    private static final String ADD_SAVE = "add_save";
    private static final String PAUSE = "pause_save";
    private static final String POS = "position";
    SearchView searchView;

    String journal_text;
    String journal_date;
    String journal_mood;
    String journal_image_uri;
    String journal_time;
    String json_array;
    String save_json;
    String load_json;
    String modify_save;
    String pause_json_obj;
    String pause_save;

    int modify_position;


    LottieAnimationView add_journal;
    TextView item_journal_date, item_journal_mood;
    RecyclerView journal_list;
    RecyclerView.LayoutManager layoutManager;
    List<Journal_item> items_journal;
    Journal_adapter journal_adapter;
    Journal_item journal_item;
    ImageView item_journal_image;



    ArrayList<String> json_list = new ArrayList<>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_journal);

        pause_save = "";




        Log.i(PAUSE, "pause_save : onCreate1" + pause_save);

        // 기존 onpause 에서 저장한 데이터 불러오기
        preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);


        pause_save = preferences.getString(PAUSE, "");






        // 데이터가 중복되서 들어감
        Log.i(PAUSE, "pause_save : onCreate2" + pause_save);




        add_journal = findViewById(R.id.add_journal);


        item_journal_date = findViewById(R.id.item_journal_date);
        item_journal_mood = findViewById(R.id.item_journal_mood);
        item_journal_image = findViewById(R.id.item_journal_image);


        // 리사이클러뷰 객체생성하고 레이아웃에 있는 리사이클러뷰와 연결
        journal_list = findViewById(R.id.recyclerview_journal);

        // 리사이클러뷰 레이아웃매니저 설정

        layoutManager = new LinearLayoutManager(this);

        journal_list.setLayoutManager(layoutManager);

        // 어댑터에 들어갈 아이템 리스트 생성

        items_journal = new ArrayList<>();

        // 어댑터에 아이템 데이터 전달

        journal_adapter = new Journal_adapter(items_journal, this, this);

        // 리사이클러뷰 객체에 어댑터 적용
        journal_list.setAdapter(journal_adapter);


        // 추가버튼 ( 날짜와 만족도를 입력하여 추가할 수 있다. )

        // 다이얼로그로 추가시 resume 에서 작업이 지속되며 List 에 데이터가 쌓인다.

        // 안되는 이유
        // 일기쓰기 액티비티 -> 일기목록 액티비티로 전환시 새로운 일기목록 액티비티가 생긴다.
        // 기존의 일기목록 액티비티에 데이터가 전달되지 않고 새로운 일기목록 액티비티를 생성 후 데이터를 전달한다.
        // 때문에 기존 일기목록 List 에 데이터가 축적되지 않는다.

        // 다이얼로그에서는 resume 상태에서 동일한 액티비티에서 데이터를 추가하기 때문에 데이터가 축적될 수 있다.
        // 인텐트 전환 에서는 새로운 액티비티에 데이터를 전달하여 데이터가 축적되지 않는다.


        // 원래 있었던 액티비티에만 데이터가 쌓일 수 있도록 변경

        //////////

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

                journal_adapter.notifyDataSetChanged();


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        add_journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Main_journal_activitiy.this, Write_journal_activitiy.class);

                startActivityForResult(intent, JOURNAL);

            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();

// 추가,수정,삭제시 저장

// 1> 데이터가 중복되지 않도록 Preference 초기화

        preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.commit();

        pause_save = "";


///////////////////////////////////////////////////////////////////////////////


//
//         수정으로 인해 List 내의 데이터가 변화함
//         List 내의 데이터를 Preference 에 저장하여 수정내용을 반영하고자 함
//         반영된 내용은 oncreate 때 액티비티에 적용
//
//
//         현재 List 에 들어가 있는 데이터를 Json 형태로 바꾸어 저장
//
//         List 데이터 끄집어 내기 -> 항목들이 뭉쳐진 상태
//         데이터에서 항목별로 추출하기 -> JSON obj 로 변환하기
//         Json Array 에 누적저장하기


        Journal_item journal_item;


        // onpause 시 아이템 List 에 들어있는 데이터를 JSON OBJ 에 집어넣고 String 으로 변경
        // String JSON OBJ 를 ArrayList 에 집어넣음
        ArrayList<String> pause_json_array = new ArrayList<>();


        for (int i = 0; i < items_journal.size(); i++) {

            try {
                journal_item = items_journal.get(i);

                String text = journal_item.getText_journal();
                String date = journal_item.getDate_journal();
                String mood = journal_item.getRating_journal();
                String image = journal_item.getImage_journal().toString();
                String time = journal_item.getTime_journal();

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("journal_text", text);
                jsonObject.put("journal_date", date);
                jsonObject.put("journal_mood", mood);
                jsonObject.put("journal_image", image);
                jsonObject.put("journal_time", time);


                String pause_json_obj = jsonObject.toString();
                pause_json_array.add(i, pause_json_obj);
                Log.i(PAUSE, "pause_json_obj : onPause()" + pause_json_obj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


// ArrayList 에 들어있는 String JSON OBJ 를 JSON OBJ 고 변경하고 JSON Array 에 누적하여 집어넣음

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < pause_json_array.size(); i++) {

            try {

//         JSON OBJ String 값을 JSON OBJ 로 변환
//         오래전에 저장한 것부터 Array 에 저장
//
                JSONObject jsonObject = new JSONObject(pause_json_array.get(i));

//         JSONArray에 JSON OBJ 를 누적하여 저장


                jsonArray.put(jsonObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.i(PAUSE, "pause_json_Array : jsonArray.toString()" + jsonArray.toString());


        // JSON Array String 값으로 변경
        String save_json_array = "";
        save_json_array = jsonArray.toString();


        // JSON Array String 값을 Preference 에 저장

        Log.i(PAUSE, "pause_save : onPause()22" + pause_save);
        preferences = getSharedPreferences(PAUSE, MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.putString(PAUSE, save_json_array);
        editor.commit();


    }


    // TODO 메인페이지에서 바로 쓰기로 갔을때에도 아이템 변경되도록 설정
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {


            if (requestCode == JOURNAL) {


                journal_text = data.getStringExtra("journal_text");
                journal_date = data.getStringExtra("journal_date");
                journal_mood = data.getStringExtra("journal_mood");
                journal_image_uri = data.getStringExtra("journal_image");
                journal_time = data.getStringExtra("journal_time");

                journal_item = new Journal_item(journal_text, journal_date, journal_mood, Uri.parse(journal_image_uri),journal_time);
                items_journal.add(0, journal_item);
                journal_adapter.notifyDataSetChanged();


                String journal_json_array = data.getStringExtra("json_array");
                String json_obj = data.getStringExtra("json_obj");


                // 어댑터에 들어갈 아이템 리스트 생성

                // 어댑터에 아이템 데이터 전달


                // 리사이클러뷰 객체에 어댑터 적용

// TODO journal_time modify 에서 받아오는 걸로 수정
            } else if (requestCode == MODIFY_JOURNAL) {

                journal_text = data.getStringExtra("modify_text");
                journal_date = data.getStringExtra("modify_date");
                journal_mood = data.getStringExtra("modify_mood");
                journal_image_uri = data.getStringExtra("modify_image");

                journal_item = new Journal_item(journal_text, journal_date, journal_mood, Uri.parse(journal_image_uri),journal_time);
                items_journal.set(modify_position, journal_item);
                journal_adapter.notifyDataSetChanged();


            }

        }

    }


    @Override
    public void on_journal_click(int position) {


        Log.i(POS,"before"+modify_position+"");
        modify_position = 0;

        modify_position = position;
        Log.i(POS,"after"+modify_position+"");
        Intent intent = new Intent(this, Modify_journal_activitiy.class);
        intent.putExtra("modify", items_journal.get(position));
        startActivityForResult(intent, MODIFY_JOURNAL);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main_journal_search, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_journal_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!searchView.isIconified())

                {
                    searchView.setIconified(true);

                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                final List<Journal_item> filterd_list = filter(items_journal,newText);


                journal_adapter.set_filter(filterd_list);

                return true;
            }
        });

        return true;
    }





private List<Journal_item> filter (List<Journal_item> filter_item,String query)

{

  query = query.toLowerCase();

  final List<Journal_item> filterd_list = new ArrayList<>();


for(Journal_item journal_item : filter_item)

{
        final String text = journal_item.getText_journal().toLowerCase().trim();

        if(text.startsWith(query))

        {
            filterd_list.add(journal_item);



        }


}

return filterd_list;


}




}
