package com.studypal.khadija.studypal.StudyTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StudyLog {
    public int _id;
    public String _subjectname;
    public String _chaptername;

    public String _datetime;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");



    public StudyLog() {
    }
    public static String getDate() {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());

        return dateFormat.format(c.getTime());
    }
}
