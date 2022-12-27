package lxy.servlet;

import lxy.dao.userDoctorDao;
import lxy.pojo.userDoctorInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "userDoctorServlet", value = "/userDoctorServlet")
public class userDoctorServlet extends HttpServlet {
    String Infos="";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<userDoctorInfo> userDoctorInfoList = new ArrayList<>();
        userDoctorDao dao = new userDoctorDao();
        resp.setContentType("text/html;charset=UTF-8");
        try {
            userDoctorInfoList = dao.testSelectAll();
            List<Map<String,Object>> infojson = new ArrayList<>();
            Map<String,Object> infoMap;
            for(userDoctorInfo u :userDoctorInfoList){
                infoMap = new HashMap<>();
                infoMap.put("userName",u.getUsername());
                infoMap.put("doctorDepartment",u.getDepartment());
                infoMap.put("doctorId",u.getDoctorId());
                infoMap.put("date",u.getDate());
                infoMap.put("doctorName",u.getDoctorName());
                infoMap.put("doctorPrice",u.getDoctorPrice());
                infoMap.put("doctorIntro",u.getDoctorIntro());
                infojson.add(infoMap);
            }
            StringBuilder json = new StringBuilder();
            json.append("[");
            if (infojson != null && infojson.size() > 0) {
                for (Map<String, Object> map : infojson) {
                    json.append(new JSONObject(map));
                    json.append(",");
                }
                json.setCharAt(json.length() - 1, ']');
            } else {
                json.append("]");
            }
            Infos = json.toString();
            System.out.println(Infos);
            PrintWriter out = resp.getWriter();
            out.println(Infos);
            out.flush();
            out.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
