package controller;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.ContactDTO;
import dto.MovieDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("*.ajax")
public class AjaxController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        System.out.println(cmd);

        if(cmd.equals("/exam01.ajax")){
            System.out.println("Ajax request");
        }else if(cmd.equals("/exam02.ajax")){
            String param1 = req.getParameter("param1");
            String param2 = req.getParameter("param2");
            //서로 통신을 위해 데이터를 직렬화 하게 되는데, 모든 데이터가 String으로 통신하게됨.
            System.out.println(param1+" "+param2);
        }else if(cmd.equals("/exam03.ajax")){
            PrintWriter out = res.getWriter();
            out.append("Ajax response");
        }else if(cmd.equals("/exam04.ajax")){
            Gson gson = new Gson();
            String[] str = new String[]{"sr","2","three","4","five"};
            String result = gson.toJson(str);
            System.out.println(str);
            System.out.println(result);
            PrintWriter out = res.getWriter();
            out.println(result);

        }else if(cmd.equals("/exam05.ajax")){
            Gson gson = new Gson();
            MovieDTO dto = new MovieDTO(1,"a","a");
            PrintWriter out = res.getWriter();

            out.println(gson.toJson(dto));
        }else if(cmd.equals("/exam06.ajax")){
            Gson gson = new Gson();
            List<MovieDTO> movies = new ArrayList<>();
            movies.add(new MovieDTO(1,"a1","a1"));
            movies.add(new MovieDTO(2,"b1","b1"));
            movies.add(new MovieDTO(3,"c1","c1"));

            PrintWriter out = res.getWriter();
            out.append(gson.toJson(movies));
        }else if(cmd.equals("/exam07.ajax")){
            Gson gson = new Gson();
            MovieDTO mdto = new MovieDTO(1,"a","a");
            ContactDTO cdto = new ContactDTO(1,"t","010");

            JsonArray jarr = new JsonArray();
            jarr.add(gson.toJson(mdto));
            jarr.add(gson.toJson(cdto));

            List list = new ArrayList();
            list.add(gson.toJson(mdto));
            list.add(gson.toJson(cdto));

            Map<String, Object> map = new HashMap<>();
            map.put("movie",mdto);
            map.put("contact",cdto);

            PrintWriter out = res.getWriter();
            //out.println(list.toString());
            out.println(gson.toJson(map));

            //핵심 : 데이터가 어떻든 적절한 형변화 및 직렬화를 하고, 클라이언트에게 데이터를 보낼 수 있어야함.
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}