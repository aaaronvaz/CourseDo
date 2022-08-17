package com.example.coursedo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HOME extends AppCompatActivity {

    TextView hometitle,datentime;
    ImageButton profile,classroom,help,signout;

    SharedPreferences sharedPreferences;
    private static final String sp_name="login";
    private static final String sp_key="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        hometitle=findViewById(R.id.home_title);
        datentime=findViewById(R.id.home_datetimme);

        profile=findViewById(R.id.home_myprofile);
        classroom=findViewById(R.id.home_classroom);
        help=findViewById(R.id.home_help);
        signout=findViewById(R.id.home_signout);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HOME.this, MyProfile.class);
                startActivity(intent);
            }
        });
        classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(HOME.this,ViewClassroom.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer();
                buffer.append("Welcome to CourseDo an App for new generation teachers " +
                        "developed by Rahul Morajker, Gavin Pereira and Aaron Vaz.\n\n" +
                        "A portable register as such, to reduce human" +
                        "efforts on paper with the help of technology.\n\n");
                buffer.append("This App comprises of:\n\n");
                buffer.append("My profile: \nTo know your (Tr) details and to even update them." +
                        " Updation of the name and the password is available.\n\n");
                buffer.append("Classroom:\n Classrooms represent a class to add students of a particular course/subject or" +
                        " so and mark their attendance and grade their marks throughout.\n\n");
                buffer.append("Help: The Screen your reading now.\n\n");
                buffer.append("NOTE: \nThe app is in Offline Mode. Any damage to the device or Operating System may lead to the loss of data.\n\n");
                buffer.append("Hope you will enjoy Using Our CourseDo application.\n");

                AlertDialog.Builder builder=new AlertDialog.Builder(HOME.this);
                builder.setTitle("Help");
                builder.setCancelable(true);
                builder.setMessage(buffer.toString());
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                account_signout();
            }
        });
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd E, LLL yyyy"+"\n"+"KK:mm aaa");
        String dateandtime=sdf.format(calendar.getTime());
        datentime.setText(dateandtime);
        hometitle.setText("Welcome, USER");

    }
    public void toast()
    {
        Toast.makeText(this, "Present!", Toast.LENGTH_SHORT).show();
    }

    private void account_signout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HOME.this);
        View customview = getLayoutInflater().inflate(R.layout.signout_layout, null);
        builder.setView(customview);
        builder.setTitle("ALERT!!");
        AlertDialog dialog = builder.create();
        dialog.show();

        Button yes = (Button) customview.findViewById(R.id.signoutyes);
        Button no = (Button) customview.findViewById(R.id.signoutno);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_tr_and_classroom db01=new database_tr_and_classroom(HOME.this);
                long db_01=db01.deletetrdata();
                long db__01=db01.deleteclassroomdata();

                db_about_student db02=new db_about_student(HOME.this);
                long db_02=db02.deleteaboutdata();
                long db__02=db02.deletestudentdata();

                db_attendance_markattend db03=new db_attendance_markattend(HOME.this);
                long db_03=db03.deleteattendancedata();

                db_marks db04=new db_marks(HOME.this);
                long db_04=db04.deleteassessmentdata();

                if(db_01!=0 || db__01!=0 || db_02!=0 || db__02!=0 || db_03!=0 || db_04!=0)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences(sp_name, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.remove(sp_key);
                    editor.clear();
                    editor.commit();

                    Toast.makeText(HOME.this, "Successfully Signed Out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HOME.this, SignIn.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(HOME.this, "Signing Out: Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}