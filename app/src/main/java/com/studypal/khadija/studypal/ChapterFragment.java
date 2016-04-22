package com.studypal.khadija.studypal;

import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChapterFragment extends Fragment implements ChapterListAdapter.AdapterCallback {

    Cursor cursor;
    private ChapterListAdapter adapter;

    public static ChapterFragment newInstance(String pClass) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putString("parent_class", pClass);
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
                args.putInt("year", a.getYear());
                args.putInt("month", a.getMonth());
                args.putInt("day", a.getDay());
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

        DatabaseChapter dbHandler = new DatabaseChapter(getActivity(), null, null, 1);
        cursor = dbHandler.getAssignments(getArguments().getString("parent_class"));
        adapter = new ChapterListAdapter(getActivity().getApplicationContext(), cursor, 0, this);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }
}
