package com.example.coursedo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GradeStudentMarks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_student_marks);

        ListView screen=findViewById(R.id.screenforstudents);
        ArrayList<MyData_Student> mylist=new ArrayList<>();

        Bundle bundle=getIntent().getExtras();
        String classname=bundle.getString("classname");
        String subname=bundle.getString("subname");
        String assname=bundle.getString("assname");
        double outofmarks= Double.parseDouble(bundle.getString("outofmarks"));

        //Toast.makeText(this, classname+":"+subname+":"+assname+":"+outofmarks, Toast.LENGTH_SHORT).show();
        db_about_student db=new db_about_student(GradeStudentMarks.this);
        Cursor cs=db.viewstudentsdetails(classname,subname);
        if(cs.getCount()==0)
        {
            Toast.makeText(this, "No Names Present!", Toast.LENGTH_SHORT).show();
            Button sbmt=findViewById(R.id.button29);
            sbmt.setVisibility(View.INVISIBLE);
        }
        else
        {
            db_about_student bb=new db_about_student(GradeStudentMarks.this);
            Cursor c=bb.viewstudentsdetails(classname,subname);
            double totalmks=outofmarks;
            Button sbmt=findViewById(R.id.button29);
            sbmt.setVisibility(View.VISIBLE);

            double wholecounnt=cs.getCount();
            ArrayList<Mydata_marksofstudents> lol=new ArrayList<>();
            int dynamiccount;

            c.moveToFirst();
            while(c.isAfterLast()==false)
            {
                mylist.add(new MyData_Student(c.getString(0),c.getString(1),c.getString(2)));
                c.moveToNext();
            }
            adaptor_forstudents adp=new adaptor_forstudents(GradeStudentMarks.this,mylist);
            screen.setAdapter(adp);

            /*ListView lv=screen;
            View v;
            TextView id,name;
            EditText mks;
            String itxt,ntxt;
            double mktxt;

            for(int i=0;i<wholecounnt;i++)
            {
                v=lv.getChildAt(i);

                id=v.findViewById(R.id.grade_temp_id);
                name=v.findViewById(R.id.grade_temp_title);
                mks=v.findViewById(R.id.grade_temp_grade);

                itxt=id.getText().toString();
                ntxt=name.getText().toString();

                try {
                    mktxt=Double.parseDouble(String.valueOf(mks.getText().toString()));
                    if(mktxt>totalmks)
                    {
                        Toast.makeText(this, "Number greater than total marks: Total Marks:"+totalmks, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        lol.add(new Mydata_marksofstudents(itxt,ntxt,mktxt));
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(this, "ENTER NUMBER ONLY:-at-id:"+itxt, Toast.LENGTH_SHORT).show();
                }

            }*/
            dynamiccount=lol.size();

            sbmt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(dynamiccount!=wholecounnt)
                    {
                        Toast.makeText(GradeStudentMarks.this, "ENTER MARKS OF ALL STUDENTS!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(GradeStudentMarks.this, "DONE!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}