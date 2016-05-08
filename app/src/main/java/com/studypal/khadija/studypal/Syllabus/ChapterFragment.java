package com.studypal.khadija.studypal.Syllabus;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.studypal.khadija.studypal.R;
import com.studypal.khadija.studypal.StudyTime.StudyTimeRecord;

public class ChapterFragment extends Fragment implements ChapterListAdapter.AdapterCallback, ChapterListAdapter.ChapterClickListener{

    Cursor cursor;
    private ChapterListAdapter adapter;
    private boolean isSyllabus = true;

    public static ChapterFragment newInstance(String pClass) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putString("parent_class", pClass);
        fragment.setArguments(args);
        return fragment;
    }

    public static ChapterFragment newInstance(String pClass, boolean isSyllabus) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putString("parent_class", pClass);
        args.putBoolean("is_syllabus", isSyllabus);
        fragment.setArguments(args);
        return fragment;
    }

    //DialogFragment editDialogFragment = null;

    @Override
    public void onMethodCallback(int call, Chapter a) {
        switch (call) {
            case 0:
                DatabaseChapter db = new DatabaseChapter(getActivity(), null, null, 1);
                adapter.changeCursor(db.getAssignments(a.getParentClass()));
                adapter.notifyDataSetChanged();
                break;
            case 1:
                DialogFragment dialogFragment = new ChapterDialogFragment();
                Bundle args = new Bundle();
                args.putString("name", a.getName());
                args.putString("description", a.getDescription());

                dialogFragment.setArguments(args);
                dialogFragment.show(getActivity().getFragmentManager(), "dialog");
                break;
          /*  case 2:
                if (editDialogFragment == null) {
                    editDialogFragment = new EditChapterDialogFragment();
                    Bundle editargs = new Bundle();
                    editargs.putString("name", a.getName());
                    editargs.putString("description", a.getDescription());
                    editargs.putInt("year", a.getYear());
                    editargs.putInt("month", a.getMonth());
                    editargs.putInt("day", a.getDay());
                    editDialogFragment.setArguments(editargs);
                    editDialogFragment.show(getActivity().getFragmentManager(), "dialog");
                }
                break;*/

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("is_syllabus")) {
            isSyllabus = bundle.getBoolean("is_syllabus");
        }
        DatabaseChapter dbHandler = new DatabaseChapter(getActivity(), null, null, 1);
        cursor = dbHandler.getAssignments(getArguments().getString("parent_class"));
        adapter = new ChapterListAdapter(getActivity().getApplicationContext(), cursor, this);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        if (isSyllabus && (cursor == null || cursor.getCount() == 0)) {
            DialogFragment dialogFragment = new CreateChapterDialogFragment();
            Bundle args = new Bundle();
            args.putString("parent_class", getArguments().getString("parent_class"));
            dialogFragment.setArguments(args);
            dialogFragment.show(getActivity().getFragmentManager(), "dialog");
        }

        return view;
    }

    @Override
    public void onChapterClick(String chapterName) {
        if (!isSyllabus) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            StudyTimeRecord fragment = new StudyTimeRecord();
            Bundle bundle = new Bundle();
            bundle.putString("chapter_name", chapterName);
            fragment.setArguments(bundle);
            transaction.replace(R.id.content_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
