package com.example.my_journal.Habit;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_journal.R;

import java.util.List;

public class Habit_adapter extends RecyclerView.Adapter<Habit_adapter.ViewHolder> {


    private final List<Habit_item> item_habit_list;
    private Context context_habit;

    public Habit_adapter(List<Habit_item> item_habit_list, Context context_habit) {
        this.item_habit_list = item_habit_list;
        this.context_habit = context_habit;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);

        // parent -> 어댑터를 생성하는 클래스
        return new ViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return item_habit_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { // RecyclerView.ViewHolder 상속을 받아서  ViewHolder를 만든다

        TextView item_habit_name, item_habit_goal;
       ImageView item_habit_image;


        // 아이템 레이아웃에 설정한 요소를 viewholder 의 view 로 연결

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_habit_name = itemView.findViewById(R.id.item_habit_name);
            item_habit_goal = itemView.findViewById(R.id.item_habit_goal);
            item_habit_image = itemView.findViewById(R.id.item_habit_image);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);


        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case 1001:


                        AlertDialog.Builder builder = new AlertDialog.Builder(context_habit);


                        View view = LayoutInflater.from(context_habit)
                                .inflate(R.layout.edit_box, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = view.findViewById(R.id.button_submit);
                        final EditText editText_habit = view.findViewById(R.id.edit_first);
                        final EditText editText_habit_number = view.findViewById(R.id.edit_second);


                        editText_habit.setText(item_habit_list.get(getAdapterPosition()).getHabit_name());

                        //getAdapterPosition() -> 터치한 위치 가져오기
                        editText_habit_number.setText(item_habit_list.get(getAdapterPosition()).getHabit_number());


                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {


                            public void onClick(View v) {
                                String habit_name = editText_habit.getText().toString();
                                String habit_number = editText_habit_number.getText().toString();


                                Habit_item recyclerview_itemHabit = new Habit_item(habit_name, habit_number , null);

                                item_habit_list.set(getAdapterPosition(), recyclerview_itemHabit);


                                notifyItemChanged(getAdapterPosition());

                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:

                        item_habit_list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), item_habit_list.size());

                        break;

                }
                return true;
            }
        };
    }

        // bind : 묶다
        // onBindViewHolder : viewholder 를 묶는 메서드
        // 어댑터로 넘어온 리스트를 아이템 클래스 객체로 넘긴다
        // 아이템 클래스에서 get 메서드를 통해 데이터를 받아서 화면에 표시한다.


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Habit_item recyclerview_itemHabit = item_habit_list.get(position);

        holder.item_habit_name.setText(recyclerview_itemHabit.getHabit_name()); // 리스트의 해당 자리에 있는 아이템 을 가져온다.
        holder.item_habit_goal.setText(recyclerview_itemHabit.getHabit_number()); // holder 에 데이터를 넣어 표시한다.


    }


}
