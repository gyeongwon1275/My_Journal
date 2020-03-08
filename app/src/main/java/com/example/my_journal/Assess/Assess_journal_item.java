package com.example.my_journal.Assess;

import android.graphics.drawable.Drawable;

public class Assess_journal_item {

    private String day;
    private String rating;


    public Assess_journal_item(String day, String rating) {
        this.day = day;
        this.rating = rating;

    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



}
