package lxy.dao;

import lxy.pojo.userlogin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/AndroidWork";
    private String username="root";
    private String password="822821";
    public List<userlogin> testSelectAll() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        Connection con= DriverManager.getConnection(url, username, password);

        String sql="select * from userlogin";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<userlogin> studentList=new ArrayList<>();
        while (rs.next()){

            String stuName=rs.getString("username");
            String stuPwd=rs.getString("password");

            userlogin student=new userlogin();
            student.setUsername(stuName);
            student.setPassword(stuPwd);

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

    public boolean insertUser(String uname,String pwd) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        boolean flag = false;
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        String sql = "insert into userlogin(username,password)" +
                "values('" + uname + "','" + pwd + "')";

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

