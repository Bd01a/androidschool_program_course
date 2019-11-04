package com.fed.androidschool_program_course.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fed.androidschool_program_course.LearningProgramAdapter;
import com.fed.androidschool_program_course.LearningProgramProvider;
import com.fed.androidschool_program_course.R;
import com.fed.androidschool_program_course.SpinnerAdapter;
import com.fed.androidschool_program_course.models.Lecture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment {

    private OnFragmentClickListener mOnFragmentClickListener;
    private LearningProgramProvider learningProgramProvider;

    private LearningProgramAdapter learningProgramAdapter;

    private RecyclerView mRecycleView;
    private Spinner mLectorSpinner, mWeekSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.main_fragment, container, false);

        mRecycleView = parent.findViewById(R.id.recycler_view);
        mLectorSpinner = parent.findViewById(R.id.lectors_spinner);
        mWeekSpinner = parent.findViewById(R.id.week_spinner);
        learningProgramProvider = new LearningProgramProvider(getContext());
        learningProgramAdapter = new LearningProgramAdapter(getContext(), mOnFragmentClickListener);

        LectureDownloadAsyncTask lectureDownloadAsyncTask = new LectureDownloadAsyncTask();
        lectureDownloadAsyncTask.execute();
        return parent;
    }

    private void initSpinnerWeek() {
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.week_spinner_items));
        final SpinnerAdapter adapter = new SpinnerAdapter(items);
        mWeekSpinner.setAdapter(adapter);

        mWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learningProgramAdapter.setWeekVisibility(position == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initLectorSpinner() {
        List<String> lectors = learningProgramProvider.provideLectors();
        Collections.sort(lectors);
        lectors.add(0,getResources().getString(R.string.all_lectors));
        final SpinnerAdapter adapter = new SpinnerAdapter(lectors);
        mLectorSpinner.setAdapter(adapter);

        mLectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learningProgramAdapter.setLecture(learningProgramProvider.filterBy(adapterView.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initRecyclerView(List<Lecture> lectures){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        learningProgramAdapter.setLecture(lectures);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecycleView.addItemDecoration(dividerItemDecoration);
        mRecycleView.setAdapter(learningProgramAdapter);
        mRecycleView.scrollToPosition(learningProgramAdapter.getPositionToNextLection());


    }

     class LectureDownloadAsyncTask extends AsyncTask<Void, Void, List<Lecture>> {

        @Override
        protected List<Lecture> doInBackground(Void... voids) {
            return learningProgramProvider.provideLecture();
        }

        @Override
        protected void onPostExecute(List<Lecture> lectures) {
            super.onPostExecute(lectures);
            initRecyclerView(lectures);
            initLectorSpinner();
            initSpinnerWeek();
        }
    }


    public void setOnFragmentClickListener(OnFragmentClickListener onFragmentClickListener){
        mOnFragmentClickListener = onFragmentClickListener;
    }
    public static interface OnFragmentClickListener{
        public void onClick(Lecture lecture);
    }
}
