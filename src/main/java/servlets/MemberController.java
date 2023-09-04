package servlets;

import common.EncryptPassword;
import dao.MemberDAO;
import dto.MemberDTO;
import dto.ReplyDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("*.member")
public class MemberController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        MemberDAO dao = new MemberDAO();
        System.out.println(cmd);
        try{
            if(cmd.equals("/Login.member")){
                String id = req.getParameter("id");
                String pw = EncryptPassword.getSHA512(req.getParameter("pw"));
                boolean result = dao.canLogin(id,pw);
                String name = dao.getName(id);
                System.out.println(name);
                if(result){
                    //모든페이지가 사용자를 알아볼 수 있게 흔적을 남겨야함.
                    //저장소 종류 : page, request, session, application
                    System.out.println("Login 성공");
                    //res.sendRedirect("/index.jsp");
                    //String result = "성공"; page scope
                    //req.setAttribute("login","성공"); request scope
                    //req.getServletContext().setAttribute("login","성공"); application scope -> 모든 사용자가 하나의 전역변수를 공유하기 때문에 로그인된 것처럼 보이기만 함.
                    req.getSession().setAttribute("login",id); //session scope
                    req.getSession().setAttribute("name",name); //session scope
                }
                else{
                    System.out.println("Login 실패");
                    res.sendRedirect("/index.jsp");
                }
                res.sendRedirect("/index.jsp");
            }else if(cmd.equals("/addMember.member")){
                String id = req.getParameter("id");
                String pw = req.getParameter("pw");
                String name = req.getParameter("name");
                String phone = req.getParameter("phone");
                String email = req.getParameter("email");
                String zipcode = req.getParameter("zipcode");
                String address1 = req.getParameter("address1");
                String address2 = req.getParameter("address2");
                MemberDTO dto = new MemberDTO();

                System.out.println(id+" "+pw+" "+name+" "+address1);
                int result = dao.insertMember(new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2, null));
                if(result>0){
                    System.out.println("정상 가입");
                    out.println("정상가입");
                    res.sendRedirect("/index.jsp");
                }else{
                    System.out.println("가입 안됨.");
                    out.println("가입안됨");
                    res.sendRedirect("/signup.jsp");
                }
            }else if(cmd.equals("/idcheck.member")){
                boolean state = false;
                try {
                    state = dao.isIdExist(req.getParameter("id"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(state);
                if(state){
                    out.println("중복있음.");
                }else{
                    out.println("중복없음");
                }
            }else if(cmd.equals("/Logout.member")){
                //req.getSession().removeAttribute("login");
                System.out.println("로그아웃");
                req.getSession().invalidate(); //일반적인 로그아웃 방법
                res.sendRedirect("/index.jsp");
            }else if(cmd.equals("/deleteMember.member")){
                String deleteID = (String)req.getSession().getAttribute("login");
                System.out.println(deleteID);
                int result = dao.deleteMember(deleteID);
                if(result>0){
                    System.out.println("회원탈퇴 완료");
                    req.getSession().invalidate();
                    res.sendRedirect("/index.jsp");
                }else{
                    System.out.println("회원탈퇴 오류");
                    res.sendRedirect("/index.jsp");
                }
            }else if(cmd.equals("/mypage.member")){
                String id = req.getParameter("id");
                List<MemberDTO> data = dao.searchMembers((String)req.getSession().getAttribute("login"));
                req.getSession().setAttribute("formedDate",data.get(0).getFormeDate());
                List<ReplyDTO> reply_data = dao.getWritedReply(data.get(0).getId());
                req.getSession().setAttribute("replies",reply_data);
                req.getSession().setAttribute("data",data);
                res.sendRedirect("/mypage.jsp");
            }else if(cmd.equals("/updateMember.member")){
                List<MemberDTO> list = dao.listMembers();
                String id = req.getParameter("id");
                String name = req.getParameter("name");
                String phone = req.getParameter("phone");
                String email = req.getParameter("email");
                String zipcode = req.getParameter("zipcode");
                String address1 = req.getParameter("address1");
                String address2 = req.getParameter("address2");
                MemberDTO dto = new MemberDTO();
                List<MemberDTO> data = dao.searchMembers((String)req.getSession().getAttribute("login"));
                
                if(!data.get(0).getName().equals(name)){
                    int result = dao.updateMember("name",name,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");

                    }else{
                        System.out.println("수정 오류");
                    }
                }
                if(!data.get(0).getEmail().equals(email)){
                    int result = dao.updateMember("email",email,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");
                    }else{
                        System.out.println("수정 오류");
                    }
                }
                if(!data.get(0).getPhone().equals(phone)){
                    System.out.println(" 다름 업데이트 예정");
                    int result = dao.updateMember("phone",phone,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");
                    }else{
                        System.out.println("수정 오류");
                    }
                }
                if(!data.get(0).getZipcode().equals(zipcode)){
                    System.out.println(" 다름 업데이트 예정");
                    int result = dao.updateMember("zipcode",zipcode,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");
                    }else{
                        System.out.println("수정 오류");
                    }
                }
                if(!data.get(0).getAddress1().equals(address1)){
                    int result = dao.updateMember("address1",address1,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");
                    }else{
                        System.out.println("수정 오류");
                    }
                }
                if(!data.get(0).getAddress2().equals(address2)){
                    int result = dao.updateMember("address2",address2,id);
                    if(result >0){
                        System.out.println("이름 정상 수정");
                    }else{
                        System.out.println("수정 오류");
                    }
                }
                List<MemberDTO> reload_data = dao.searchMembers((String)req.getSession().getAttribute("login"));
                req.getSession().setAttribute("data",reload_data);
                res.sendRedirect("/mypage.jsp");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        MemberDAO dao = new MemberDAO();

        try{
            if(cmd.equals("/Login.member")){
                String id = req.getParameter("id");
                String pw = EncryptPassword.getSHA512(req.getParameter("pw"));
                boolean result = dao.canLogin(id,pw);
                if(result){
                    //모든페이지가 사용자를 알아볼 수 있게 흔적을 남겨야함.
                    //저장소 종류 : page, request, session, application
                    System.out.println("Login 성공");
                    //res.sendRedirect("/index.jsp");

                    //String result = "성공"; page scope
                    //req.setAttribute("login","성공"); request scope
                    //req.getServletContext().setAttribute("login","성공"); application scope -> 모든 사용자가 하나의 전역변수를 공유하기 때문에 로그인된 것처럼 보이기만 함.
                    req.getSession().setAttribute("login",id); //session scope
                }
                else{
                    System.out.println("Login 실패");
                }
                res.sendRedirect("/index.jsp");
            }else if(cmd.equals("/addMember.member")){
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

                System.out.println(id+" "+pw+" "+name+" "+address1);
                int result = dao.insertMember(new MemberDTO(id,pw,name,phone,email,zipcode,address1,address2, null));
                if(result>0){
                    System.out.println("정상 가입");
                    out.println("정상가입");
                    res.sendRedirect("/index.jsp");
                }else{
                    System.out.println("가입 안됨.");
                    out.println("가입안됨");
                    res.sendRedirect("/signup.jsp");
                }
            }else if(cmd.equals("/idcheck.member")){
                boolean state = false;
                try {
                    state = dao.isIdExist(req.getParameter("id"));
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(state);
                if(state){
                    out.println("중복있음.");

                }else{
                    out.println("중복없음");
                }
            }else if(cmd.equals("/Logout.member")) {
                //req.getSession().removeAttribute("login");
                System.out.println("로그아웃");
                req.getSession().invalidate(); //일반적인 로그아웃 방법
                res.sendRedirect("/index.jsp");
            }else if(cmd.equals("/deleteMember.member")){
                String deleteID = (String)req.getSession().getAttribute("login");
                System.out.println(deleteID);
                int result = dao.deleteMember(deleteID);
                if(result>0){
                    System.out.println("회원탈퇴 완료");
                    req.getSession().invalidate();
                    res.sendRedirect("/index.jsp");
                }else{
                    System.out.println("회원탈퇴 오류");
                    res.sendRedirect("/index.jsp");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}