package com.fed.androidschool_program_course;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.fed.androidschool_program_course.fragments.InfoFragment;
import com.fed.androidschool_program_course.fragments.MainFragment;
import com.fed.androidschool_program_course.models.Lecture;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            mainFragment.setOnFragmentClickListener(new MainFragment.OnFragmentClickListener() {
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
                    .replace(R.id.fragment_layout,mainFragment)
                    .commit();
        }
    }


}
