package com.example.coursedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        Bundle bundle=getIntent().getExtras();
        String classname=bundle.getString("classname");
        String subname=bundle.getString("subname");

        ListView list=findViewById(R.id.attendance_screen);

        //Toast.makeText(this, classname+":"+subname, Toast.LENGTH_SHORT).show();

        db_attendance_markattend db=new db_attendance_markattend(StudentAttendance.this);

        Cursor cursor=db.getattendance(classname,subname);
        if(cursor.getCount()==0)
        {
            String txt="NO DATA AVAILABLE!"+"\n"+"ADD SOME!";
            sc_adapter sad=new sc_adapter(txt,StudentAttendance.this);
            list.setAdapter(sad);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position<2)
                    {
                        Bundle bundle2=getIntent().getExtras();
                        String cl=bundle2.getString("classname");
                        String su=bundle2.getString("subname");
                        add_attendance(cl,su);
                    }
                    else
                    {
                        int x=1;
                    }
                }
            });
        }
        else
        {
            Cursor cs=db.getattendance(classname,subname);

            ArrayList<MyData_Attendance> mylist=new ArrayList<>();

            cs.moveToFirst();
            while(cs.isAfterLast()==false)
            {
                mylist.add(new MyData_Attendance(cs.getString(0),cs.getString(1),cs.getString(2)));
                cs.moveToNext();
            }
            //mylist.add(new MyData_Attendance("01","23-23-23","11-11"));

            myadapter_attendance mad=new myadapter_attendance(this,mylist);
            list.setAdapter(mad);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position< mylist.size())
                    {
                        Bundle bundle007=getIntent().getExtras();
                        String cn=bundle007.getString("classname");
                        String sn=bundle007.getString("subname");

                        String attendance_id=mylist.get(position).getAttendance_id();

                        Bundle bund=new Bundle();
                        bund.putString("cname",cn);
                        bund.putString("sname",sn);
                        bund.putString("Attend_id",attendance_id);
                        Intent intent=new Intent(StudentAttendance.this,MarkStudentAttendance.class);
                        intent.putExtras(bund);
                        startActivity(intent);
                    }
                    else {
                        int x=007;
                    }
                }
            });

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.attendance_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case(R.id.add_attendance):
                Bundle bundle1=getIntent().getExtras();
                String classname=bundle1.getString("classname");
                String subname=bundle1.getString("subname");
                add_attendance(classname,subname);
                return true;
            case(R.id.delete_attendance):
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void add_attendance(String classname, String subname) {
        AlertDialog.Builder builder=new AlertDialog.Builder(StudentAttendance.this);
        View vs=getLayoutInflater().inflate(R.layout.add_attendace_layout,null);
        builder.setView(vs);
        builder.setTitle("ADD ATTENDANCE DETAILS");
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();

        Button cancel=vs.findViewById(R.id.button21);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button submit=vs.findViewById(R.id.button20);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c=classname;
                String s=subname;
                DatePicker dp=new DatePicker(StudentAttendance.this);
                TimePicker tp=new TimePicker(StudentAttendance.this);

                tp.setIs24HourView(false);
                String date,time;
                int day,month,year,hr,mins;
                String am_pm;

                day=dp.getDayOfMonth();
                month=dp.getMonth();
                year=dp.getYear();
                date=day+"-"+month+"-"+year;

                hr=tp.getCurrentHour();
                if(hr<12)
                {
                    am_pm="AM";
                }
                else
                {
                    am_pm="PM";
                }
                mins=tp.getCurrentMinute();
                time=hr+":"+mins+" "+am_pm;

                db_attendance_markattend db=new db_attendance_markattend(StudentAttendance.this);
                long check=db.addattendance(c,s,date,time);
                if(check!=0)
                {
                    Toast.makeText(StudentAttendance.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(StudentAttendance.this, "Failed to Add!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}