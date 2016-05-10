package com.studypal.khadija.studypal.StudyTime;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.studypal.khadija.studypal.R;

public class StudyTimeRecord extends Fragment {

    public Button startButton;
    public Button stopButton;
    public Button resetButton;
    public Chronometer studyWatch;
    private long timeWhenStopped = 0;
    private boolean stopClicked;
    private String mChapterName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_studytimerecord, container, false);
        Bundle bundle = getArguments();
        mChapterName = bundle.getString("chapter_name");
        startButton = (Button) rootView.findViewById(R.id.start_button);
        stopButton = (Button) rootView.findViewById(R.id.stop_button);
        resetButton = (Button) rootView.findViewById(R.id.reset_button);
        studyWatch = (Chronometer) rootView.findViewById(R.id.chronometer);

        // the method for when we press the 'start' button
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                studyWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                studyWatch.start();
                stopClicked = false;
                return;
            }
        });

        // the method for when we press the 'reset' button
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                studyWatch.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                //   TextView secondsText = (TextView)getView().findViewById(R.id.hmsTekst);
                //  secondsText.setText("0 seconds");

            }
        });

        // the method for when we press the 'stop' button
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if (!stopClicked) {
                    // TextView secondsText = (TextView)getView().findViewById(R.id.hmsTekst);
                    timeWhenStopped = studyWatch.getBase() - SystemClock.elapsedRealtime();
                    int seconds = (int) timeWhenStopped / 1000;
                    //  secondsText.setText( Math.abs(seconds) + " seconds");
                    studyWatch.stop();
                    stopClicked = true;
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.alert_dialog);
                    dialog.show();
                    dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatabaseStudyLogs  dbStudylog = new DatabaseStudyLogs(getContext(),null,null,1);
                            StudyLog log = new StudyLog();
                            log.chaptername = mChapterName;
                            Log.d("yesbutton", mChapterName);
                            dbStudylog.addStudyLog(log);
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(),StudyTimeLogsActivity.class);
                            intent.putExtra("chapter_name", mChapterName);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    dialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });

                }
            }
        });

        setTitle();


        return rootView;

    }
    private void setTitle() {
        getActivity().setTitle("Study Time");
    }

}

