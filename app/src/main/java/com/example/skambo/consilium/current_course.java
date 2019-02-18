package com.example.skambo.consilium;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class current_course extends AppCompatActivity {
    ImageView scheduleimageview;
    ImageView infoimageview;
    ImageView discussionimageview;
    ImageView quizimageview;
    ImageView materialimageview;
    ImageView attendanceimageview;
    String coursename;
    Toolbar toolbar;
    TextView textView;
    Intent currentcourse;
    Intent courseinfo;
    String coursecode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_course);
        scheduleimageview = findViewById(R.id.scheduleimageview);
        infoimageview = findViewById(R.id.courseinfo);
        discussionimageview = findViewById(R.id.discussionimageview);
        quizimageview = findViewById(R.id.quizimageview);
        materialimageview = findViewById(R.id.materialimageview);
        attendanceimageview = findViewById(R.id.attendanceimageview);
        textView = findViewById(R.id.coursetextview);
        currentcourse = new Intent(this, current_course.class);
        Intent intent = getIntent();
        //toolbar = findViewById(R.id.toolbar);
        coursename = intent.getStringExtra("course");
        coursecode = coursename.substring(0, 6);
        Toast.makeText(this, coursename, Toast.LENGTH_SHORT).show();
        //toolbar.setTitle(coursename);
        textView.setText(coursename);
    }

    public void courseinfo(View view) {
        //courseinfo = new Intent(this,);
        courseinfo = new Intent(this, Courseinfo.class);
        courseinfo.putExtra("code", coursecode);
        startActivity(courseinfo);
    }

    public void comingsoon(View view) {
        Toast.makeText(this, "Coming Soon :) ", Toast.LENGTH_SHORT).show();

    }
}
