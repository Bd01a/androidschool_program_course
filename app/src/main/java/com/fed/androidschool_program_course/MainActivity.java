package com.fed.androidschool_program_course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LearningProgramProvider learningProgramProvider = new LearningProgramProvider(this);

    private LearningProgramAdapter   learningProgramAdapter = new LearningProgramAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initSpinner();
        initSpinnerWeek();

    }

    private void initSpinnerWeek() {
        Spinner spinner = findViewById(R.id.week_spinner);
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.week_spinner_items));
        final SpinnerAdapter adapter = new SpinnerAdapter(items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals(getResources().getStringArray(R.array.week_spinner_items)[0])){
                    learningProgramAdapter.setWeekVisibility(true);
                }
                else {
                    learningProgramAdapter.setWeekVisibility(false);
                }
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
                TextView textView = view.findViewById(R.id.lector);
                learningProgramAdapter.setmLecture(learningProgramProvider.filterBy(adapterView.getSelectedItem().toString()));
//                learningProgramProvider.provideLecture().get(i).getmLector())
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        learningProgramAdapter.setmLecture(learningProgramProvider.provideLecture());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(learningProgramAdapter);
        recyclerView.scrollToPosition(learningProgramAdapter.getPositionToNextLection());
    }
}
