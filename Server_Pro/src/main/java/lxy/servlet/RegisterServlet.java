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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
        boolean flag = false;
        boolean cf = false;
        try {
            List<userlogin> users = new ArrayList<>();
            users = dao.testSelectAll();
            for(userlogin u:users){
                if(u.getUsername().equals(name)){
                    cf = true;
                    break;
                }
            }
            if(!cf){
                flag = dao.insertUser(name,pwd);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String msg="";
        if(flag&&!cf){
            msg = "cg";
        }else if (!flag&&!cf){
            msg = "sb";
        }else {
            msg = "cf";
        }
        PrintWriter out = resp.getWriter();
        out.println(msg);
        out.flush();
        out.close();
    }
}
