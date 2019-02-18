package com.example.skambo.consilium;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Studentcourses extends AppCompatActivity {

    Intent signoutintent;
    TextView textView;
    Intent addcourse;
    FirebaseAuth mAuth;
    FirebaseUser currentuser;
    FirebaseDatabase mydatabase;
    static DatabaseReference value;
    ArrayList<String> arrayList = null;
    ArrayAdapter<String> adapter;
    ListView listView;
    Intent coursepage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentcourses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Courses");
        listView = findViewById(R.id.studentcourseslistview);

        arrayList = new ArrayList<String>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcourse = new Intent(Studentcourses.this, student_course_add.class);
                startActivity(addcourse);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadcourse(parent.getItemAtPosition(position).toString(), true);
            }
        });

        textView = findViewById(R.id.nametextview2);
        textView.setText("WELCOME! Student");
        listView = findViewById(R.id.studentcourseslistview);

    }

    public void loadcourse(String test, boolean id) {
        coursepage = new Intent(this, current_course.class);
        coursepage.putExtra("course", test);
        startActivity(coursepage);
        //Toast.makeText(this,test+"",Toast.LENGTH_SHORT).show();

    }

    public void SignOut() {

        FirebaseAuth.getInstance().signOut();
        signoutintent = new Intent(this, Login_Activity.class);
        startActivity(signoutintent);

    }

    public void toast(String key) {
        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signoutmenu) {
            SignOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateui(FirebaseUser user) {
        value = mydatabase.getInstance().getReference();
        value.child("free").setValue("loggedin :)");
        value = mydatabase.getInstance().getReference("studentcourses/" + user.getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    arrayList.add(snapshot.getKey() + " - " + snapshot.getValue().toString());
                }
                if (arrayList != null)
                    updatelist(arrayList);
                else {
                    //toast();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        value.addValueEventListener(valueEventListener);


    }

    public void updatelist(ArrayList<String> arrayList1) {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        listView.setAdapter(adapter);
        arrayList = null;
    }

    public void makelist(String item) {
        arrayList.add(item);

    }

    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();

        updateui(currentuser);

    }


}
