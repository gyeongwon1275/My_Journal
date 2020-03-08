package com.example.my_journal.Habit_coupon;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_journal.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Habit_coupon_adapter extends RecyclerView.Adapter<Habit_coupon_adapter.ViewHolder> {

    // extends RecyclerView.Adapter 이후 implement method
    // 클래스명.ViewHolder -> create class ViewHolder
    // ViewHolder class 에서 extends RecyclerView.ViewHolder
    //  리사이클러뷰가 표시되는 클래스에서 데이터를 넘겨받을 리스트어레이 생성
    // 데이터는 미리 설정한 아이템 형식으로 받음

    ArrayList number_coupon;
    Context context;

    public Habit_coupon_adapter(ArrayList number_coupon, Context context) {
        this.number_coupon = number_coupon;
        this.context = context;
    }


    // 뷰홀더를 만든다.

    // ViewHolder
    //  Holder : a device for putting objects in or for keeping them in place:
    //    ex> a toothbrush holder ( 칫솔 보관함 ) , a cigarette holder ( 담배 보관함 )
    // ViewHolder -> 뷰를 보관장치, 뷰 보관함


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_coupon, parent, false);

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
    public int getItemCount() { // item 을 몇개 만들건지 리턴
        return number_coupon.size();
    }


    // bind : 묶다
    // onBindViewHolder : viewholder 를 묶는 메서드
    // 어댑터로 넘어온 리스트를 아이템 클래스 객체로 넘긴다
    // 아이템 클래스에서 get 메서드를 통해 데이터를 받아서 화면에 표시한다.
    @Override
    public void onBindViewHolder(@NonNull Habit_coupon_adapter.ViewHolder holder, int position) {

        // 아이템 클래스에 어댑터로 넘어온 데이터를 전달
//        Habit_coupon_item coupon_item = number_coupon.get(position);
        String text = number_coupon.get(position) + "";

        // 아이템 클래스에서 데이터를 받아서 적용
        holder.button_habit_coupon.setText(text);


    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { // RecyclerView.ViewHolder 상속을 받아서  ViewHolder를 만든다
        TextView button_habit_coupon;

        // ViewHolder 가 아이템 view 를 갖고 있기 때문에 클릭이벤트는 viewholder에서 진행 된다.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            button_habit_coupon = itemView.findViewById(R.id.button_habit_coupon);
// 아이템 레이아웃에 있는 요소와 연결
            itemView.setOnCreateContextMenuListener(this);


            button_habit_coupon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION)
                    // 터치가 되면
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context)
                                .inflate(R.layout.coupon_info, null, false);
                        builder.setView(view);

                        Button button_coupon_confirm = view.findViewById(R.id.button_coupon_confirm);
                        TextView textView_coupon_time = view.findViewById(R.id.textView_coupon_time);
                        final EditText textView_coupon_memo = view.findViewById(R.id.textView_coupon_memo);
                        final String text = textView_coupon_memo.getText().toString();
                        Date date = new Date();
                        SimpleDateFormat input_date = new SimpleDateFormat("yyyy-MM-dd hh시 mm분");
                        String date_text = input_date.format(date);

                        textView_coupon_time.setText(date_text);
                        textView_coupon_memo.setText(text);


                        final AlertDialog dialog = builder.create();

                        button_coupon_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                dialog.dismiss();


                            }
                        });
                        dialog.show();


                    }


                }
            });

        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


            MenuItem edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            edit.setOnMenuItemClickListener(oneditmenu);
            delete.setOnMenuItemClickListener(oneditmenu);


        }

        private final MenuItem.OnMenuItemClickListener oneditmenu = new MenuItem.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case 1001:


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        // 다이얼로그를 보여주기 위해 edit_box.xml 파일을 사용합니다.

                        View view = LayoutInflater.from(context)
                                .inflate(R.layout.coupon_info, null, false);
                        builder.setView(view);

                        final Button button_coupon_confirm = view.findViewById(R.id.button_coupon_confirm);
                        final TextView textView_coupon_time = view.findViewById(R.id.textView_coupon_time);
                        final EditText textView_coupon_memo = view.findViewById(R.id.textView_coupon_memo);


                        final AlertDialog dialog = builder.create();

                        button_coupon_confirm.setOnClickListener(new View.OnClickListener() {


                            // 7. 수정 버튼을 클릭하면 현재 UI에 입력되어 있는 내용으로

                            public void onClick(View v) {
                                String date = textView_coupon_time.getText().toString();
                                String memo = textView_coupon_memo.getText().toString();


                                textView_coupon_memo.setText(memo);


                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        break;

                    case 1002:


                        break;

                }
                return true;
            }
        };


    }
}
