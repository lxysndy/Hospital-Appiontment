package lxy.dao;

import lxy.pojo.doctorInfo;
import lxy.pojo.userDoctorInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class doctorDao {
    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/AndroidWork";
    private String username="root";
    private String password="822821";
    public List<doctorInfo> testSelectAll() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        Connection con= DriverManager.getConnection(url, username, password);

        String sql="select * from doctorinfo";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<doctorInfo> studentList=new ArrayList<>();
        while (rs.next()){

            String department = rs.getString("department");
            int doctorId = rs.getInt("doctorId");
            Date date = rs.getDate("date");
            String doctorName = rs.getString("doctorName");
            int doctorPrice = rs.getInt("doctorPrice");
            String doctorIntro  = rs.getString("doctorIntro");

            doctorInfo student=new doctorInfo();
            student.setDepartment(department);
            student.setDoctorId(doctorId);
            student.setDate(date);
            student.setDoctorName(doctorName);
            student.setDoctorPrice(doctorPrice);
            student.setDoctorIntro(doctorIntro);

            studentList.add(student);
        }

        if(rs!=null){
            rs.close();
        }
        if(pstm!=null){
            pstm.close();
        }
        if(con!=null){
            con.close();
        }
        return studentList;
    }
}
