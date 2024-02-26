package com.example.project2;

public class Events {

    private String ename;
    private String eid;
    private String sname;
    private String place;
    private String time;

    // Default constructor
    public Events() {
        // Default constructor is needed for JavaFX TableView
    }

    // Parameterized constructor
    public Events(String ename, String eid, String sname, String place, String time) {
        this.ename = ename;
        this.eid = eid;
        this.sname = sname;
        this.place = place;
        this.time = time;
    }

    // Getters and Setters

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
