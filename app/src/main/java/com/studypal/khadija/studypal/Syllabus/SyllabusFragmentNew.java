package com.studypal.khadija.studypal.Syllabus;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studypal.khadija.studypal.R;

public class SyllabusFragmentNew extends Fragment implements ColorDialogFragment.onCreateInteractionListener,
        CreateChapterDialogFragment.onCreateInteractionListener,
        SubjectFragment.chapterCall,
        EditSubjectDialogFragment.onEditListener,
        EditChapterDialogFragment.onEditListener,
        EditColorDialogFragment.onCreateInteractionListener {

    public SyllabusFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_syllabus, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
         /* Creating listener for Floating Action Button. */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Action for when Floating Action Button is clicked. */
                DialogFragment dialogFragment = new CreateSubjectDialogFragment();
                dialogFragment.show(getActivity().getFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void chapterCallMethod(String pClass) {

    }

    @Override
    public void createClicked() {

    }

    @Override
    public void editChapterMethod(int c) {

    }

    @Override
    public void createChapterClicked(Chapter chapter) {

    }

    @Override
    public void editSubjectMethod() {

    }
}
