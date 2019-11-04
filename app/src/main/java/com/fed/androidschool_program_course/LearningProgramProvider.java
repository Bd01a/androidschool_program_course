package com.fed.androidschool_program_course;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fed.androidschool_program_course.models.Lecture;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LearningProgramProvider {
    private static final String LECTURES_URL = "http://landsovet.ru/learning_program.json";

    Context context;

    private List<Lecture> mLectures;

    LearningProgramProvider(Context context){
        this.context = context;
    }

    public List<Lecture> provideLecture() {
        downloadLectures();
        return new ArrayList<>(mLectures);
    }

    public List<String> provideLectors() {
        Set<String> lectors = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectors.add(lecture.getLector());
        }
        return new ArrayList<String>(lectors);
    }

    public List<Lecture> filterBy(String lector){

        if(lector == context.getString(R.string.all_lectors)){
            return provideLecture();
        }

        List<Lecture> sortedLectures = new ArrayList<>();
        for (Lecture lecture : mLectures) {
            if(lecture.getLector().equals(lector)){
                sortedLectures.add(lecture);
            }
        }
        return sortedLectures;
    }

    private void downloadLectures(){
        InputStream is = null;
        try {
            URL myUrl = new URL(LECTURES_URL);
            URLConnection myUrlCon = myUrl.openConnection();
            is = myUrlCon.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            Lecture[] lectures = mapper.readValue(is, Lecture[].class);
            mLectures = Arrays.asList(lectures);


        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
