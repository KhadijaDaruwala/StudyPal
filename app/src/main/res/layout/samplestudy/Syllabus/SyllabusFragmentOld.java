package layout.samplestudy.Syllabus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import app.pal.study.samplestudy.R;

public class SyllabusFragmentOld extends Fragment implements View.OnClickListener {
    //  private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_syllabus, container, false);

        setTitle();
        return rootView;

    }

    private void setTitle() {
        getActivity().setTitle("SyllabusActivity");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button newBlockButton = (Button) getActivity().findViewById(R.id.new_block_button);
        newBlockButton.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), Syllabus.class);
        startActivity(intent);
    }

}
