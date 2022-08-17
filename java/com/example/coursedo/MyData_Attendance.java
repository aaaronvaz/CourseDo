package com.example.coursedo;

public class MyData_Attendance {
    private String attendance_id;
    private String attendance_date;
    private String attendance_time;

    public MyData_Attendance(String attendance_id, String attendance_date, String attendance_time) {
        this.attendance_id = attendance_id;
        this.attendance_date = attendance_date;
        this.attendance_time = attendance_time;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public String getAttendance_date() {
        return attendance_date;
    }

    public String getAttendance_time() {
        return attendance_time;
    }
}
