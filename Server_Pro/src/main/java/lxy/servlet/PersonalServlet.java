package lxy.servlet;

import lxy.dao.userDoctorDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static com.mysql.cj.util.StringUtils.getBytes;

@WebServlet(name = "PersonalServlet", value = "/PersonalServlet")
public class PersonalServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = new String(req.getParameter("userName").getBytes("ISO8859-1"), "UTF-8");
        String department = new String(req.getParameter("doctorDepartment").getBytes("ISO8859-1"), "UTF-8");
        int doctorId = Integer.parseInt(req.getParameter("doctorId"));
        String date = new String(req.getParameter("date").getBytes("ISO8859-1"), "UTF-8");
        String doctorName = new String(req.getParameter("doctorName").getBytes("ISO8859-1"), "UTF-8");
        int doctorPrice = Integer.parseInt(req.getParameter("doctorPrice"));
        String doctorIntro = new String(req.getParameter("doctorIntro").getBytes("ISO8859-1"), "UTF-8");
        userDoctorDao u = new userDoctorDao();
        try {
            u.insertUserDoctor(username,department,doctorId,date,doctorName,doctorPrice,doctorIntro);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
