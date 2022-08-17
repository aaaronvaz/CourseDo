package com.example.coursedo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfile extends AppCompatActivity {

    TextView namee;
    Button viewdetails,editname,oldpass,newpass;
    SharedPreferences sharedPreferences;

    private static final String sp_name="login";
    private static final String sp_key="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        namee=findViewById(R.id.myprofile_title);
        viewdetails=findViewById(R.id.button3);

        sharedPreferences=getSharedPreferences(sp_name,MODE_PRIVATE);
        String name=sharedPreferences.getString(sp_key,null);
        if(name!=null)
        {
            namee.setText("Welcome,");
        }

        database_tr_and_classroom db=new database_tr_and_classroom(MyProfile.this);

        viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = db.viewData();
                if (cursor.getCount() == 0)
                {

                }
                else
                {
                    StringBuffer buffer = new StringBuffer();
                    while (cursor.moveToNext())
                    {
                        buffer.append("ID: " + cursor.getString(0) + "\n");
                        buffer.append("FNAME: " + cursor.getString(1) + "\n");
                        buffer.append("LNAME: " + cursor.getString(2) + "\n");
                        buffer.append("FULLNAME: " + cursor.getString(3) + "\n");
                        buffer.append("EMAIL: " + cursor.getString(4) + "\n");
                        buffer.append("PASSWORD: " + cursor.getString(5) + "\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                    builder.setCancelable(true);
                    builder.setTitle("USER-DATA ENTRY");
                    builder.setMessage(buffer.toString());
                    builder.show();

                }

            }

        });

        editname=findViewById(R.id.button4);
        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder build=new AlertDialog.Builder(MyProfile.this);
                View customview=getLayoutInflater().inflate(R.layout.editname,null);
                build.setView(customview);
                build.setTitle("Edit Name");
                build.setCancelable(false);

                AlertDialog dialog=build.create();
                dialog.show();
                TextView oldname=customview.findViewById(R.id.customlayout_oldname);
                String on=db.getoldname();
                oldname.setText(on);

                Button submit=(Button)customview.findViewById(R.id.button6);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText fname=customview.findViewById(R.id.customlayout_newfname);
                        EditText lname=customview.findViewById(R.id.customlayout_newlname);

                        String id="01";
                        String fn=fname.getText().toString();
                        String ln=lname.getText().toString();
                        //Toast.makeText(MyProfile.this, "fn+ln"+fn+ln, Toast.LENGTH_SHORT).show();

                        if(fn.trim().length()>=2 && ln.trim().length()>=2)
                        {
                            long check=db.updatename(id,fn,ln);
                            if(check!=0)
                            {
                                Toast.makeText(MyProfile.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(MyProfile.this, "Updation Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MyProfile.this, "ENTER COMPLETE NAME!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                Button cancel=(Button)customview.findViewById(R.id.button8);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        Button changepass=findViewById(R.id.button5);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MyProfile.this);
                View customview=getLayoutInflater().inflate(R.layout.editpasswordlayoout,null);
                builder.setView(customview);
                builder.setTitle("Change Password");
                builder.setCancelable(false);

                AlertDialog dialog=builder.create();
                dialog.show();
                String old_db_pass=db.getoldpass();

                EditText oldp=(EditText)customview.findViewById(R.id.editTextTextPassword3);
                EditText newp=(EditText)customview.findViewById(R.id.editTextTextPassword4);

                final int inputtype_old=oldp.getInputType();
                final int inputtype_new=newp.getInputType();

                Button showpass=(Button)customview.findViewById(R.id.button10);
                showpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(oldp.getInputType()==inputtype_old && newp.getInputType()==inputtype_new)
                        {
                            oldp.setInputType(InputType.TYPE_CLASS_TEXT);
                            newp.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        else
                        {
                            oldp.setInputType(inputtype_old);
                            newp.setInputType(inputtype_new);
                        }
                    }
                });

                Button cancel=(Button)customview.findViewById(R.id.button13);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button change=(Button)customview.findViewById(R.id.button12);
                change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id="01";
                        String oldpassword_txt=oldp.getText().toString();
                        String newpassword_txt=newp.getText().toString();
                        String newpass_txt=newpassword_txt;

                        Boolean oldpass_check=false;
                        Boolean newpass_check=false;

                        if (newpass_txt.trim().length() >= 8)
                        {
                            if ((newpass_txt.contains("0") || newpass_txt.contains("1")|| newpass_txt.contains("2") ||newpass_txt.contains("3")||
                                    newpass_txt.contains("4") || newpass_txt.contains("5") || newpass_txt.contains("6") || newpass_txt.contains("7") ||
                                    newpass_txt.contains("8") || newpass_txt.contains("9"))==true)
                            {
                                newpass_check=true;
                            }
                            else {
                                Toast.makeText(MyProfile.this, "PASSWORD MUST CONTAIN A NUMBER!", Toast.LENGTH_SHORT).show();
                                newpass_check=false;
                            }
                        }
                        else
                        {
                            Toast.makeText(MyProfile.this, "PASSWORD MUST CONTAIN MINIMUM 8 CHARACTERS!", Toast.LENGTH_SHORT).show();
                            newpass_check=false;
                        }
                        if(oldpassword_txt.equals(old_db_pass))
                        {
                            oldpass_check=true;
                        }
                        else
                        {
                            Toast.makeText(MyProfile.this, "Old password does not match!", Toast.LENGTH_SHORT).show();
                            oldpass_check=false;
                        }
                        if(oldpass_check==true && newpass_check==true)
                        {
                            long check=db.updatepassword(id,newpass_txt);
                            if(check!=0)
                            {
                                Toast.makeText(MyProfile.this, "PASSWORD UPDATED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(MyProfile.this, "PASSWORD UPDATION FAILED!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MyProfile.this, "PASSWORD VALIDATION FAILED!..RECHECK-", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}