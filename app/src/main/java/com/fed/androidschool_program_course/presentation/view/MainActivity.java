package com.fed.androidschool_program_course.presentation.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fed.androidschool_program_course.R;
import com.fed.androidschool_program_course.model.LearningProgramProvider;
import com.fed.androidschool_program_course.model.Lecture;
import com.fed.androidschool_program_course.presentation.presentor.LecturePresentor;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ILectureView {
    private static final String PROVIDER = "Provider";

    private MainFragment mMainFragment;
    private View mProgressBar;
    private LecturePresentor mLecturePresentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);
        LearningProgramProvider learningProgramProvider = new LearningProgramProvider();
        mLecturePresentor = new LecturePresentor(this, learningProgramProvider);
        mLecturePresentor.loadData();

    }


    @Override
    public void showAllLectures() {
        mProgressBar.setVisibility(View.GONE);
        List<String> lectors = mLecturePresentor.provideLectors();
        Collections.sort(lectors);
        lectors.add(0, getResources().getString(R.string.all_lectors));
        mMainFragment = MainFragment.newInstance(lectors, mLecturePresentor.provideLecture());


    }

    @Override
    public void init() {
        mMainFragment.setOnFragmentClickListener(new MainFragment.OnFragmentClickListener() {
            @Override
            public void onClick(Lecture lecture) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_layout, InfoFragment.newInstance(lecture))
                        .commit();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, mMainFragment)
                .commit();
    }


    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLecturePresentor.detachView();
    }
}
