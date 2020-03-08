package com.example.my_journal.Hour_record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.my_journal.Journal.Journal_item;
import com.example.my_journal.R;

import java.util.ArrayList;

public class Hour_record_adapter extends BaseAdapter {


    // extends BaseAdapter 한 후 getCount getItem  getItemId getView method 를 implemet 한다.
    // 리스트뷰가 위치하는 액티비티 클래스에서 어댑터로 데이터를 받을 List 를 생성 and 생성자 생성한다.

    private ArrayList<Journal_item> hour_record_items = new ArrayList<>();




    public Hour_record_adapter(ArrayList<Journal_item> hour_record_items) {
        this.hour_record_items = hour_record_items;

    }


//    constructor
//    객체를 생성할 때 객체변수를 초기화 하기 위해 사용되는 메서드
//    A constructor in Java is a special method that is used to initialize objects.
//    The constructor is called when an object of a class is created.
//    It can be used to set initial values for object attributes:
//    https://www.w3schools.com/java/java_constructors.asp



    // 리스트뷰에서 아이템을 몇개 만들지 결정하는 메서드
    // 위에서 만든 List 의 크기를 리턴한다.

    @Override
    public int getCount() {
        return hour_record_items.size();
    }



    // List 의 해당 위치의 데이터를 리턴
    @Override
    public Object getItem(int position) {
        return hour_record_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    // convertView
    // convert 동사 : 변하게 하다, 전환하다 // 명사 : 전향자
    // convertView : 전환된 View
    // 아이템 레이아웃의 View 를 리스트뷰의 레이아웃에 들어가는 View 로 변환한다.


    // LayoutInflater : XML에 미리 정의해둔 틀을 실제 메모리에 올려주는 역할
    // XML 에 정의된 Resource 를 View 객체로 반환해주는 역할
    // inflate를 사용하기 위해서는 inflater 를 얻어와야 한다.

    // inflate (@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot )
    // resource -> 리사이클러뷰의 아이템 레이아웃 / 반복적으로 만들 레이아웃
    // root -> 생성될 View 의 parent 를 명시한다.
    // attachRoot  -> 생성되는 뷰를 root 의 자식으로 만들지 여부
    // 아이템 레이아웃에서 만든 것을 view 객체에 넣음





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 아이템 클래스 객체화 하여 해당위치의 객체정보를 전달



        if (convertView == null) {  // convertView는 스크롤이 넘어가서 안 보이게 될 때 재활용되는 view다. 그래서 빈 값일 떄는 새로 만들어야 한다.


            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal, parent, false);

            // parent -> 어댑터를 생성하는 클래스
            // 아이템 레이아웃을 그려줌


        }



        ImageView imageView_journal, item_journal_mood_image;
        TextView item_journal_date, item_journal_rating,item_journal_time;

        // 아이템 레이아웃의 요소의 참조를 받아 연결
//        TextView hour_record_time = convertView.findViewById(R.id.hour_record_item_time);
//        TextView hour_record_item_text = convertView.findViewById(R.id.hour_record_item_text);

//        ImageView imageView = convertView.findViewById(R.id.item_journal_image);

        imageView_journal = convertView.findViewById(R.id.item_journal_image);
        item_journal_date = convertView.findViewById(R.id.item_journal_date);
        item_journal_rating = convertView.findViewById(R.id.item_journal_mood);
        item_journal_mood_image = convertView.findViewById(R.id.item_journal_mood_image);
        item_journal_time = convertView.findViewById(R.id.item_journal_time);



        // 아이템 클래스 객체 생성
        // 리스트뷰가 위치하는 액티비티 클래스에서 넘어온 데이터를 받아서 넘겨줌

//        Hour_record_item hour_record_item = hour_record_items.get(position);
        Journal_item journal_item = hour_record_items.get(position);

        String mood = journal_item.getRating_journal();
        if (mood.equals("1")) {
//            holder.item_journal_rating.setText("매우나쁨");
            item_journal_mood_image.setImageResource(R.drawable.mood_1);

        } else if (mood.equals("2")) {
//            holder.item_journal_rating.setText("나쁨");
            item_journal_mood_image.setImageResource(R.drawable.mood_2);
        } else if (mood.equals("3")) {
//            holder.item_journal_rating.setText("보통");
            item_journal_mood_image.setImageResource(R.drawable.mood_3);
        } else if (mood.equals("4")) {
//            holder.item_journal_rating.setText("좋음");
            item_journal_mood_image.setImageResource(R.drawable.mood_4);
        } else {
//            holder.item_journal_rating.setText("매우좋음");
            item_journal_mood_image.setImageResource(R.drawable.mood_5);
        }


        // view 요소에 데이터를 넘겨서 보여지게 해줌



        Glide.with(convertView.getContext()).load(journal_item.getImage_journal()).into(imageView_journal);

        item_journal_date.setText(journal_item.getDate_journal());
        item_journal_rating.setText(journal_item.getRating_journal());

        item_journal_time.setText(journal_item.getTime_journal());


        // 리스트뷰에 뷰를 넘겨줌

        return convertView;


    }


}
