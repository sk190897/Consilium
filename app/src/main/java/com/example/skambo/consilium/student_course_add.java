package com.example.skambo.consilium;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class student_course_add extends AppCompatActivity {
    EditText coursecodeeditText;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase mydatabase;
    static DatabaseReference value;
    Intent intent;
    boolean id = false;

    public void onenroll(View view) {
        String coursecode = coursecodeeditText.getText().toString();
        if (coursecode.length() < 6) {
            Toast.makeText(this, "Incorrect Course code ", Toast.LENGTH_SHORT).show();
        } else {
            checkcoursecode(coursecode);
        }
    }

    public void changeui() {
        intent = new Intent(this, Studentcourses.class);
        startActivity(intent);

    }

    public void writedatabase(String code, String name) {
        user = mAuth.getCurrentUser();
        value = mydatabase.getInstance().getReference("studentcourses/" + user.getUid());
        value.child(code).setValue(name);

    }


    public void checkcoursecode(final String code) {

        user = mAuth.getCurrentUser();
        value = mydatabase.getInstance().getReference("");
        value.child("free").setValue("logged in :)");
        value = mydatabase.getInstance().getReference("courses/");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (code.equals(snapshot.getKey())) {
                        id = true;
                        toast(id);
                        writedatabase(code, snapshot.getValue().toString());
                        changeui();
                        break;
                    }
                }
                toast(id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        value.addValueEventListener(valueEventListener);


    }

    public void toast(boolean id) {
        if (id == false) {
            Toast.makeText(this, "Course does not exist :404", Toast.LENGTH_SHORT).show();
            coursecodeeditText.clearComposingText();
        } else {
            Toast.makeText(this, "Succesfully Enrolled", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_add);
        coursecodeeditText = findViewById(R.id.courseenrolledittext);
        mAuth = FirebaseAuth.getInstance();
    }
}
