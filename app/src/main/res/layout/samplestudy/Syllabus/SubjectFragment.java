package layout.samplestudy.Syllabus;

import android.app.Activity;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import app.pal.study.samplestudy.*;

public class SubjectFragment extends Fragment implements SubjectListAdapter.AdapterCallback {
    public static final String COLUMN_NAME = "classname";
    public static final String COLUMN_QUANTITY = "quantity";

    Cursor cursor;
    private SubjectListAdapter adapter;
    private static assignmentCall mCall;

    public static SubjectFragment newInstance() {
        return new SubjectFragment();
    }

    @Override
    public void onMethodCallback(int call, String pClass) {
        switch (call) {
            case 0:
                DatabaseSubject db = new DatabaseSubject(getActivity(), null, null, 1);
                adapter.changeCursor(db.getSubjects());
                adapter.notifyDataSetChanged();
                break;
            case 1:
                mCall.assignmentCallMethod(pClass);
                break;
            case 2:
                DialogFragment dialogFragment = new EditSubjectDialogFragment();
                Bundle args = new Bundle();
                args.putString("name", pClass);
                dialogFragment.setArguments(args);
                dialogFragment.show(getActivity().getFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);

        DatabaseSubject dbHandler = new DatabaseSubject(getActivity(), null, null, 1);
        cursor = dbHandler.getSubjects();

        adapter = new SubjectListAdapter(getActivity().getApplicationContext(), cursor, 0, this);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCall = (assignmentCall) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    public interface assignmentCall {
        void assignmentCallMethod(String pClass);
    }
}