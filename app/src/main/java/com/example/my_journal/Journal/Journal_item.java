package com.example.my_journal.Journal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Journal_item implements Parcelable {

    // 1> ALT + ENTER -> implement All Parcelable method

    // 2> ALT + ENTER -> Add Parcelable implementation

    // 3> activity 에서 인텐트로 데이터 전달시 List 로 보낼 수 있다.

    private String text_journal,date_journal,rating_journal,time_journal;
private Uri image_journal;

    protected Journal_item(Parcel in) {
        text_journal = in.readString();
        date_journal = in.readString();
        rating_journal = in.readString();
        time_journal = in.readString();
        image_journal = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Journal_item> CREATOR = new Creator<Journal_item>() {
        @Override
        public Journal_item createFromParcel(Parcel in) {
            return new Journal_item(in);
        }

        @Override
        public Journal_item[] newArray(int size) {
            return new Journal_item[size];
        }
    };

    public String getText_journal() {
        return text_journal;
    }

    public void setText_journal(String text_journal) {
        this.text_journal = text_journal;
    }

    public String getDate_journal() {
        return date_journal;
    }

    public void setDate_journal(String date_journal) {
        this.date_journal = date_journal;
    }

    public String getRating_journal() {
        return rating_journal;
    }

    public void setRating_journal(String rating_journal) {
        this.rating_journal = rating_journal;
    }

    public String getTime_journal() {
        return time_journal;
    }

    public void setTime_journal(String time_journal) {
        this.time_journal = time_journal;
    }

    public Uri getImage_journal() {
        return image_journal;
    }

    public void setImage_journal(Uri image_journal) {
        this.image_journal = image_journal;
    }

    public Journal_item(String text_journal, String date_journal, String rating_journal, Uri image_journal, String time_journal ) {
        this.text_journal = text_journal;
        this.date_journal = date_journal;
        this.rating_journal = rating_journal;
        this.time_journal = time_journal;
        this.image_journal = image_journal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text_journal);
        dest.writeString(date_journal);
        dest.writeString(rating_journal);
        dest.writeString(time_journal);
        dest.writeParcelable(image_journal, flags);
    }
}
