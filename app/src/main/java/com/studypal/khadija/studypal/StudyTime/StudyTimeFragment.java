package com.studypal.khadija.studypal.StudyTime;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.studypal.khadija.studypal.R;
import com.studypal.khadija.studypal.Syllabus.Chapter;
import com.studypal.khadija.studypal.Syllabus.ChapterFragment;
import com.studypal.khadija.studypal.Syllabus.ChapterListAdapter;
import com.studypal.khadija.studypal.Syllabus.CreateChapterDialogFragment;
import com.studypal.khadija.studypal.Syllabus.DatabaseSubject;
import com.studypal.khadija.studypal.Syllabus.SubjectFragment;
import com.studypal.khadija.studypal.Syllabus.SubjectListAdapter;


public class StudyTimeFragment extends Fragment implements SubjectListAdapter.AdapterCallback, ChapterListAdapter.ChapterClickListener {
    Cursor cursor;
    private SubjectListAdapter adapter;
    /* Variable, that is changed depending on which fragment is being displayed. */
    private static final int SUBJECT_LIST_VIEW = 0;
    private static final int CHAPTER_LIST_VIEW = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_studytime, container, false);
        setTitle();
        DatabaseSubject dbHandler = new DatabaseSubject(getActivity(), null, null, 1);
        cursor = dbHandler.getSubjects();
        adapter = new SubjectListAdapter(getActivity(), cursor, 0, this);
        ListView listView = (ListView) rootView.findViewById(R.id.studyList);
        listView.setAdapter(adapter);
        return rootView;
    }
    private void setTitle() {
        getActivity().setTitle("Study Time");
    }

    @Override
    public void onMethodCallback(int call, String pClass) {
        Log.d("chapterCallMethod", "chapterCallMethod");
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.content_frame, ChapterFragment.newInstance(pClass, false));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onChapterClick(String chapterName) {

    }
}