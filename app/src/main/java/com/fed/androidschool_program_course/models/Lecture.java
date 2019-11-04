package com.fed.androidschool_program_course.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Lecture implements Parcelable {
    private static int sWeekNumber= 1;

    private final int mNumber;
    private final Date mDate;
    private final String mTheme;
    private final String mLector;
    private final List<String> mSubtopics;

    private final int mWeek;

    public Lecture(@JsonProperty("number") int number,
                   @JsonProperty("date") String date,
                   @JsonProperty("theme") String theme,
                   @JsonProperty("lector") String lector,
                   @JsonProperty("subtopics") List<String> subtopics) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        this.mNumber = number;

        this.mDate = format.parse(date);

        this.mTheme = theme;
        this.mLector = lector;
        this.mSubtopics = subtopics;
        mWeek = sWeekNumber/3;
        sWeekNumber++;
    }

    private Lecture(Parcel in) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        mNumber = in.readInt();
        mTheme = in.readString();
        mLector = in.readString();
        mSubtopics = in.createStringArrayList();
        mWeek = in.readInt();
        mDate = format.parse(in.readString());
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            try {
                return new Lecture(in);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    public int getWeek() {
        return mWeek;
    }
    public int getNumber() {
        return mNumber;
    }

    public Date getDate() {
        return mDate;
    }

    public String getTheme() {
        return mTheme;
    }

    public String getLector() {
        return mLector;
    }

    public String getSubtopics() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String sting:mSubtopics) {
            stringBuilder.append(sting).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNumber);
        dest.writeString(mLector);
        dest.writeStringList(mSubtopics);
        dest.writeString(mTheme);
        dest.writeInt(mWeek);
        dest.writeString(mDate.toString());
    }


//
//    public Lecture(
//            @NonNull int week,
//            @NonNull String number,
//            @NonNull Date date,
//            @NonNull String theme,
//            @NonNull String lector
//    ){
//        mWeek = week;
//        mNumber = number;
//        mDate = date;
//        mTheme = theme;
//        mLector = lector;
//    }



}
