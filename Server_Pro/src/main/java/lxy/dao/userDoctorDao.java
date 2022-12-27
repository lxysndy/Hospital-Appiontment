package lxy.dao;

import lxy.pojo.userDoctorInfo;
import lxy.pojo.userlogin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class userDoctorDao {
    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/AndroidWork";
    private String username="root";
    private String password="822821";
    public List<userDoctorInfo> testSelectAll() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        Connection con= DriverManager.getConnection(url, username, password);

        String sql="select * from userdoctorinfo";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<userDoctorInfo> studentList=new ArrayList<>();
        while (rs.next()){

            String userName = rs.getString("username");
            String department = rs.getString("department");
            int doctorId = rs.getInt("doctorId");
            Date date = rs.getDate("date");
            String doctorName = rs.getString("doctorName");
            int doctorPrice = rs.getInt("doctorPrice");
            String doctorIntro  = rs.getString("doctorIntro");

            userDoctorInfo student=new userDoctorInfo();
            student.setUsername(userName);
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

    public boolean insertUserDoctor(String uname,String department,int doctorId,String date,String doctorName,int doctorPrice,String doctorIntro) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        boolean flag = false;
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        String sql = "insert into userdoctorinfo(username,department,doctorId,date,doctorName,doctorPrice,doctorIntro)" +
                "values('" + uname + "','" + department + "','" + doctorId + "','"+ date +"','"+ doctorName +"','"+ doctorPrice +"','"+ doctorIntro +"')";

        try {
            ps = con.prepareStatement(sql);
            int a = ps.executeUpdate();
            if (a > 0) {
                System.out.println("添加成功");
                flag = true;
            } else {
                System.out.println("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return flag;
    }
}
