package lxy.servlet;

import lxy.dao.doctorDao;
import lxy.dao.userDoctorDao;
import lxy.pojo.doctorInfo;
import lxy.pojo.userDoctorInfo;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DoctorServlet", value = "/DoctorServlet")
public class DoctorServlet extends HttpServlet {
    String Infos="";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<doctorInfo> doctorInfoList = new ArrayList<>();
        doctorDao dao = new doctorDao();
        resp.setContentType("text/html;charset=UTF-8");
        try {
            doctorInfoList = dao.testSelectAll();
//            System.out.println(doctorInfoList.size());
//            System.out.println(doctorInfoList.get(1).getDate());
            List<Map<String,Object>> infojson = new ArrayList<>();
            Map<String,Object> infoMap;
            for(doctorInfo u :doctorInfoList){
                infoMap = new HashMap<>();
                infoMap.put("doctorDepartment",u.getDepartment());
                infoMap.put("doctorId",u.getDoctorId());
                infoMap.put("date",u.getDate());
//                System.out.println(u.getDate());
                infoMap.put("doctorName",u.getDoctorName());
                infoMap.put("doctorPrice",u.getDoctorPrice());
                infoMap.put("doctorIntro",u.getDoctorIntro());
                infojson.add(infoMap);
            }
//            System.out.println(infojson.get(1).get("date"));
            StringBuilder json = new StringBuilder();
            json.append("[");
            if (infojson != null && infojson.size() > 0) {
                for (Map<String, Object> map : infojson) {
                    json.append(new JSONObject(map));
//                    System.out.println(map.get("date"));
                    json.append(",");
                }
                json.setCharAt(json.length() - 1, ']');
            } else {
                json.append("]");
            }
            Infos = json.toString();
//            System.out.println(Infos);
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
