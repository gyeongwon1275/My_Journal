package com.example.my_journal.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_journal.Habit_coupon.Habit_coupon_adapter;
import com.example.my_journal.R;

import java.util.List;

public class Game_record_adapter extends RecyclerView.Adapter <Game_record_adapter.ViewHolder> {


    List<Game_record_item> list_game_record;

    public Game_record_adapter(List<Game_record_item> list_game_record) {
        this.list_game_record = list_game_record;
    }

    @NonNull
    @Override
    public Game_record_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_record, parent, false);

        // parent -> 어댑터를 생성하는 클래스
        // 아이템 레이아웃에서 만든 것을 view 객체에 넣음

        // LayoutInflater : XML에 미리 정의해둔 틀을 실제 메모리에 올려주는 역할
        // XML 에 정의된 Resource 를 View 객체로 반환해주는 역할
        // inflate를 사용하기 위해서는 inflater 를 얻어와야 한다.
        // inflate (@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot )
        // resource -> 리사이클러뷰의 아이템 레이아웃 / 반복적으로 만들 레이아웃
        // root -> layoutParams 값을 설정하기 위한 상위 뷰
        // attachRoot  -> 생성되는 뷰를 root 의 자식으로 만들지 여부


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Game_record_adapter.ViewHolder holder, int position) {



        Game_record_item game_record_item = list_game_record.get(position);
        holder.game_record_time.setText(game_record_item.getGame_time());
        holder.game_record_point.setText(game_record_item.getGame_point());


    }

    @Override
    public int getItemCount() {
        return list_game_record.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView game_record_time, game_record_point,text_game_record;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            text_game_record = itemView.findViewById(R.id.text_game_record);
            game_record_point = itemView.findViewById(R.id.game_record_point);
            game_record_time = itemView.findViewById(R.id.game_record_time);

        }
    }
}
