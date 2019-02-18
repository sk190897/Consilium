package com.example.skambo.consilium;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;

public class Login_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Intent signupintent;
    Intent uiintent1;
    Intent uiintent2;
    FirebaseDatabase mydatabase;
    DatabaseReference value;
    String id;


    public void signin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("message", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(Login_Activity.this, "hello", Toast.LENGTH_SHORT).show();

                            updateui(user, "teacher");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("message", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void SignIn(View view) {
        EditText emailid = findViewById(R.id.emailedittext);
        String email = emailid.getText().toString();
        EditText passwordid = findViewById(R.id.passwordedittext);
        String password = passwordid.getText().toString();
        Log.i("email", email);
        if (email != null && password != null)
            signin(email, password);

    }

    public void SignUp(View view) {

        signupintent = new Intent(this, SignUpactivity.class);
        startActivity(signupintent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null)
            setContentView(R.layout.activity_login_);


    }

    public void updateui(final FirebaseUser user, String id) {

        uiintent1 = new Intent(this, MyCourses.class);
        uiintent2 = new Intent(this, Studentcourses.class);
        // uiintent.putExtra("ID",id);
        // startActivity(uiintent);
        Toast.makeText(Login_Activity.this, "hello", Toast.LENGTH_SHORT).show();
        value = mydatabase.getInstance().getReference();
        value.child("free").setValue("loggedin :)");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //Toast.makeText(SignUpactivity.this,""+snapshot.hasChild(user.getUid()+"S"),Toast.LENGTH_SHORT).show();
                    if (snapshot.hasChild(user.getUid() + "T")) {
                        startActivity(uiintent1);
                    } else if (snapshot.hasChild(user.getUid() + "S")) {
                        startActivity(uiintent2);
                    } else {
                        Log.e("error", "usr not found");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        value.addValueEventListener(valueEventListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        // value = FirebaseDatabase.getInstance().getReference();
        if (currentUser != null) {
            final String userid = currentUser.getUid();

            updateui(currentUser, id);

        }

        //updateUI(currentUser);
    }


}
