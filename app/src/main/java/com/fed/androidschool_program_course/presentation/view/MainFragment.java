package com.fed.androidschool_program_course.presentation.view;

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

import com.fed.androidschool_program_course.R;
import com.fed.androidschool_program_course.model.Lecture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {
    private static final String ARG_LECTORS = "arg_lectors";
    private static final String ARG_LECTURES = "arg_lectures";

    private OnFragmentClickListener mOnFragmentClickListener;
//    private LearningProgramProvider learningProgramProvider;

    private LearningProgramAdapter learningProgramAdapter;
    private RecyclerView mRecycleView;
    private Spinner mLectorSpinner, mWeekSpinner;


    static MainFragment newInstance(List<String> lectors, List<Lecture> lectures) {

        Bundle args = new Bundle();
        ArrayList<String> lectorsArray = new ArrayList<>(lectors);
        args.putStringArrayList(ARG_LECTORS, lectorsArray);
        ArrayList<Lecture> lecturesArray = new ArrayList<>(lectures);
        args.putParcelableArrayList(ARG_LECTURES, lecturesArray);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(args);
        return mainFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.main_fragment, container, false);

        mRecycleView = parent.findViewById(R.id.recycler_view);
        mLectorSpinner = parent.findViewById(R.id.lectors_spinner);
        mWeekSpinner = parent.findViewById(R.id.week_spinner);
        learningProgramAdapter = new LearningProgramAdapter(getContext(), mOnFragmentClickListener);


        init();
        return parent;
    }

    public void init() {
        initRecyclerView();
        initLectorSpinner();
        initSpinnerWeek();
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
        List<String> lectors = getArguments().getStringArrayList(ARG_LECTORS);
        final SpinnerAdapter adapter = new SpinnerAdapter(lectors);
        mLectorSpinner.setAdapter(adapter);

        mLectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learningProgramAdapter.setLecture(filterBy(adapterView.getSelectedItem().toString(),
                        getArguments().<Lecture>getParcelableArrayList(ARG_LECTURES)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<Lecture> filterBy(String lector, List<Lecture> lectures) {

        if (lector == getContext().getString(R.string.all_lectors)) {
            return lectures;
        }

        List<Lecture> sortedLectures = new ArrayList<>();
        for (Lecture lecture : lectures) {
            if (lecture.getLector().equals(lector)) {
                sortedLectures.add(lecture);
            }
        }
        return sortedLectures;
    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        learningProgramAdapter.setLecture(getArguments().<Lecture>getParcelableArrayList(ARG_LECTURES));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecycleView.addItemDecoration(dividerItemDecoration);
        mRecycleView.setAdapter(learningProgramAdapter);
        mRecycleView.scrollToPosition(learningProgramAdapter.getPositionToNextLection());


    }

    public void setOnFragmentClickListener(OnFragmentClickListener onFragmentClickListener){
        mOnFragmentClickListener = onFragmentClickListener;
    }

    public interface OnFragmentClickListener {
        void onClick(Lecture lecture);
    }
}
