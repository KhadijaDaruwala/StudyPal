package layout.samplestudy.Syllabus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class EditColorDialogFragment extends DialogFragment {

    String colors[] = {"Red", "Pink", "Purple", "Blue", "Cyan", "Teal", "Green", "Yellow", "Orange"};
    private onCreateInteractionListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose a theme color")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseSubject db = new DatabaseSubject(getActivity().getApplicationContext(),
                                null, null, 1);
                        db.changeColor(getArguments().getString("name"), which);
                        db.close();
                        mListener.createClicked();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onCreateInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMainFragmentInteractionListener");
        }
    }

    public interface onCreateInteractionListener {
        void createClicked();
    }
}