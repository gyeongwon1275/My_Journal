package com.example.my_journal.Hour_record;

public class Hour_record_item {


    public String time;
    public String text;

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        this.time = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Hour_record_item(String time, String text) {
        this.time = time;
        this.text = text;
    }
}
