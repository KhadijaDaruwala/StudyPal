package com.studypal.khadija.studypal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class StudyTimeFragment extends Fragment {

    public Button startButton;
    public Button stopButton;
    public Button resetButton;
    public Chronometer studyWatch;
    private long timeWhenStopped = 0;
    private boolean stopClicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_studytime, container, false);

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
                }

            }
        });

        return rootView;

    }

}


