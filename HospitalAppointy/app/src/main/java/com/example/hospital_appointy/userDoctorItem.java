package com.example.hospital_appointy;

import java.io.Serializable;
import java.util.Date;

public class userDoctorItem implements Serializable {
    String userName;
    String doctorDepartment;
    int doctorId;
    String date;
    String doctorName;
    int doctorPrice;
    String doctorIntro;

    public userDoctorItem() {
    }

    public userDoctorItem(String userName, String doctorDepartment, int doctorId, String date, String doctorName, int doctorPrice, String doctorIntro) {
        this.userName = userName;
        this.doctorDepartment = doctorDepartment;
        this.doctorId = doctorId;
        this.date = date;
        this.doctorName = doctorName;
        this.doctorPrice = doctorPrice;
        this.doctorIntro = doctorIntro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDoctorDepartment() {
        return doctorDepartment;
    }

    public void setDoctorDepartment(String doctorDepartment) {
        this.doctorDepartment = doctorDepartment;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getDoctorPrice() {
        return doctorPrice;
    }

    public void setDoctorPrice(int doctorPrice) {
        this.doctorPrice = doctorPrice;
    }

    public String getDoctorIntro() {
        return doctorIntro;
    }

    public void setDoctorIntro(String doctorIntro) {
        this.doctorIntro = doctorIntro;
    }
}
