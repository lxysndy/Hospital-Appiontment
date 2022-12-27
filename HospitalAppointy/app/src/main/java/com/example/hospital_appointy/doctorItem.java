package com.example.hospital_appointy;

import java.io.Serializable;

public class doctorItem implements Serializable {
    String doctorDepartment;
    int doctorId;
    String date;
    String doctorName;
    int doctorPrice;
    String doctorIntro;

    public doctorItem() {
    }

    public doctorItem(String doctorDepartment, int doctorId, String date, String doctorName, int doctorPrice, String doctorIntro) {
        this.doctorDepartment = doctorDepartment;
        this.doctorId = doctorId;
        this.date = date;
        this.doctorName = doctorName;
        this.doctorPrice = doctorPrice;
        this.doctorIntro = doctorIntro;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
