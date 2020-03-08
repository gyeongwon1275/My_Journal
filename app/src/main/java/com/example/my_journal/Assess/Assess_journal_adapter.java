package com.example.my_journal.Assess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_journal.R;

import java.util.List;

public class Assess_journal_adapter extends RecyclerView.Adapter<Assess_journal_adapter.ViewHolder> {

    // extends RecyclerView.Adapter<클래스명.Viewholder>
    // create class ViewHolder
    // implement method
    // ViewHolder class extends extends RecyclerView.ViewHolder
    // create constructor matching super

    List<Assess_journal_item> journal_data;

    public Assess_journal_adapter(List<Assess_journal_item> journal_data) {
        this.journal_data = journal_data;
    }

    @Override
    public int getItemCount() {
        return journal_data.size();
    }

// ViewHolder 를 생성하는 메서드
    // 뷰홀더를 만든다.

    // ViewHolder
    //  Holder : a device for putting objects in or for keeping them in place:
    //    ex> a toothbrush holder ( 칫솔 보관함 ) , a cigarette holder ( 담배 보관함 )
    // ViewHolder -> 뷰를 보관장치, 뷰 보관함

    // inflate 한 layout을 ViewHolder 에 담아 return

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assess_journal,parent,false);

        return new ViewHolder(view);


    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView access_item_day, access_item_rating;
        ImageView access_item_graph;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 아이템 레이아웃에 있는 view와 연결
            access_item_day = itemView.findViewById(R.id.access_item_day);
            access_item_rating = itemView.findViewById(R.id.access_item_rating);
            access_item_graph = itemView.findViewById(R.id.access_item_graph);




            // super : 상부의, 위의
            // super : 클래스가 상속받은 부모를 가르킴
            // super()
            // 자식클래스가 자신을 생성하려면 부모를 먼저 생성하여야 한다.
            // 때문에 부모클래스의 생성자를 호출하는 super() 를 사용하여
            // 부모클래스의 속성을 초기화 한 후 자식의 속성을 초기화 한다.

        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Assess_journal_item assess_journal_item = journal_data.get(position);

        holder.access_item_day.setText(assess_journal_item.getDay());
        holder.access_item_rating.setText(assess_journal_item.getRating());





        String mood = assess_journal_item.getRating();

        if (mood.equals("1")) {

            holder.access_item_graph.setImageResource(R.drawable.graph_1);
        } else if (mood.equals("2")) {


            holder.access_item_graph.setImageResource(R.drawable.graph_2);

        } else if (mood.equals("3")) {


            holder.access_item_graph.setImageResource(R.drawable.graph_3);
        } else if (mood.equals("4")) {


            holder.access_item_graph.setImageResource(R.drawable.graph_4);
        } else {
            holder.access_item_graph.setImageResource(R.drawable.graph_5);
        }


    }













}
