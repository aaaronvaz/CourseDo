package com.example.coursedo;

public class Mydata_marksofstudents {
    private String stud_id;
    private String stud_name;
    private double marks;

    public Mydata_marksofstudents(String stud_id, String stud_name, double marks) {
        this.stud_id = stud_id;
        this.stud_name = stud_name;
        this.marks = marks;
    }

    public String getStud_id() {
        return stud_id;
    }

    public String getStud_name() {
        return stud_name;
    }

    public double getMarks() {
        return marks;
    }
}
