package com.fed.androidschool_program_course;

import android.content.Context;
import android.util.Log;

import com.fed.androidschool_program_course.models.Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LearningProgramProvider {

    Context context;

    private List<Lecture> mLectures;

    LearningProgramProvider(Context context){

        this.context = context;


        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
        mLectures = Arrays.asList(
                new Lecture(1 , "1", format.parse("24.09.2019"), "Вводное занятие", "Соколов"),
                new Lecture(1 , "2", format.parse("26.09.2019"), "View, Layouts", "Соколов"),
                new Lecture(1 , "3", format.parse("28.09.2019"), "Drawables", "Соколов"),
                new Lecture(2 , "4", format.parse("01.10.2019"), "Activity", "Сафарян"),
                new Lecture(2 , "5", format.parse("03.10.2019"), "Адаптеры", "Чумак"),
                new Lecture(2 , "6", format.parse("05.10.2019"), "UI: практика", "Кудрявцев"),
                new Lecture(3 , "7", format.parse("08.10.2019"), "Custom View", "Кудрявцев"),
                new Lecture(3 , "8", format.parse("10.10.2019"), "Touch events", "Бильчук"),
                new Lecture(3 , "9", format.parse("12.10.2019"), "Сложные жесты", "Соколов"),
                new Lecture(4 , "10",format.parse("15.10.2019"), "Layout & Measurement", "Кудрявцев"),
                new Lecture(4 , "11",format.parse("17.10.2019"), "Custom ViewGroup", "Кудрявцев"),
                new Lecture(4 , "12",format.parse("19.10.2019"), "Анимации", "Чумак"),
                new Lecture(5 , "13",format.parse("22.10.2019"), "Практика View", "Соколов"),
                new Lecture(5 , "14",format.parse("24.10.2019"), "Фрагменты: база", "Бильчук"),
                new Lecture(5 , "15",format.parse("26.10.2019"), "Фрагменты: практика", "Соколов"),
                new Lecture(6 , "16",format.parse("29.10.2019"), "Фоновая работа", "Чумак"),
                new Lecture(6 , "17",format.parse("31.10.2019"), "Абстракции фон/UI", "Леонидов"),
                new Lecture(6 , "18",format.parse("05.11.2019"), "Фон: практика", "Чумак"),
                new Lecture(7 , "19",format.parse("07.11.2019"), "BroadcastReceiver", "Бильчук"),
                new Lecture(7 , "20",format.parse("09.11.2019"), "Runtime permissions", "Кудрявцев"),
                new Lecture(7 , "21",format.parse("12.11.2019"), "Service", "Леонидов"),
                new Lecture(8 , "22",format.parse("14.11.2019"), "Service: практика", "Леонидов"),
                new Lecture(8 , "23",format.parse("16.11.2019"), "Service: биндинг", "Леонидов"),
                new Lecture(8 , "24",format.parse("19.11.2019"), "Preferences", "Сафарян"),
                new Lecture(9 , "25",format.parse("21.11.2019"), "SQLite", "Бильчук"),
                new Lecture(9 , "26",format.parse("23.11.2019"), "SQLite: Room", "Соколов"),
                new Lecture(9 , "27",format.parse("26.11.2019"), "ContentProvider", "Сафарян"),
                new Lecture(10, "28",format.parse("28.11.2019"), "FileProvider", "Соколов"),
                new Lecture(10, "29",format.parse("30.11.2019"), "Геолокация", "Леонидов"),
                new Lecture(10, "30",format.parse("03.12.2019"), "Material", "Чумак"),
                new Lecture(11, "31",format.parse("05.12.2019"), "UI-тесты", "Сафарян"),
                new Lecture(11, "32",format.parse("07.12.2019"), "Финал", "Соколов")
        );
        } catch (
                ParseException e) {
            e.printStackTrace();
        }


    }




    public List<Lecture> provideLecture() {
        return mLectures;
    }

    public List<String> provideLectors() {
        Set<String> lectors = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectors.add(lecture.getmLector());
        }
        return new ArrayList<String>(lectors);
    }

    public List<Lecture> filterBy(String lector){

        if(lector == context.getString(R.string.all_lectors)){
            return provideLecture();
        }

        List<Lecture> sortedLectures = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if(lecture.getmLector() == lector){
                sortedLectures.add(lecture);
            }
        }
        return sortedLectures;
    }

}
