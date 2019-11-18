package com.fed.androidschool_program_course.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fed.androidschool_program_course.R;
import com.fed.androidschool_program_course.model.Lecture;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LearningProgramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_ITEM = 1;
    private static final int WEEK_ITEM = 2;

    private Context mContext;

    private List<Lecture> mLecture;
    private List<Object> mLecturesWithWeek;
    private List<Object> mItems;

    private MainFragment.OnFragmentClickListener mOnFragmentCLickListener;

    public LearningProgramAdapter(Context context, MainFragment.OnFragmentClickListener onFragmentCLickListener) {
        mContext = context;
        mOnFragmentCLickListener = onFragmentCLickListener;
    }

    public int getPositionToNextLection() {
        Date currentDate = new Date();
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i) instanceof Lecture) {
                if (((Lecture) mItems.get(i)).getDate().after(currentDate)) {
                    return i;
                }
            }
        }
        return mItems.size() - 1;
    }


    public void setLecture(List<Lecture> lectures) {
        mLecture = new ArrayList<>();
        mLecturesWithWeek = new ArrayList<>();
        int week = 0;
        for (Lecture lecture : lectures) {
            if (lecture.getWeek() > week) {
                week = lecture.getWeek();
                mLecturesWithWeek.add(week);
            }
            mLecturesWithWeek.add(lecture);
            mLecture.add(lecture);
        }
        mItems = mLecturesWithWeek;
        notifyDataSetChanged();
    }

    public void setWeekVisibility(boolean visibility) {
        mItems = new ArrayList<>(visibility ? mLecturesWithWeek : mLecture);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DEFAULT_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lecture, parent, false);
            return new LectureHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_layout, parent, false);
            return new WeekHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LectureHolder) {
            final Lecture lecture = (Lecture) mItems.get(position);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            LectureHolder lectureHolder = ((LectureHolder) holder);
            lectureHolder.mNumber.setText(String.valueOf(lecture.getNumber()));
            lectureHolder.mDate.setText(format.format(lecture.getDate()));
            lectureHolder.mTheme.setText(lecture.getTheme());
            lectureHolder.mLector.setText(lecture.getLector());
            lectureHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnFragmentCLickListener.onClick(lecture);
                }
            });
        } else if (holder instanceof WeekHolder) {
            ((WeekHolder) holder).textView.setText(mContext.getResources().getString(R.string.week_item_string,
                    (Integer) mItems.get(position)));
        }
    }


    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position) instanceof Lecture ? DEFAULT_ITEM : WEEK_ITEM;
    }

    static class LectureHolder extends RecyclerView.ViewHolder {

        private final TextView mNumber;
        private final TextView mDate;
        private final TextView mTheme;
        private final TextView mLector;
        private final View mItemView;

        public LectureHolder(@NonNull View itemView) {
            super(itemView);
            mItemView = itemView;
            mNumber = itemView.findViewById(R.id.number);
            mDate = itemView.findViewById(R.id.date);
            mTheme = itemView.findViewById(R.id.theme);
            mLector = itemView.findViewById(R.id.lector);
        }
    }

    static class WeekHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public WeekHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.week_textview);
        }
    }

}
