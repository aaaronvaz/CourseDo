package com.example.coursedo;

public class MyData {
    private String classroom_name;
    private String sub_name;

    public MyData(String course_name, String sub_name) {
        this.classroom_name = course_name;
        this.sub_name = sub_name;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public String getSub_name() {
        return sub_name;
    }
}
