package com.studypal.khadija.studypal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExamFragment  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_exams, container, false);
        setTitle();
        return rootView;
    }
    private void setTitle() {
        getActivity().setTitle("Exam");
    }
}
