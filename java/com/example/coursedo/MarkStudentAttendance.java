package com.example.coursedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MarkStudentAttendance extends AppCompatActivity {

    ArrayList<MyData_Student> presentlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_student_attendance);

        ListView list = findViewById(R.id.marked_student_attend);

        Bundle bund = getIntent().getExtras();
        String classname = bund.getString("cname");
        String subname = bund.getString("sname");
        String attend_id = bund.getString("Attend_id");

        db_about_student db=new db_about_student(MarkStudentAttendance.this);
        Cursor cursor = db.viewstudentsdetails(classname, subname);
        if (cursor.getCount() == 0) {
            Button submit = findViewById(R.id.button23);
            submit.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No Student Names Found!", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<MyData_Student> mylist = new ArrayList<>();
            Button submit = findViewById(R.id.button23);
            submit.setVisibility(View.VISIBLE);

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                mylist.add(new MyData_Student(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
            //mylist.add(new MyData_Student("01","01","01"));
            myadaptor_markattendance md = new myadaptor_markattendance(this, mylist);
            list.setAdapter(md);


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position > -1 && position < mylist.size()) {
                        presentlist.add(new MyData_Student(mylist.get(position).getCid(), mylist.get(position).getId(), mylist.get(position).getFullname()));
                        parent.getChildAt(position).setBackgroundColor(Color.rgb(13, 163, 20));
                    } else {
                        int x = 1;
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bun = getIntent().getExtras();
                    String cnn = bun.getString("cname");
                    String snn = bun.getString("sname");
                    String aannid = bun.getString("Attend_id");

                    //ArrayList<MyData_Student> plist=presentlist;
                    /*long check;
                    db_attendance_markattend db = new db_attendance_markattend(MarkStudentAttendance.this);
                    int i = 0;
                    while (i < presentlist.size()) {
                        check = db.addmarkedattendance(cnn, snn, aannid, presentlist.get(i).getId(), presentlist.get(i).getFullname());
                        i++;
                        if (check != 0) {
                            //Toast.makeText(MarkStudentAttendance.this, "DONE!:"+ cnn + ":" + ":" + snn + ":" + ":" + aannid, Toast.LENGTH_SHORT).show();
                            Toast.makeText(MarkStudentAttendance.this, "Added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MarkStudentAttendance.this, "Failed to Add Attendance!", Toast.LENGTH_SHORT).show();

                        }
                    }*/
                    if(presentlist.size()!=0)
                    {
                        Toast.makeText(MarkStudentAttendance.this, "SUCCESSFULLY ADDED!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MarkStudentAttendance.this, "Failed to Add Attendence!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }}

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.inside_attendance,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case(R.id.view_details):
                view_details();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void view_details(){
        /*Bundle bun=getIntent().getExtras();
        String cnn=bun.getString("cname");
        String snn=bun.getString("sname");
        String aannid=bun.getString("Attend_id");
        //loc
        db_attendance_markattend db=new db_attendance_markattend(MarkStudentAttendance.this);
        Cursor cs=db.viewmarkedattendance(cnn,snn,aannid);
        if(cs.getCount()==0)
        {
            Toast.makeText(this, "NO DETAILS PRESENT!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            buffer.append("No. of Students Present: "+cs.getCount());
            buffer.append("\n");
            cs.moveToFirst();
            while(cs.isAfterLast()==false)
            {
                buffer.append("ID: "+cs.getString(0));
                buffer.append("NAME: "+cs.getString(1));
                buffer.append("\n\n");
            }*/
        String txt;
        StringBuffer buffer=new StringBuffer();

        AlertDialog.Builder builder=new AlertDialog.Builder(MarkStudentAttendance.this);
        builder.setTitle("ATTENDANCE DETAILS");
        builder.setCancelable(true);

        if(presentlist.size()==0) {
            txt = "EMPTY";
            buffer.append((txt));
            builder.setMessage(buffer.toString());
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else
        {
            buffer.append("No. of Students Present: "+presentlist.size());
            buffer.append("\n\n");
            for (int i=0;i<presentlist.size();i++)
            {
                buffer.append("ID:"+presentlist.get(i).getId());
                buffer.append("\n");
                buffer.append("NAME: "+presentlist.get(i).getFullname());
                buffer.append("\n\n");
                builder.setMessage(buffer.toString());
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        }


}}
