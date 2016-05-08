package com.studypal.khadija.studypal.Syllabus;

public class Subject {

    private int _id;
    private String _subjectname;
    private int _quantity;
    private int _color;

    public Subject() {
    }

    public Subject(int id, String subjectname, int quantity, int color) {
        this._id = id;
        this._subjectname = subjectname;
        this._quantity = quantity;
        this._color = color;
    }

    public Subject(String subjectname, int quantity, int color) {
        this._subjectname = subjectname;
        this._quantity = quantity;
        this._color = color;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setSubjectName(String subjectname) {
        this._subjectname = subjectname;
    }

    public String getSubjectName() {
        return this._subjectname;
    }

    public void setQuantity(int quantity) {
        this._quantity = quantity;
    }

    public int getQuantity() {
        return this._quantity;
    }

    public void setColor(int color) {
        this._color = color;
    }

    public int getColor() {
        return this._color;
    }
}
