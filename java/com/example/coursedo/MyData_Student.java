package com.example.coursedo;

public class MyData_Student {
    private String id;
    private String fname;
    private String lname;
    private String fullname;
    private String cid;

    public MyData_Student(String cid,String id,String fullname) {
        this.cid=cid;
        this.id = id;
        //this.fname = fname;
        //this.lname = lname;
        this.fullname = fullname;

    }

    public String getCid() {
        return cid;
    }

    public String getId() {
        return id;
    }

   /* public String getFname() { return fname; }

    public String getLname() {
        return lname;
    }*/

    public String getFullname() {
        return fullname;
    }
}
