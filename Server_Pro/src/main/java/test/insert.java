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
        //3.ʹ���������������������---���һ�����ݿ����Ӷ���Connection
        Connection con = DriverManager.getConnection(url, username, password);
        //4.ʹ��Connection����PreparedStatementԤ�������---PreparedStatement�������ִ�д� ? ��sql���
        String sql = "insert into userlogin(username,password)" +
                "values('" + username + "','" + password + "')";

        try {
            ps = con.prepareStatement(sql);//��д�õ�sql��䴫�ݵ����ݿ⣬�����ݿ�֪������Ҫ��ʲô
            int a = ps.executeUpdate();//����������ڸı����ݿ����ݣ�a����ı����ݿ������
            if (a > 0) {
                System.out.println("��ӳɹ�");
                flag = true;
            } else {
                System.out.println("���ʧ��");
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
