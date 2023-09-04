package servlets;

import common.EncryptPassword;
import dao.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //id, pw 전달받아서 dao에 일치하는 계정 찾으면 성공 아니면 실패
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        MemberDAO dao = new MemberDAO();

        String id = req.getParameter("id");
        String pw = EncryptPassword.getSHA512(req.getParameter("pw"));

        try{
            boolean result = dao.canLogin(id,pw);
            if(result){
                System.out.println("Login 성공");
            }
            else{
                System.out.println("Login 실패");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}