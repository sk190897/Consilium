package com.example.skambo.consilium;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class SignUpactivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Intent uiintent1;
    Intent uiintent2;
    String ID = "T";
    FirebaseDatabase mydatabase;
    static DatabaseReference value;

    public void updateui(final FirebaseUser user, String id) {
        value = mydatabase.getInstance().getReference();
        uiintent1 = new Intent(this, MyCourses.class);
        uiintent2 = new Intent(this, Studentcourses.class);
        // uiintent.putExtra("ID",id);
        // startActivity(uiintent);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //Toast.makeText(SignUpactivity.this,""+snapshot.hasChild(user.getUid()+"S"),Toast.LENGTH_SHORT).show();
                    if (snapshot.hasChild(user.getUid() + "T")) {
                        startActivity(uiintent1);
                    } else {
                        startActivity(uiintent2);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        value.addListenerForSingleValueEvent(valueEventListener);


    }

    public void writedatabase(final FirebaseUser user, String id) {
        value = mydatabase.getInstance().getReference();
        String id2;
        if (id.equals("S")) {
            id2 = "student";
            value.child("studentcourses").child(user.getUid()).setValue("");
        } else {
            id2 = "teacher";
            value.child("teachercourses").child(user.getUid()).setValue("");
        }
        value.child("users").child(user.getUid() + id).child(id).setValue(id2);


    }

    public void SignUp(View view) {

        RadioGroup radioGroup = findViewById(R.id.radiobutton);
        RadioButton teacherbutton = findViewById(R.id.teacher);
        if (teacherbutton.isChecked()) {
            //
            ID = "T";
        } else {
            ID = "S";
        }


        EditText emailid = findViewById(R.id.emailedittext);
        String email = emailid.getText().toString();
        EditText passwordid = findViewById(R.id.passwordedittext);
        String password = passwordid.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("message", "createUserWithEmail:success");
                            Toast.makeText(SignUpactivity.this, "Sign up success", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            writedatabase(user, ID);
                            updateui(user, ID);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("message", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpactivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upactivity);
        mAuth = FirebaseAuth.getInstance();
        mydatabase = FirebaseDatabase.getInstance();
    }


}
