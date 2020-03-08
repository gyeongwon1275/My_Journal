package com.example.my_journal.Journal;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.example.my_journal.R;

import java.util.ArrayList;
import java.util.List;

public class Journal_adapter extends RecyclerView.Adapter<Journal_adapter.ViewHolder> {


    private List<Journal_item> journal_items;
    private List<Journal_item> total_items;
    private Context context_journal;
    private on_journal_listener m_On_journal_listener;

    public Journal_adapter(List<Journal_item> journal_items, Context context_journal, on_journal_listener on_journal_listener) {
        this.journal_items = journal_items;
        this.context_journal = context_journal;
        this.m_On_journal_listener = on_journal_listener;


    }

    //   https://blog.mindorks.com/understanding-context-in-android-application-330913e32514
    // Context : the situation within which something exists or happens, and that can help explain it
    // 존재하는 사물이나 사건안의 상황
    // 문맥 : 서로 이어져 있는 문장의 앞뒤 관계
    // 맥락 : 사물의 서로 잇닿아 있는 관계

    // context 는 자신에 대해 알려주는 신분증 역할을 한다.
    // context 는 Activity 인스턴스간에 자원을 공유하거나 설정에 접근하기 위해 사용된다.

    // Context 는 ActivityManagerService에 접근할 수 있도록 하는 통로 역할을 한다.
    // context 는 application 과 관련된 정보에 접근하거나 시스템 레벨의 함수를 호출하고자 할 때 사용된다.
    // 안드로이드 시스템에서 application 정보를 관리하는 것은 ActivityManager 라는 application 이다.
    // 이에 안드로이드에서 application 과 관련된 정보에 접근하고자 할때는 ActivityManager 를 통해야 한다.
    //


    // ex> 윈도우에서 폴더나 파일을 클릭하고 마우스 우클릭을 누르면 context menu 가 나온다.
    // 폴더가 달라도 context 메뉴에서 표시하는 내용은 같다 ( ex> 속성 등 )
    // 하지만 폴더별로 속성을 클릭했을 때 나오는 내용은 다르다.
    // 같은 속성항목이지만 폴더에 따라 속성의 내용이 다르다.
    // -> context 는 각각의 application, activity 를 구별할 수 있는 정보


//    The Context in Android is actually the context of what we are talking about and where we are currently present
//    You can use the context to get information regarding activity and application.
//    Context is like a handle to the system, it provides access to the resources, databases, and preferences, etc

//    The activity object extends the Context object.
//    It allows access to application-specific resources and gives information about the application environment.


    // ViewHolder
    //  Holder : a device for putting objects in or for keeping them in place:
    //    ex> a toothbrush holder ( 칫솔 보관함 ) , a cigarette holder ( 담배 보관함 )
    // ViewHolder -> 뷰보관장치, 뷰 보관함


    @NonNull
    @Override
    public Journal_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_journal, parent, false);
        // parent -> 어댑터를 생성하는 클래스

        return new ViewHolder(view, m_On_journal_listener);


    }

    // bind : 묶다
    // onBindViewHolder : viewholder 를 묶는 메서드
    // 어댑터로 넘어온 리스트를 아이템 클래스 객체로 넘긴다
    // 아이템 클래스에서 get 메서드를 통해 데이터를 받아서 화면에 표시한다.

    @Override
    public void onBindViewHolder(@NonNull Journal_adapter.ViewHolder holder, int position) {


        Journal_item journal_item = journal_items.get(position);

//        holder.imageView_journal.setImageURI(journal_item.getImage_journal());

        Glide.with(holder.itemView.getContext()).load(journal_item.getImage_journal()).into(holder.imageView_journal);
        holder.item_journal_date.setText(journal_item.getDate_journal());
        holder.item_journal_time.setText(journal_item.getTime_journal());


        String mood = journal_item.getRating_journal();
        if (mood.equals("1")) {
//            holder.item_journal_rating.setText("매우나쁨");
            holder.item_journal_mood_image.setImageResource(R.drawable.mood_1);

        } else if (mood.equals("2")) {
//            holder.item_journal_rating.setText("나쁨");
            holder.item_journal_mood_image.setImageResource(R.drawable.mood_2);
        } else if (mood.equals("3")) {
//            holder.item_journal_rating.setText("보통");
            holder.item_journal_mood_image.setImageResource(R.drawable.mood_3);
        } else if (mood.equals("4")) {
//            holder.item_journal_rating.setText("좋음");
            holder.item_journal_mood_image.setImageResource(R.drawable.mood_4);
        } else {
//            holder.item_journal_rating.setText("매우좋음");
            holder.item_journal_mood_image.setImageResource(R.drawable.mood_5);
        }


    }

    @Override
    public int getItemCount() {
        return journal_items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {


        ImageView imageView_journal, item_journal_mood_image;
        TextView item_journal_date, item_journal_rating,item_journal_time;
        on_journal_listener on_journal_listener;


        public ViewHolder(@NonNull View itemView, on_journal_listener on_journal_listener) {
            super(itemView);
            this.on_journal_listener = on_journal_listener;


            imageView_journal = itemView.findViewById(R.id.item_journal_image);
            item_journal_date = itemView.findViewById(R.id.item_journal_date);
            item_journal_rating = itemView.findViewById(R.id.item_journal_mood);
            item_journal_mood_image = itemView.findViewById(R.id.item_journal_mood_image);
            item_journal_time = itemView.findViewById(R.id.item_journal_time);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            MenuItem delete = menu.add(0, 102, 1, "삭제");


            delete.setOnMenuItemClickListener(onedit_menu);

        }

        public final MenuItem.OnMenuItemClickListener onedit_menu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {


                    case 102: // 삭제 선택

                        journal_items.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), journal_items.size());

                        break;

                }


                return true;
            }
        };


        @Override
        public void onClick(View v) {


            on_journal_listener.on_journal_click(getAdapterPosition());


        }
    }


    public interface on_journal_listener {

        void on_journal_click(int position);


    }

    public void set_filter(List<Journal_item> listitem)

    {

        journal_items = new ArrayList<>();

        journal_items.addAll(listitem);
        notifyDataSetChanged();

    }







}
