package servlets;

import dao.MemberDAO;
import dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MemberDAO dao = new MemberDAO();
        List<MemberDTO> list = dao.listMembers();
        System.out.println(request.getParameter("id"));
        boolean state = false;
        try {
             state = dao.isIdExist(request.getParameter("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(state);
        if(state){
            out.println("중복있음.");

        }else{
            out.println("중복없음");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}