package com.fed.androidschool_program_course.models;


import androidx.annotation.NonNull;

import java.util.Date;

public class Lecture {
    private final int mWeek;
    private final String mNumber;
    private final Date mDate;
    private final String mTheme;
    private final String mLector;

    public Lecture(
            @NonNull int week,
            @NonNull String number,
            @NonNull Date date,
            @NonNull String theme,
            @NonNull String lector
    ){
        mWeek = week;
        mNumber = number;
        mDate = date;
        mTheme = theme;
        mLector = lector;
    }

    public int getmWeek(){
        return mWeek;
    }

    public String getmNumber() {
        return mNumber;
    }

    public Date getmDate() {
        return mDate;
    }

    public String getmTheme() {
        return mTheme;
    }

    public String getmLector() {
        return mLector;
    }
}
