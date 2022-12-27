package test;

import lxy.pojo.userlogin;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class insert {
    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/AndroidWork";
    private String username="root";
    private String password="822821";
    @Test
    public void insertUser() throws ClassNotFoundException, SQLException {
        String username = "xy";
        String password = "123";
        List<userlogin> userTextList = new ArrayList<>();
        PreparedStatement ps = null;
        boolean flag = false;
        Class.forName(driver);
        //3.使用驱动管理器来获得连接---获得一个数据库连接对象Connection
        Connection con = DriverManager.getConnection(url, username, password);
        //4.使用Connection创建PreparedStatement预处理对象---PreparedStatement对象可以执行带 ? 的sql语句
        String sql = "insert into userlogin(username,password)" +
                "values('" + username + "','" + password + "')";

        try {
            ps = con.prepareStatement(sql);//把写好的sql语句传递到数据库，让数据库知道我们要干什么
            int a = ps.executeUpdate();//这个方法用于改变数据库数据，a代表改变数据库的条数
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
    }
}
