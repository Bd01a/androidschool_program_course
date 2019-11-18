package com.fed.androidschool_program_course.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fed.androidschool_program_course.R;
import com.fed.androidschool_program_course.model.Lecture;

import java.text.SimpleDateFormat;

public class InfoFragment extends Fragment {

    private static final String ARG = "arg";

    private TextView mNumber;
    private TextView mDate;
    private TextView mTheme;
    private TextView mLector;
    private TextView mSubTopics;

    public static InfoFragment newInstance(Lecture lecture) {

        Bundle args = new Bundle();
        args.putParcelable(ARG, lecture);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.info_fragment, container, false);
        mNumber = parent.findViewById(R.id.number);
        mDate = parent.findViewById(R.id.date);
        mTheme = parent.findViewById(R.id.theme);
        mLector = parent.findViewById(R.id.lector);
        mSubTopics = parent.findViewById(R.id.sub_topics);

        Lecture lecture = getArguments().getParcelable(ARG);
        mNumber.setText(String.valueOf(lecture.getNumber()));
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        mDate.setText(format.format(lecture.getDate()));
        mTheme.setText(lecture.getTheme());
        mLector.setText(lecture.getLector());
        mSubTopics.setText(lecture.getSubtopics().toString());


        return parent;
    }
}
