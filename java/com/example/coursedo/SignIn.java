package com.example.coursedo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText id, fname, lname, password, password2;
    Button submit, showpass;
    SharedPreferences sharedPreferences;
    private static final String sp_name = "login";
    private static final String sp_key = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        id = findViewById(R.id.editTextTextEmailAddress);
        fname = findViewById(R.id.editTextTextPersonName2);
        lname = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPassword2);
        int p1_input = password.getInputType();

        password2 = findViewById(R.id.editTextTextPassword);
        int p2_input = password2.getInputType();

        showpass = findViewById(R.id.button2);
        submit = findViewById(R.id.button);

        sharedPreferences = getSharedPreferences(sp_name, MODE_PRIVATE);
        String name = sharedPreferences.getString(sp_key, null);
        if (name != null)
        {
            Intent intent = new Intent(SignIn.this, HOME.class);
            startActivity(intent);
        }



        showpass.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (password.getInputType() == p1_input &&
                    password2.getInputType() == p2_input) {
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                password2.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                password.setInputType(p1_input);
                password2.setInputType(p2_input);
            }
        }
    });
        submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String id_txt = id.getText().toString();
            String fname_txt = fname.getText().toString();
            String lname_txt = lname.getText().toString();
            String password1_txt = password.getText().toString();
            String password_txt2 = password2.getText().toString();

            if (fname_txt.isEmpty() && lname_txt.isEmpty() && password1_txt.isEmpty()) {
                Toast.makeText(SignIn.this, "ENTER COMPLETE DATA", Toast.LENGTH_SHORT).show();
            } else {
                Boolean id_check = id_validate(id_txt);
                Boolean name_check = name_validate(fname_txt, lname_txt);
                Boolean pass_check = password_validate(password1_txt, password_txt2);

                TextView pass_error = findViewById(R.id.textView3);

                if (id_check == true && name_check == true && pass_check == true)
                {
                    if(password1_txt.equals(password_txt2)) {
                        pass_error.setText("Both Passwords Match!");
                        long check;
                        database_tr_and_classroom mdb = new database_tr_and_classroom(SignIn.this);
                        check = mdb.insertdata(fname_txt, lname_txt, id_txt, password1_txt);

                        if (check != 0) {
                            Toast.makeText(SignIn.this, "ACCOUNT CREATED: SUCCESSFULLY!", Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(sp_key, fname_txt);
                            editor.apply();

                            pass_error.setText("");
                            Intent intent = new Intent(getApplicationContext(), HOME.class);
                            startActivity(intent);
                        } else {
                            //Toast.makeText(MainActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        {
                            pass_error.setText("BOTH PASSWORDS ARE DIFFERENT");
                        }
                     }
                 else {
                    //Toast.makeText(MainActivity.this, "ACCOUNT CREATION FAILED!", Toast.LENGTH_SHORT).show();
                    pass_error.setText("");
                }
            }

        }
    });
}
    private boolean id_validate(String e) {
        Boolean x, y;
        x = e.contains("@");
        y = e.contains(".");

        if (x && y) {
            return true;

        } else {
            Toast.makeText(this, "ENTER VALID EMAIL", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean name_validate(String fn, String ln) {
        if (fn.length() < 2 || ln.length() < 2) {

            Toast.makeText(this, "ENTER VALID NAME", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean password_validate(String p1, String p2) {
        TextView pass_error = findViewById(R.id.textView3);

        Boolean b1 = false;


        if (p1.trim().length() >= 8)
        {
            if ((p1.contains("0") || p1.contains("1") || p1.contains("2") || p1.contains("3") ||
                    p1.contains("4") || p1.contains("5") || p1.contains("6") || p1.contains("7") ||
                    p1.contains("8") || p1.contains("9")) == true)
            {
                return true;
            }
            else
                {
                Toast.makeText(this, "PASSWORD MUST TO CONTAIN A NUMBER", Toast.LENGTH_SHORT).show();
                pass_error.setText("");
                return false;
            }
        }
        else
            {
            Toast.makeText(this, "PASSWORD MUST CONTAIN MINIMUM 8 CHARACTERS!", Toast.LENGTH_SHORT).show();
            pass_error.setText("");
            return false;
        }

    }
}