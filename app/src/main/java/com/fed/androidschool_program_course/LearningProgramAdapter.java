package com.fed.androidschool_program_course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fed.androidschool_program_course.models.Lecture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LearningProgramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int DEFAULT_ITEM = 1;
    public static final int WEEK_ITEM=2;

    private Context context;

    private List<Object> mLecture;
    private  List<Object> mLecturesWithWeek;
    private  List<Object> mItems;

    LearningProgramAdapter(Context context){
        this.context = context;
    }

    public int getPositionToNextLection(){
        Date currentDate = new Date();
        for(int i=0; i<mItems.size(); i++){
            if(mItems.get(i) instanceof Lecture){
                if(((Lecture) mItems.get(i)).getmDate().after(currentDate)){
                    return i;
                }
            }
        }
        return mItems.size()-1;
    }


    public void setmLecture(List<Lecture> lectures) {
        mLecture = new ArrayList<>();
        mLecturesWithWeek = new ArrayList<>();
        int week=0;
        for(Lecture lecture: lectures){
            if(lecture.getmWeek()>week){
                week=lecture.getmWeek();
                mLecturesWithWeek.add(week);
            }
            mLecturesWithWeek.add(lecture);
            mLecture.add(lecture);
        }
        mItems = mLecturesWithWeek;
        notifyDataSetChanged();
    }

    public void setWeekVisibility(boolean visibility){
        if(visibility){
            mItems = mLecturesWithWeek;
        }
        else {
            mItems = mLecture;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==DEFAULT_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
            return new LectureHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_layout, parent, false);
            return new WeekHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LectureHolder) {
            Lecture lecture = (Lecture)mItems.get(position);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            ((LectureHolder)holder).mNumber.setText(lecture.getmNumber());
            ((LectureHolder)holder).mDate.setText(format.format(lecture.getmDate()));
            ((LectureHolder)holder).mTheme.setText(lecture.getmTheme());
            ((LectureHolder)holder).mLector.setText(lecture.getmLector());
        }
        else if(holder instanceof WeekHolder){
            ((WeekHolder) holder).textView.setText(context.getResources().getString(R.string.week_item_string,
                    (Integer)mItems.get(position)));
        }
    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0: mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mItems.get(position) instanceof Lecture){
            return DEFAULT_ITEM;
        }
        else {
            return WEEK_ITEM;
        }
    }

    static class LectureHolder extends RecyclerView.ViewHolder{

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mLector;

        public LectureHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.date);
            mTheme = itemView.findViewById(R.id.theme);
            mLector = itemView.findViewById(R.id.lector);
        }
    }
    static class WeekHolder extends RecyclerView.ViewHolder{

        private final TextView textView;

        public WeekHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.week_textview);
        }
    }

}
