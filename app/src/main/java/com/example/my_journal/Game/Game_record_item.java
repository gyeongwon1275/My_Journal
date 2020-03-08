package com.example.my_journal.Game;

import android.os.Parcel;
import android.os.Parcelable;

public class Game_record_item  {




    private String game_time;
    private String game_point;

    public String getGame_time() {
        return game_time;
    }

    public void setGame_time(String game_time) {
        this.game_time = game_time;
    }

    public String getGame_point() {
        return game_point;
    }

    public void setGame_point(String game_point) {
        this.game_point = game_point;
    }

    public Game_record_item(String game_time, String game_point) {
        this.game_time = game_time;
        this.game_point = game_point;
    }
}
