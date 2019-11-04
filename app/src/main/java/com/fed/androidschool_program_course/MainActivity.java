package com.fed.androidschool_program_course;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.fed.androidschool_program_course.models.Lecture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LearningProgramProvider learningProgramProvider = new LearningProgramProvider(this);

    private LearningProgramAdapter   learningProgramAdapter = new LearningProgramAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LectureDownloadAsyncTask lectureDownloadAsyncTask = new LectureDownloadAsyncTask();
        lectureDownloadAsyncTask.execute();


    }

    private void initSpinnerWeek() {
        Spinner spinner = findViewById(R.id.week_spinner);
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.week_spinner_items));
        final SpinnerAdapter adapter = new SpinnerAdapter(items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learningProgramAdapter.setWeekVisibility(position == 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.lectors_spinner);
        List<String> lectors = learningProgramProvider.provideLectors();
        Collections.sort(lectors);
        lectors.add(0,getResources().getString(R.string.all_lectors));
        final SpinnerAdapter adapter = new SpinnerAdapter(lectors);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        learningProgramAdapter.setLecture(lectures);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(learningProgramAdapter);
        recyclerView.scrollToPosition(learningProgramAdapter.getPositionToNextLection());


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
            initSpinner();
            initSpinnerWeek();
        }
    }
}
