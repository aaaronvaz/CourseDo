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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewClassroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classroom);

        ListView listView=findViewById(R.id.course_screen);

        database_tr_and_classroom db=new database_tr_and_classroom(ViewClassroom.this);
        Cursor cursor=db.getclassdetails();

        //int cursor=0;
        if(cursor.getCount()==0)
        {
            String txt="CLASSROOM EMPTY!\nCLICK ON ADD TO ADD NEW CLASSROOM";
            sc_adapter scd=new sc_adapter(txt,ViewClassroom.this);
            listView.setAdapter(scd);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position<2)
                    {
                        //Toast.makeText(ViewClassroom.this, "ADD COURSE!", Toast.LENGTH_SHORT).show();
                        add_course();
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
            Cursor viewdata=db.getclassdetails();
            //viewdata.moveToFirst();

            ArrayList<MyData> mylist=new ArrayList<>();
            while(viewdata.moveToNext())
            {
                mylist.add(new MyData(viewdata.getString(0),viewdata.getString(1)));
            }
            //mylist.add(new MyData("dialogue","eng"));
            //mylist.add(new MyData("hci","computer science"));
            MyAdapter mad=new MyAdapter(this,mylist);
            listView.setAdapter(mad);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position<mylist.size())
                    {
                        Intent classroom=new Intent(ViewClassroom.this,Classroom_template.class);
                        Bundle details=new Bundle();
                        //Toast.makeText(ViewClassroom.this, "", Toast.LENGTH_SHORT).show();
                        details.putString("classroomname",mylist.get(position).getClassroom_name());
                        details.putString("subjectname",mylist.get(position).getSub_name());

                        classroom.putExtras(details);
                        startActivity(classroom);
                    }
                    else
                    {
                        Toast.makeText(ViewClassroom.this, "FAIL!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.add_classroom):
                add_course();
                return true;

            /*case (R.id.refresh_screen):
                refresh_screen();
                return true;*/

            case (R.id.classroom_count):
                viewclass();
                return true;

            case (R.id.delete_classroom):
                deletecourse();
                return true;


            case (R.id.help):
                //Toast.makeText(this, "Being Developed", Toast.LENGTH_SHORT).show();
                StringBuffer buffer=new StringBuffer();
                buffer.append("Welcome Again,\n\n");
                buffer.append("The screen your on is the Classroom.\n\n" +
                        " Addition of classroom can be done with the help of the Add icon present on the right side of the bar.\n\n");
                buffer.append("Deletion of the classroom is available.\n\n");
                buffer.append("On clicking the classroom, you can add details related to your classrroom,\n" +
                        " students and also its attendance and marks of different courses.\n\nTo obtain more functionality");
                buffer.append(" with different aspects of the classroom click on the options button on the right side of the action bar.\n");
                AlertDialog.Builder builder=new AlertDialog.Builder(ViewClassroom.this);
                builder.setTitle("Help");
                builder.setCancelable(true);
                builder.setMessage(buffer.toString());
                AlertDialog dialog=builder.create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void add_course() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewClassroom.this);

        View customview = getLayoutInflater().inflate(R.layout.addcourse_layout, null);
        builder.setView(customview);
        builder.setTitle("Add Course");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        database_tr_and_classroom db=new database_tr_and_classroom(ViewClassroom.this);
        Button cancel=(Button)customview.findViewById(R.id.ac_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button submit = (Button) customview.findViewById(R.id.button7);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText class_name = (EditText) customview.findViewById(R.id.classroom_name);
                EditText subname = (EditText) customview.findViewById(R.id.subject_name);

                String class_txt = class_name.getText().toString();
                String sub_txt = subname.getText().toString();

                //Toast.makeText(CourseActivity.this, "txt:"+sub_txt+course_txt, Toast.LENGTH_SHORT).show();
                Boolean course_name_present = false;
                Cursor cursor = db.getclassdetails();

                if (sub_txt.trim().length() < 2 && class_txt.trim().length() < 2) {
                    Toast.makeText(ViewClassroom.this, "ENTER COMPLETE NAME", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.getCount() != 0) {


                        cursor.moveToFirst();
                        while(cursor.isAfterLast()==false)
                        {
                            if(class_txt.toUpperCase().equals(cursor.getString(0).toUpperCase()))
                            {
                                course_name_present=true;
                                Toast.makeText(ViewClassroom.this, "CLASS ALREADY PRESENT!"+"\n"+"CHANGE CLASS NAME", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            cursor.moveToNext();
                        }
                        if(course_name_present==true)
                        {
                            int x=1;
                        }
                        else
                        {
                            long check = db.insertclassroomdetails(class_txt, sub_txt);
                            if (check == 0) {
                                Toast.makeText(ViewClassroom.this, "FAILED!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ViewClassroom.this, "DONE!", Toast.LENGTH_SHORT).show();

                                EditText class_na = (EditText) customview.findViewById(R.id.classroom_name);
                                EditText subna = (EditText) customview.findViewById(R.id.subject_name);

                                class_na.setText("");
                                subna.setText("");
                            }
                        }
                    }
                    else {
                        course_name_present = false;
                        long check = db.insertclassroomdetails(class_txt, sub_txt);
                        if (check == 0) {
                            Toast.makeText(ViewClassroom.this, "FAILED!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewClassroom.this, "DONE!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                }
            }
        });
    }
    public void viewclass() {
        database_tr_and_classroom db=new database_tr_and_classroom(ViewClassroom.this);
        int cnt=db.getclasscount();
        Toast.makeText(this, "CNT: "+cnt, Toast.LENGTH_SHORT).show();
    }
    public void deletecourse() {
        AlertDialog.Builder builder=new AlertDialog.Builder(ViewClassroom.this);
        View deletelayout=getLayoutInflater().inflate(R.layout.deletecourse,null);
        builder.setView(deletelayout);
        builder.setCancelable(false);
        builder.setTitle("DELETE COURSE");
        AlertDialog dialog=builder.create();
        dialog.show();

        Button cancel=(Button)deletelayout.findViewById(R.id.dc_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button delete=(Button)deletelayout.findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_tr_and_classroom db=new database_tr_and_classroom(ViewClassroom.this);

                EditText cname=(EditText)deletelayout.findViewById(R.id.delete_classroom_name);
                EditText sname=(EditText)deletelayout.findViewById(R.id.delete_subject_name);

                String cname_txt=cname.getText().toString();
                String sname_txt=sname.getText().toString();

                Cursor cs=db.getclassdetails();
                Boolean present=false;
                cs.moveToFirst();
                while(cs.isAfterLast()==false)
                {
                    if(cname_txt.trim().toUpperCase().equals(cs.getString(0).toUpperCase()))
                    {
                        present=true;
                        Long check=db.deleteclassroom(cname_txt,sname_txt);
                        if(check==0)
                        {
                            Toast.makeText(ViewClassroom.this, "DELETION FAILED!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ViewClassroom.this, "DELETED!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cs.moveToNext();
                }
                if(present==false)
                {
                    Toast.makeText(ViewClassroom.this, "CLASSROOM NOT FOUND!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int x=1;
                }
            }
        });
    }
}