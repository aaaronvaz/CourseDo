package com.example.coursedo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GradeStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_students);

        TextView clname=findViewById(R.id.grade_ac_clname);
        TextView suname=findViewById(R.id.grade_ac_subname);
        TextView assname=findViewById(R.id.grade_ac_assname);

        Bundle tograde=getIntent().getExtras();
        String cname=tograde.getString("classname");
        String snmae=tograde.getString("subname");
        String assid=tograde.getString("ass_id");

        clname.setText("CLASSROOM NAME: "+cname);
        suname.setText("SUBJECT NAME: "+snmae);
        assname.setText("ASSESSMENT TITLE: "+assid);

        Button add=findViewById(R.id.add_grade);
        Button view=findViewById(R.id.view_grade);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GradeStudents.this);
                View view=getLayoutInflater().inflate(R.layout.grade_mks_l,null);
                builder.setView(view);
                builder.setTitle("ADD MARKS");
                builder.setCancelable(false);
                AlertDialog dialog=builder.create();
                dialog.show();

                Button cancel=view.findViewById(R.id.button28);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button add=view.findViewById(R.id.button27);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText marks=view.findViewById(R.id.grade_outofmarks);
                        String marks_txt=marks.getText().toString();
                        float mk;
                        try {
                            mk=Float.parseFloat(marks_txt);
                            Bundle bundle=new Bundle();
                            bundle.putString("classname",cname);
                            bundle.putString("subname",snmae);
                            bundle.putString("assname",assid);
                            bundle.putString("outofmarks", String.valueOf(mk));

                            Intent intent=new Intent(GradeStudents.this,GradeStudentMarks.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        catch (NumberFormatException e)
                        {
                            Toast.makeText(GradeStudents.this, ""+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                Button vc=findViewById(R.id.view_grade);
                vc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GradeStudents.this, "View Grades", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}