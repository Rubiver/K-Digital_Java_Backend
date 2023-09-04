package servlets;

import dao.ReplyDAO;
import dto.ReplyDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("*.reply")
public class ReplyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        System.out.println(cmd);
        ReplyDAO dao = new ReplyDAO();
        try{
            if(cmd.equals("/getReply.reply")){
                int board_seq = Integer.parseInt(req.getParameter("seq"));
                List<ReplyDTO> replies = dao.getReply(board_seq);
                System.out.println("댓글 갯수 : "+replies.size());
                if(replies.size()<=0){
                    res.sendRedirect("/boardContents.jsp?seq="+board_seq);
                }else{
                    req.setAttribute("replies",replies);
//                    res.sendRedirect("/boardContents.jsp?seq="+board_seq);
                    req.getRequestDispatcher("/boardContents.jsp?seq="+board_seq).forward(req,res);
                }

            }else if(cmd.equals("/addReply.reply")){
                System.out.println("댓글작성 시작");
                int board_seq = Integer.parseInt(req.getParameter("seq"));
                System.out.println("test1");
                String writer = req.getParameter("writer"); //reply_writer?
                String contents = req.getParameter("contents");
                System.out.println("test2");
                int result = dao.addReply(board_seq,writer,contents);

                if(result>0){
                    System.out.println("댓글 작성 성공");
                }else{
                    System.out.println("댓글 작성 실패");
                }
                res.sendRedirect("/getReply.reply?seq="+board_seq);
            }else if(cmd.equals("/deleteReply.reply")){
                System.out.println("댓글 삭제");
                int reply_seq = Integer.parseInt(req.getParameter("reply"));
                int board_seq = Integer.parseInt(req.getParameter("seq"));
                int result = dao.deleteReply(reply_seq);
                if(result>0){
                    System.out.println("댓글 삭제 성공");
                }else{
                    System.out.println("댓글 삭제 실패");
                }
                res.sendRedirect("/getReply.reply?seq="+board_seq);
            }else if(cmd.equals("/updateReply.reply")){
                System.out.println("수정");
                int reply_seq = Integer.parseInt(req.getParameter("reply"));
                int board_seq = Integer.parseInt(req.getParameter("seq"));
                String contents = req.getParameter("contents");
                int result = dao.updateReply(reply_seq,contents);
                if(result>0){
                    System.out.println("댓글 수정 성공");
                }else{
                    System.out.println("댓글 수정 실패");
                }
                res.sendRedirect("/getReply.reply?seq="+board_seq);
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}