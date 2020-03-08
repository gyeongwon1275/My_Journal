package com.example.my_journal.Habit;

import android.net.Uri;

public class Habit_item {

    private String habit_name;
    private String habit_number;
    private Uri habit_image;

    public String getHabit_name() {
        return habit_name;
    }

    public void setHabit_name(String habit_name) {
        this.habit_name = habit_name;
    }

    public String getHabit_number() {
        return habit_number;
    }

    public void setHabit_number(String habit_number) {
        this.habit_number = habit_number;
    }

    public Uri getHabit_image() {
        return habit_image;
    }

    public void setHabit_image(Uri habit_image) {
        this.habit_image = habit_image;
    }

    public Habit_item(String habit_name, String habit_number, Uri habit_image) {
        this.habit_name = habit_name;
        this.habit_number = habit_number;
        this.habit_image = habit_image;
    }
}
