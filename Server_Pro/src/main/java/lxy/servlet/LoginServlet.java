package lxy.servlet;

import lxy.dao.UserDao;
import lxy.pojo.userlogin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String pwd = req.getParameter("password");
        System.out.println(name);
        System.out.println(pwd);
        resp.setCharacterEncoding("UTF-8");
        UserDao dao = new UserDao();
        boolean login = false;
        try {
            List<userlogin> userloginList = dao.testSelectAll();
            for (userlogin user:userloginList){
                if(user.getUsername().equals(name)&&user.getPassword().equals(pwd)){
                    login = true;
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String msg = "";
        if(login){
            msg = "cg";
        }else{
            msg = "sb";
        }
        PrintWriter out = resp.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }
}
