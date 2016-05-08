package com.studypal.khadija.studypal.StudyTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.studypal.khadija.studypal.R;



public class CreateStudySubjectDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_study_subject, null);
        builder.setView(view)

                /* Adding the action buttons, with listeners. */
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editTitle = (EditText) view.findViewById(R.id.chapterName);
                     /*   if (editTitle.getText().length() != 0) {
                            DatabaseSubject dbHandler = new DatabaseSubject(getActivity().getApplicationContext(), null, null, 1);
                            //if (dbHandler))
                            //Create Chapter
                            EditText editDescription = (EditText) view.findViewById(R.id.chapterDescription);
                            Chapter chapter = new Chapter(getArguments().getString("parent_class"), editTitle.getText().toString(), editDescription.getText().toString());
                            mListener.createChapterClicked(chapter);

                        }
                        else*/
                            Toast.makeText(getActivity(), "Chapter names must have at least one character.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateStudySubjectDialogFragment.this.getDialog().cancel();
                        // Cancels ...
                    }
                });

        builder.setTitle("Choose a Subject");

        return builder.create();
    }


}
