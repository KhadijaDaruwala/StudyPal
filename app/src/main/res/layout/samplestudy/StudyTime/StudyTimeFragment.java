package layout.samplestudy.StudyTime;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.pal.study.samplestudy.R;


public class StudyTimeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_studytime, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button newButton = (Button) getActivity().findViewById(R.id.start_study);
        newButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        DialogFragment dialogFragment = new CreateStudySubjectDialogFragment();
                        Bundle args = new Bundle();
                        // args.putString("parent_class", getParentClass());
                        dialogFragment.setArguments(args);
                        dialogFragment.show(getActivity().getFragmentManager(), "dialog");
                    }
                }

        );

    }

}