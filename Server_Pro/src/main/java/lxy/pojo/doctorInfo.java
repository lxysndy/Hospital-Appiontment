package lxy.pojo;

import java.util.Date;

public class doctorInfo {
    String department ;
    int doctorId;
    Date date = new Date();
    String doctorName;
    int doctorPrice;
    String doctorIntro ;


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
