package com.example.my_journal.Habit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_journal.Habit_coupon.Habit_coupon_activitiy;
import com.example.my_journal.R;

import java.util.ArrayList;
import java.util.List;

public class Habit_activitiy extends AppCompatActivity  {


    private static final int GALLERY_REQUEST_CODE = 5;
    TextView textView_habit, item_habit_goal;
    Button button_insert;
    ImageView item_habit_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        RecyclerView habit_list = findViewById(R.id.recyclerview_habit);
        textView_habit = findViewById(R.id.textView_habit);
        item_habit_goal = findViewById(R.id.item_habit_goal);
        button_insert = findViewById(R.id.button_insert);
        item_habit_image = findViewById(R.id.item_habit_image);

        // 리사이클러뷰 하나로 레이아웃 매니저를 통해 grid , linear 등 다양한 형태로 표현할 수 있다.
        // 리니어레이아웃 매니저 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        // recyclerView에 레이아웃 매니저 적용
        habit_list.setLayoutManager(layoutManager);



        final List<Habit_item> habit_list_data = new ArrayList<>();
        // 아이템 클래스로 보낼 리스트

        final Habit_adapter adapter = new Habit_adapter(habit_list_data,this); // 리사이클러 뷰에 들어갈 데이터 만든거 어댑터로 넘김
        // list 에 입력받은 데이터를 어댑터로 넘김
        habit_list.setAdapter(adapter);


        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                AlertDialog.Builder builder = new AlertDialog.Builder(Habit_activitiy.this);
                View view = LayoutInflater.from(Habit_activitiy.this)
                        .inflate(R.layout.edit_box, null, false);
                builder.setView(view);

                Button button_submit = view.findViewById(R.id.button_submit);
                ImageButton habit_imageButton = view.findViewById(R.id.habit_imageButton);
                final EditText edit_habit =  view.findViewById(R.id.edit_first);
                final EditText edit_habit_number = view.findViewById(R.id.edit_second);


                // final 을 붙이는 이유 //
                // final 로 변수나 list 만들면 생성과 동시에 상수화 하여 사라지지 않고 지속해서 다른 메서드가 사용할 수 있다.
                // ex>Oncreate에 선언한 변수들은 지역변수로서 Oncreate function이 종료하면 사라지게 된다.
                // 그 경우 Onclick 같이 click 할 때 마다 불리는 함수에서는 지역변수를 접근할 수가 없다




                final AlertDialog dialog = builder.create();

                button_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String habit_name = edit_habit.getText().toString();
                        String habit_number = edit_habit_number.getText().toString();
                        final String number_habit = habit_number;
                        Habit_item recyclerview_itemHabit = new Habit_item(habit_name, habit_number,null);
                        habit_list_data.add(0, recyclerview_itemHabit);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                habit_imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(); // 암시적 인텐트라 인텐트가 어디에서 어디로 갈지 선언하지 않았음
                        intent.setType("image/*");
                        String[] mimeTypes = {"image/jpeg", "image/png"}; // 이미지의 파일 확장자 설정
                        intent.setAction(Intent.ACTION_PICK); // intent 의 action 만 설정 하여 다른 앱에 있는 액티비티가 이를 수행하도록 함

                        if (intent.resolveActivity(getPackageManager()) != null) {



                            // MIME = Multipurpose Internet Mail Extensions : 파일변환
                            // 기존 인코딩 방식을 보완하여 만들어진 인코딩방식
                            // 데이터가 어떤형식의 데이터인지를 알려준다.
                            intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);

                            // Launching the Intent
                            startActivityForResult(intent,GALLERY_REQUEST_CODE);

                        }




                    }
                });



                dialog.show();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    //URI : Uniform Resource Identifier ( 통합자원식별자 )
                    List<Habit_item> habit_list_data = new ArrayList<>();


                    Habit_item recyclerview_itemHabit = new Habit_item(null, null,selectedImage);
//                    item_habit_image.setImageURI(selected_image);
                    habit_list_data.add(0, recyclerview_itemHabit);

                    break; }

    }


    public void move_coupon(View view) {

        item_habit_goal = findViewById(R.id.item_habit_goal);

        Intent intent = new Intent(this, Habit_coupon_activitiy.class);
        intent.putExtra("coupon_number",item_habit_goal.getText().toString());
        startActivity(intent);

    }
}
