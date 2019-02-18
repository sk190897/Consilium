package com.example.skambo.consilium;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class teacher_course_add extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText coursecodeeditText;
    EditText coursenameedittext;
    EditText inscodeedittexxt;
    EditText insnameedittext;
    Spinner spinner;
    FirebaseDatabase mydatabase;
    static DatabaseReference value;
    Intent succcode;
    long test;

    public int generatecode() {

        Random random = new Random();
        return random.nextInt(10);

    }


    public void writedatabase(courseinfo courseinfo, String code) {
        FirebaseUser user = mAuth.getCurrentUser();
        value = mydatabase.getInstance().getReference();
        value.child("teachercourses").child(user.getUid()).child(code).setValue(courseinfo.coursename + " " + courseinfo.courseid);
        value.child("courses").child(code).setValue(courseinfo.coursename + " " + courseinfo.courseid);
        value.child("courses_info").child(code).setValue(courseinfo);


        Toast.makeText(this, "Successfull : ", Toast.LENGTH_SHORT).show();

        succcode = new Intent(this, MyCourses.class);

        startActivity(succcode);
    }

    public void makecourse(View view) {

        String code = "";
        int x;

        String ccode = coursecodeeditText.getText().toString();
        String cname = coursenameedittext.getText().toString();
        String icode = inscodeedittexxt.getText().toString();
        String iname = insnameedittext.getText().toString();
        //String classtype = spinner.getSelectedItem().toString();
        //spinner.getSelectedItemPosition();
        courseinfo courseinfo = new courseinfo(ccode, cname, icode, iname);

        for (int i = 0; i < 6; i++) {

            x = generatecode();
            if (x == 0) {
                do {
                    x = generatecode();
                } while (x == 0);
            }
            code = code + x;
        }
        writedatabase(courseinfo, code);
        //Toast.makeText(this , code ,Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_add);
        mAuth = FirebaseAuth.getInstance();
        coursecodeeditText = findViewById(R.id.coursecode_edittext);
        coursenameedittext = findViewById(R.id.coursename_edittext);
        inscodeedittexxt = findViewById(R.id.teachercode_edittext);
        insnameedittext = findViewById(R.id.teachername_edittext);


    }
}
