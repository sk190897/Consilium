package com.example.skambo.consilium;

public class courseinfo {

    String coursename;
    String courseid;
    String insid;
    String insname;
    String classtype;

    courseinfo() {
        //to get items

    }

    courseinfo(String courseid, String coursename, String insid, String insname) {
        this.coursename = coursename;
        this.courseid = courseid;
        this.insid = insid;
        this.insname = insname;
        // this.classtype = classtype;


    }

}
