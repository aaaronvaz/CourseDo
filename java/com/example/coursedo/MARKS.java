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

public class MARKS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        ListView list=findViewById(R.id.marks_screen);

        Bundle bundle=getIntent().getExtras();
        String classname=bundle.getString("classname");
        String subname=bundle.getString("subname");

        db_marks db=new db_marks(MARKS.this);
        Cursor view=db.viewassessmentinfor(classname,subname);
        if(view.getCount()==0)
        {
            String txt="NO ASSESSMENTS PRESENT\nADD SOME!";
            sc_adapter sc=new sc_adapter(txt,MARKS.this);
            list.setAdapter(sc);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position<2)
                    {

                        //Toast.makeText(MARKS.this, "listener", Toast.LENGTH_SHORT).show();
                        addassessment(classname,subname);
                    }
                }
            });
        }
        else
        {
            ArrayList<mydata_assessment> mylist=new ArrayList<>();

            view.moveToFirst();
            while(view.isAfterLast()==false)
            {
                mylist.add(new mydata_assessment(view.getString(0)));
                view.moveToNext();
            }
            //mylist.add(new mydata_assessment("test"));
            adaptor_assessment add=new adaptor_assessment(MARKS.this,mylist);
            list.setAdapter(add);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position>-1 && position<mylist.size())
                    {
                        Bundle bun=getIntent().getExtras();
                        String cln=bundle.getString("classname");
                        String sun=bundle.getString("subname");
                        String ass_title=mylist.get(position).getTitle();

                        Bundle tograde=new Bundle();
                        tograde.putString("classname",cln);
                        tograde.putString("subname",sun);
                        tograde.putString("ass_id",ass_title);

                        Intent intent=new Intent(MARKS.this,GradeStudents.class);
                        intent.putExtras(tograde);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.marks,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case(R.id.add_assessment):
                Bundle bundle=getIntent().getExtras();
                String classname=bundle.getString("classname");
                String subname=bundle.getString("subname");

                addassessment(classname,subname);
                return true;
            case(R.id.delete_assessment):
                Bundle bundl=getIntent().getExtras();
                String classnam=bundl.getString("classname");
                String subnam=bundl.getString("subname");

                delete_assessment(classnam,subnam);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void delete_assessment(String c,String s)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.delete_assessm,null);
        builder.setView(view);
        builder.setTitle("DELETE ASSESSMENT");
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();

        Button delete=view.findViewById(R.id.button24);
        Button cancel=view.findViewById(R.id.button25);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=c;
                String sname=s;

                EditText txt=view.findViewById(R.id.editTextTextPersonName11);
                String txt_txt=txt.getText().toString();

                if(txt_txt.trim().length()<2)
                {
                    Toast.makeText(MARKS.this, "NAME TOO SHORT!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    db_marks db=new db_marks(MARKS.this);
                    Cursor cursor=db.viewassessmentinfor(cname,sname);
                    if(cursor.getCount()==0)
                    {
                        Toast.makeText(MARKS.this, "NO ASSESSMENT'S AVAILABLE", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Boolean presentname=false;

                        cursor.moveToFirst();
                        while(cursor.isAfterLast()==false)
                        {
                            if(txt_txt.trim().equals(cursor.getString(0)))
                            {
                                presentname=true;
                                Toast.makeText(MARKS.this, "ASSESSMENT FOUND", Toast.LENGTH_SHORT).show();

                                long check=db.deleteassessment(cname,sname,txt_txt);
                                if(check!=0)
                                {
                                    Toast.makeText(MARKS.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(MARKS.this, "DB: PROBLEM", Toast.LENGTH_SHORT).show();
                                }
                            }
                            cursor.moveToNext();
                        }
                        if(presentname==true)
                        {
                            int x=1;
                        }
                        else
                        {
                            Toast.makeText(MARKS.this, "ASSESSMENT NOT FOUND!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void addassessment(String c, String s)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(MARKS.this);
        View vs=getLayoutInflater().inflate(R.layout.add_assessm,null);
        builder.setView(vs);
        builder.setTitle("ADD ASSESSMENT");
        builder.setCancelable(false);
        AlertDialog dialog=builder.create();
        dialog.show();

        String cname=c;
        String sname=s;


        Button submit=vs.findViewById(R.id.button22);
        Button cancel=vs.findViewById(R.id.button24);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name=vs.findViewById(R.id.editTextTextPersonName10);
                String txt=name.getText().toString();
                if(txt.trim().length()<2)
                {
                    Toast.makeText(MARKS.this, "NAME TOO SHORT!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db_marks db=new db_marks(MARKS.this);
                    Cursor cursor=db.viewassessmentinfor(cname,sname);
                    if(cursor.getCount()==0)
                    {
                        long check=db.addassessment(cname,sname,txt);
                        if(check!=0)
                        {
                            Toast.makeText(MARKS.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MARKS.this, "Failed to Add!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Boolean presentname=false;
                        cursor.moveToFirst();
                        while(cursor.isAfterLast()==false)
                        {
                            if(txt.equals(cursor.getString(0)))
                            {
                                presentname=true;
                                Toast.makeText(MARKS.this, "ASSESSMENT WITH THIS NAME IS ALREADY PRESENT!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            cursor.moveToNext();
                        }
                        if(presentname)
                        {
                            int x=1;
                        }
                        else
                        {
                            long check=db.addassessment(cname,sname,txt);
                            if(check!=0)
                            {
                                Toast.makeText(MARKS.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MARKS.this, "Failed to Add!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}