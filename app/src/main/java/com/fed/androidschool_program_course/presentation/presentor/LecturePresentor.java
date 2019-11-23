package com.fed.androidschool_program_course.presentation.presentor;

import com.fed.androidschool_program_course.model.LearningProgramProvider;
import com.fed.androidschool_program_course.model.Lecture;
import com.fed.androidschool_program_course.presentation.view.ILectureView;

import java.lang.ref.WeakReference;
import java.util.List;

public class LecturePresentor {

    private final WeakReference<ILectureView> mLectureView;

    private final LearningProgramProvider mLearningProgramProvider;


    public LecturePresentor(ILectureView lectureView, LearningProgramProvider learningProgramProvider) {
        mLectureView = new WeakReference<>(lectureView);
        mLearningProgramProvider = learningProgramProvider;
    }


    public void loadData() {
        if (mLectureView.get() != null) {
            mLectureView.get().showProgress();
        }

        LearningProgramProvider.OnLoadingFinishedListening onLoadingFinishedListening =
                new LearningProgramProvider.OnLoadingFinishedListening() {
                    @Override
                    public void onFinish() {
                        if (mLectureView.get() != null) {
                            mLectureView.get().showAllLectures();
                            mLectureView.get().init();
                        }

                    }
                };

        mLearningProgramProvider.loadLectures(onLoadingFinishedListening);
    }

    public List<Lecture> provideLecture() {
        return mLearningProgramProvider.provideLecture();
    }

    public List<String> provideLectors() {
        return mLearningProgramProvider.provideLectors();
    }

    public void detachView() {
        mLectureView.clear();
    }


}
