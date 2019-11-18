package com.fed.androidschool_program_course.presentation.presentor;

import com.fed.androidschool_program_course.model.LearningProgramProvider;
import com.fed.androidschool_program_course.model.Lecture;
import com.fed.androidschool_program_course.presentation.view.ILectureView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LecturePresentorTest {

    private LecturePresentor mLecturePresentor;
    private LearningProgramProvider mLearningProgramProvider;

    @Before
    public void setUp() throws Exception {
        mLearningProgramProvider = Mockito.mock(LearningProgramProvider.class);
        ILectureView lectureView = Mockito.mock(ILectureView.class);
        mLecturePresentor = new LecturePresentor(lectureView, mLearningProgramProvider);
    }

    @Test
    public void provideLectures() {
        List<String> strings1 = new ArrayList<>();
        strings1.add("One1");
        strings1.add("Two1");

        List<String> strings2 = new ArrayList<>();
        strings2.add("One2");
        strings2.add("Two2");

        List<String> strings3 = new ArrayList<>();
        strings3.add("One3");
        strings3.add("Two3");

        try {


            List<Lecture> lectures = Arrays.asList(
                    new Lecture(1, "21.01.1997", "them_one", "Lector_one", strings1),
                    new Lecture(2, "21.01.1977", "them_one", "Lector one", strings2),
                    new Lecture(3, "21.01.1947", "them_one", "Lector one", strings3));

            Mockito.when(mLearningProgramProvider.provideLecture()).thenReturn(lectures);
            assertEquals(mLecturePresentor.provideLecture(), lectures);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Test
    public void provideLectorsTest() {
        List<String> strings = new ArrayList<>();
        strings.add("One2");
        strings.add("Two2");
        strings.add("Tw");
        strings.add("wee");

        Mockito.when(mLearningProgramProvider.provideLectors()).thenReturn(strings);
        assertEquals(mLecturePresentor.provideLectors(), strings);
    }


}