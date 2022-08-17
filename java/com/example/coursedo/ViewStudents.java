package com.example.coursedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class ViewStudents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        ListView list = findViewById(R.id.students_screen);

        Bundle bundle = getIntent().getExtras();
        String class_name = bundle.getString("classname");
        String sub_name = bundle.getString("subname");

        db_about_student db = new db_about_student(ViewStudents.this);

        //String id=class_name+sub_name;
        Cursor cursor = db.viewstudentsdetails(class_name, sub_name);
        Toast.makeText(this, "Count: " + cursor.getCount(), Toast.LENGTH_SHORT).show();
        if (cursor.getCount() == 0) {
            String txt = "List Empty!" + "\n" + "Enter Some Names!";
            sc_adapter sad = new sc_adapter(txt, ViewStudents.this);
            list.setAdapter(sad);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position > -1 && position < 2) {
                        Bundle b = getIntent().getExtras();
                        String cd = b.getString("classname");
                        String st = b.getString("subname");
                        addstudents(cd, st);
                    } else {
                        int x = 1;
                    }
                }
            });
        } else {
            Cursor cs = db.viewstudentsdetails(class_name, sub_name);

            ListView lst = findViewById(R.id.students_screen);
            ArrayList<MyData_Student> arrayList = new ArrayList<>();

            cs.moveToFirst();
            while (cs.isAfterLast() == false) {
                arrayList.add(new MyData_Student(cs.getString(0), cs.getString(1), cs.getString(2)));
                cs.moveToNext();
            }

            //arrayList.add(new MyData_Student("dsd","sddsd","888"));
            //arrayList.add(new MyData_Student("773","fsbs","jdauhau"));
            myadapter_stud msd = new myadapter_stud(ViewStudents.this, arrayList);
            lst.setAdapter(msd);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.add_students):
                Bundle bundle = getIntent().getExtras();

                String class_name = bundle.getString("classname");
                String sub_name = bundle.getString("subname");

                addstudents(class_name, sub_name);
                return true;
            case (R.id.edit_students):
                Bundle bundle1 = getIntent().getExtras();

                String c = bundle1.getString("classname");
                String s = bundle1.getString("subname");

                edit_students(c, s);
                return true;
            case (R.id.remove_students):
                Bundle bundle2 = getIntent().getExtras();

                String cn = bundle2.getString("classname");
                String sn = bundle2.getString("subname");
                remove_student(cn, sn);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void remove_student(String cn, String sn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.delete_student_name, null);
        builder.setView(view);
        builder.setTitle("DELETE STUDENT");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText id = view.findViewById(R.id.editTextTextPersonName9);
        Button delete = view.findViewById(R.id.button18);
        Button cancel = view.findViewById(R.id.button19);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        db_about_student db = new db_about_student(ViewStudents.this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_txt = id.getText().toString();
                if (id_txt.trim().length() == 0) {
                    Toast.makeText(ViewStudents.this, "ID/ROLL NO. CANNOT BE LEFT EMPTY!", Toast.LENGTH_SHORT).show();
                } else {
                    String c = cn;
                    String s = sn;
                    Cursor cs = db.getstudentname(c, s, id_txt);
                    if (cs.getCount() == 0) {
                        Toast.makeText(ViewStudents.this, "ID NOT FOUND!", Toast.LENGTH_SHORT).show();
                    } else {
                        long check = db.deletestudent(c, s, id_txt);
                        if (check != 0) {
                            Toast.makeText(ViewStudents.this, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(ViewStudents.this, "Failed to Delete!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void edit_students(String c, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.edit_stud_name, null);
        builder.setView(view);
        builder.setTitle("EDIT STUDENTS NAME");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText id = view.findViewById(R.id.editTextTextPersonName6);
        EditText fname = view.findViewById(R.id.editTextTextPersonName7);
        EditText lname = view.findViewById(R.id.editTextTextPersonName8);

        Button submit = view.findViewById(R.id.button16);
        Button cancel = view.findViewById(R.id.button17);

        db_about_student db = new db_about_student(ViewStudents.this);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_txt = id.getText().toString();
                String fname_txt = fname.getText().toString();
                String lname_txt = lname.getText().toString();

                Boolean idpass = false;
                Boolean namepass = false;

                if (id_txt.trim().length() == 0) {
                    Toast.makeText(ViewStudents.this, "ID/ROLL NO. IS COMPULSORY", Toast.LENGTH_SHORT).show();
                } else {
                    String cname = c;
                    String sname = s;
                    Cursor cursor = db.getstudentname(c, s, id_txt);
                    if (cursor.getCount() == 0) {
                        Toast.makeText(ViewStudents.this, "ID/ROLL NO. NOT FOUND!!" + "\n" + "TRY AGAIN!", Toast.LENGTH_SHORT).show();
                    } else {
                        cursor.moveToFirst();
                        String fname = cursor.getString(0);
                        String lname = cursor.getString(1);
                        Toast.makeText(ViewStudents.this, "STUDENT FOUND:" + "\n" + fname + " " + lname, Toast.LENGTH_SHORT).show();
                        idpass = true;

                        //cursor.moveToNext();

                    }
                }
                if (fname_txt.trim().length() < 2 && lname_txt.trim().length() < 2) {
                    Toast.makeText(ViewStudents.this, "ENTER COMPLETE NAME", Toast.LENGTH_SHORT).show();
                } else {
                    namepass = true;
                }
                if (idpass && namepass) {
                    long check = db.editstudentsnames(c, s, id_txt, fname_txt, lname_txt);
                    if (check != 0) {
                        Toast.makeText(ViewStudents.this, "UPDATED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewStudents.this, "UPDATION FAILED!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void addstudents(String class_name, String sub_name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudents.this);
        View vs = getLayoutInflater().inflate(R.layout.add_student, null);
        builder.setView(vs);
        builder.setTitle("ADD STUDENT");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText id = vs.findViewById(R.id.editTextTextPersonName);
        EditText fname = vs.findViewById(R.id.editTextTextPersonName4);
        EditText lname = vs.findViewById(R.id.editTextTextPersonName5);

        Button submit = vs.findViewById(R.id.button14);
        Button cancel = vs.findViewById(R.id.button15);

        String classroomname = class_name;
        String subjectname = sub_name;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_txt = id.getText().toString();
                String fname_txt = fname.getText().toString();
                String lname_txt = lname.getText().toString();
                Boolean idpass = true;
                Boolean namepass = false;

                if (id_txt.trim().length() == 0) {
                    Toast.makeText(ViewStudents.this, "ID/ROLL NO IS COMPULSORY", Toast.LENGTH_SHORT).show();

                } else {
                    String c = classroomname;
                    String s = subjectname;
                    db_about_student abcd = new db_about_student(ViewStudents.this);

                    Cursor cursor = abcd.viewstudentsdetails(c, s);
                    cursor.moveToFirst();
                    while (cursor.isAfterLast() == false) {
                        if (id_txt.trim().toUpperCase().equals(cursor.getString(1).toUpperCase())) {
                            Toast.makeText(ViewStudents.this, "ID OF THIS STUDENT IS ALREADY PRESENT", Toast.LENGTH_SHORT).show();
                            idpass = false;
                            break;
                        } else {
                            cursor.moveToNext();
                        }
                    }
                    if (idpass == false) {
                        int x = 1;
                    } else {
                        idpass = true;
                    }

                }
                if (fname_txt.trim().length() < 2 || lname_txt.trim().length() < 2) {
                    Toast.makeText(ViewStudents.this, "NAME ENTERED IS SHORT!", Toast.LENGTH_SHORT).show();
                } else {
                    namepass = true;
                }
                if (idpass == true && namepass == true) {
                    db_about_student x = new db_about_student(ViewStudents.this);

                    String id = classroomname + subjectname;

                    long check = x.addstudent(id, id_txt, fname_txt, lname_txt);
                    if (check != 0) {
                        Toast.makeText(ViewStudents.this, "ADDED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();

                        EditText id_f = vs.findViewById(R.id.editTextTextPersonName);
                        EditText fname_f = vs.findViewById(R.id.editTextTextPersonName4);
                        EditText lname_f = vs.findViewById(R.id.editTextTextPersonName5);

                        id_f.setText("");
                        fname_f.setText("");
                        lname_f.setText("");
                    } else {
                        Toast.makeText(ViewStudents.this, "ADDITION OF STUDENT FAILED!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int y = 1;
                }

            }
        });

    }
}

