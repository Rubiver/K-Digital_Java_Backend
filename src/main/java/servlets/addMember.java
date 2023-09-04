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

@WebServlet("/addMember")
public class addMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        MemberDAO dao = new MemberDAO();
        List<MemberDTO> list = dao.listMembers();
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String zipcode = req.getParameter("zipcode");
        String address1 = req.getParameter("address1");
        String address2 = req.getParameter("address2");
        MemberDTO dto = new MemberDTO();
        try {
            System.out.println(id+" "+pw+" "+name+" "+address1);
            int result = dao.insertMember(new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2, null));
            if(result>0){
                System.out.println("정상 가입");
                out.println("정상가입");
            }else{
                System.out.println("가입 안됨.");
                out.println("가입안됨");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}