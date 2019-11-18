package com.fed.androidschool_program_course.model;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

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


    private List<Lecture> mLectures;

    private OnLoadingFinishedListening mOnLoadingFinishedListening;

    public LearningProgramProvider() {


    }


    public List<Lecture> provideLecture() {
        return new ArrayList<>(mLectures);
    }

    public List<String> provideLectors() {
        Set<String> lectors = new HashSet<>();
        for (Lecture lecture : mLectures) {
            lectors.add(lecture.getLector());
        }
        return new ArrayList<String>(lectors);
    }


    public void loadLectures(OnLoadingFinishedListening onLoadingFinishedListening) {
        if (mLectures == null) {
            mOnLoadingFinishedListening = onLoadingFinishedListening;
            LectureDownloadAsyncTask lectureDownloadAsyncTask = new LectureDownloadAsyncTask();
            lectureDownloadAsyncTask.execute();
        }
    }

    private void downloadLecturesAsync() {
        InputStream is = null;
        try {
            URL myUrl = new URL(LECTURES_URL);
            URLConnection myUrlCon = myUrl.openConnection();
            is = myUrlCon.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            Lecture[] lectures = mapper.readValue(is, Lecture[].class);
            mLectures = Arrays.asList(lectures);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public interface OnLoadingFinishedListening {
        void onFinish();
    }

    class LectureDownloadAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            downloadLecturesAsync();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mOnLoadingFinishedListening.onFinish();
        }
    }


}
