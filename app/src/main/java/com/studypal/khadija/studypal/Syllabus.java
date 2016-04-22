package com.studypal.khadija.studypal;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;


public class Syllabus extends AppCompatActivity
        implements ColorDialogFragment.onCreateInteractionListener,
        CreateChapterDialogFragment.onCreateInteractionListener,
        SubjectFragment.assignmentCall,
        EditSubjectDialogFragment.onEditListener,
        EditChapterDialogFragment.onEditListener,
        EditColorDialogFragment.onCreateInteractionListener {

    private String currentClass;
    /*  Function returns the current parent class of activities being displayed. To only be called
     *  when a assignment fragment is being displayed.*/
    public String getParentClass() {
        return currentClass;
    }

    /* Definition variables, indicating a certain fragment. */
        /* Variable, that is changed depending on which fragment is being displayed. */
    private static final int SUBJECT_LIST_VIEW = 0;
    private static final int CHAPTER_LIST_VIEW = 1;

    private int currentFragment;
    /*  A function which receives no arguments. Returns an integer value, indicating which fragment
     *  is being displayed within the activity. */
    public int getCurrentFragment() {
        return currentFragment;
    }

    /* A function that is called when a new fragment is being displayed, which sets the currentFragment
     variable to its proper value. */
    public void setCurrentFragment(int fragment) {
        switch (fragment) {

            case SUBJECT_LIST_VIEW:
                currentFragment = fragment;
                break;

            case CHAPTER_LIST_VIEW:
                currentFragment = fragment;
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        /*Fixing elevation for lollipop users, to comply with lollipop guidelines, while allowing
         *availability with previous versions. */
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            floatingActionButton.setElevation(12);
        }

        /* Creating listener for Floating Action Button. */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Action for when Floating Action Button is clicked. */
                switch (getCurrentFragment()) {

                    case SUBJECT_LIST_VIEW: {
                        DialogFragment dialogFragment = new CreateSubjectDialogFragment();
                        dialogFragment.show(getFragmentManager(), "dialog");
                        break;
                    }

                    case CHAPTER_LIST_VIEW: {
                        DialogFragment dialogFragment = new CreateChapterDialogFragment();
                        Bundle args = new Bundle();
                        args.putString("parent_class", getParentClass());
                        dialogFragment.setArguments(args);
                        dialogFragment.show(getFragmentManager(), "dialog");
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        /* Creating initial fragment, containing the list view for all of the classes registered. */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, SubjectFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        setCurrentFragment(SUBJECT_LIST_VIEW);
    }

    @Override
    public void assignmentCallMethod(String pClass) {
        /* A class was clicked. Changing the fragment to the assignment fragment. */
        currentClass = pClass;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment, ChapterFragment.newInstance(pClass));
        transaction.addToBackStack(null);
        transaction.commit();
        setCurrentFragment(CHAPTER_LIST_VIEW);
    }

    /* Function that is called from the create class fragment. */
    @Override
    public void createClicked() {
        /* A new class was created. Recreating the class view fragment. */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit, R.anim.enter, R.anim.exit);
        transaction.replace(R.id.fragment, SubjectFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void editClassMethod() {
        /* A new class was edited. Recreating the class view fragment. */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit, R.anim.enter, R.anim.exit);
        transaction.replace(R.id.fragment, SubjectFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void editAssignmentMethod(int c) {
        switch (c) {
            case 0:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.fragment, ChapterFragment.newInstance(getParentClass()));
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
        }
    }

    public Chapter newChapter;

    @Override
    public void createAssignmentClicked(Chapter chapter) {

        newChapter = chapter;
        DatabaseChapter databaseChapter = new DatabaseChapter(getApplicationContext(), null, null, 1);
        databaseChapter.addAssignment(newChapter);

            /* Adding the count for number of chapter to the subject. */
        DatabaseSubject dbHandler = new DatabaseSubject(getApplicationContext(), null, null, 1);
        dbHandler.addQuantity(getParentClass());

            /* A new chapter was created. Recreating the chapter view fragment. */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.fragment, ChapterFragment.newInstance(getParentClass()));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction;
        switch (getCurrentFragment()) {
            /* If viewing the class view, close the app. */
            case SUBJECT_LIST_VIEW:
                finish();
                return;
            /* If viewing an assignment, return to the class view. */
            case CHAPTER_LIST_VIEW:
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit, R.anim.enter, R.anim.exit);
                transaction.replace(R.id.fragment, SubjectFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
                setCurrentFragment(SUBJECT_LIST_VIEW);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}