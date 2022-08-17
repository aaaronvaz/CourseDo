package com.example.coursedo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Classroom_template extends AppCompatActivity {
    TextView coursetitle,subtitle;
    ImageButton about,studlist,attendance,marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_template);

        coursetitle = findViewById(R.id.ct_coursetitle);
        subtitle=findViewById(R.id.ct_subtitle);

        Bundle details=getIntent().getExtras();
        String class_name=details.getString("classroomname");
        String sub_name=details.getString("subjectname");

        coursetitle.setText("Classroom- "+class_name);
        subtitle.setText("Subject- "+sub_name);

        about=findViewById(R.id.imageButton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_about(class_name,sub_name);
            }
        });

        studlist=findViewById(R.id.imageButton2);
        studlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=class_name;
                String sname=sub_name;

                Intent intent=new Intent(Classroom_template.this,ViewStudents.class);
                Bundle bundle=new Bundle();

                bundle.putString("classname",cname);
                bundle.putString("subname",sname);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        attendance=findViewById(R.id.imageButton3);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=class_name;
                String sname=sub_name;

                Intent intent=new Intent(Classroom_template.this,StudentAttendance.class);
                Bundle bundle=new Bundle();

                bundle.putString("classname",cname);
                bundle.putString("subname",sname);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        marks=findViewById(R.id.imageButton4);
        marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=class_name;
                String sname=sub_name;

                Intent intent=new Intent(Classroom_template.this,MARKS.class);
                Bundle bundle=new Bundle();

                bundle.putString("classname",cname);
                bundle.putString("subname",sname);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
    public void view_about(String classroomname, String subjectname) {
        String cname=classroomname;
        String sname=subjectname;

        AlertDialog.Builder builder=new AlertDialog.Builder(Classroom_template.this);
        View cs= getLayoutInflater().inflate(R.layout.edit_about,null);
        builder.setView(cs);
        builder.setTitle("EDIT ABOUT");
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();

        EditText editText=cs.findViewById(R.id.about_editext);
        Button save=cs.findViewById(R.id.button9);
        Button cancel=cs.findViewById(R.id.button11);
        Button view=cs.findViewById(R.id.viewabout);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid=cname;
                String sid=sname;
                String txt=editText.getText().toString();
                if(txt.trim().length()<4)
                {
                    Toast.makeText(Classroom_template.this, "ENTER MORE DETAILS!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db_about_student db=new db_about_student(Classroom_template.this);
                    Long check=db.addclassroomaboutdetails(cid,sid,txt);
                    if(check==0)
                    {
                        Toast.makeText(Classroom_template.this, "Adding Details Failed!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Classroom_template.this, "Added Successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cid=cname;
                String sid=sname;
                Toast.makeText(Classroom_template.this, cid+":"+sid, Toast.LENGTH_SHORT).show();

                db_about_student mdb=new db_about_student(Classroom_template.this);

                Cursor cursor=mdb.viewabout(cid,sid);
                AlertDialog.Builder builder1=new AlertDialog.Builder(Classroom_template.this);
                builder1.setTitle("VIEW ABOUT");
                builder1.setCancelable(true);
                StringBuffer buffer=new StringBuffer();
                if(cursor.getCount()==0)
                {
                    //Toast.makeText(Classroom_template.this, "zero", Toast.LENGTH_SHORT).show();
                    buffer.append("NO DETAILS!");
                }
                else
                {
                    cursor.moveToFirst();
                    buffer.append(cursor.getString(0));
                    //Toast.makeText(Classroom_template.this, "n:"+cursor.getCount(), Toast.LENGTH_SHORT).show();
                }
                builder1.setMessage(buffer.toString());
                builder1.show();
            }
        });

    }
}