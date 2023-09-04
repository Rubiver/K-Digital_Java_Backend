package servlets;

import contants.Constants;
import dao.BoardDAO;
import dto.BoardDTO;
import dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("*.board")
public class BoardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        BoardDAO dao = new BoardDAO();
        System.out.println(cmd);
        try{
            if(cmd.equals("/moveToBoard.board")){

                int curPage = 0;
                String cpage = req.getParameter("curPage");
                if(cpage==null){
                    curPage = 1;

                }else{
                    curPage = Integer.parseInt(req.getParameter("curPage"));
                }
                List<BoardDTO> board_data = dao.searchBy("",curPage* Constants.recordCountPerPage-(Constants.recordCountPerPage-1),curPage*Constants.recordCountPerPage);

                //String navi = dao.getPageNavi(curPage); 클라이언트에서 구현 예정이기 떄문에 제거
                //req.getSession().setAttribute("navi",navi);
                req.getSession().setAttribute("latestPage",curPage);
                req.setAttribute("recordTotalCount",dao.getRecordCount());
                req.setAttribute("recordCountPerPage",Constants.recordCountPerPage);
                req.setAttribute("naviCountPerPage",Constants.naviCountPerPage);
                req.getSession().setAttribute("state",true);
                req.getSession().setAttribute("board_data",board_data);

//                res.sendRedirect("/board.jsp");
                req.getRequestDispatcher("/board.jsp").forward(req,res);
            }else if(cmd.equals("/writeBoard.board")){

                res.sendRedirect("/write_board.jsp");

            }else if(cmd.equals("/writeContents.board")){
                System.out.println("글쓰기 시작");
                String id = (String)req.getSession().getAttribute("login");
                String title = req.getParameter("title");
                String contents = req.getParameter("contents");
                int result = dao.insertBoard(new BoardDTO(0,id,title,contents,null,0));
                //dao 실행이 안됨.
                if(result>0){
                    System.out.println("정상 작성");
                    res.sendRedirect("/moveToBoard.board");
                }else{
                    System.out.println("글 작성 오류");
                    res.sendRedirect("/moveToBoard.board");
                }
            } else if (cmd.equals("/showContents.board")) {
                int seq = Integer.parseInt(req.getParameter("seq"));
                BoardDTO dto = dao.showBoard(seq);
                System.out.println(dto.getWriter());
                req.getSession().setAttribute("boardCon",dto);
                res.sendRedirect("/getReply.reply?seq="+dto.getSeq());
//                res.sendRedirect("/boardContents.jsp?seq="+dto.getSeq());
            } else if(cmd.equals("/addViewCount.board")){
                int seq = Integer.parseInt(req.getParameter("seq"));
                int result = dao.addViewcount(seq);
                if(result>0){
                    System.out.println("조회수 정상");
                }else{
                    System.out.println("조회수 비정상");
                }
                res.sendRedirect("/showContents.board?seq="+seq);
            }else if(cmd.equals("/deleteContents.board")){
                System.out.println("삭제");
                System.out.println(req.getParameter("seq"));
                int seq = Integer.parseInt(req.getParameter("seq"));
                System.out.println(req.getParameter("seq"));
                int result = dao.deleteBoardContents(seq);
                res.sendRedirect("/moveToBoard.board");
            }else if(cmd.equals("/updateContents.board")){
                int seq = Integer.parseInt(req.getParameter("seq"));
                String title = req.getParameter("title");
                String contents = req.getParameter("contents");
                int result = dao.updateBoardContents(title,contents,seq);
                if(result>0){
                    System.out.println("정상 수정");
                    res.sendRedirect("/moveToBoard.board");
                }else{
                    System.out.println("비정상 수정");
                    res.sendRedirect("/moveToBoard.board");
                }
            }else if(cmd.equals("/searchBoard.board")){
                int curPage = 0;
                String cpage = req.getParameter("curPage");
                if(cpage==null){
                    curPage = 1;
                }else{
                    curPage = Integer.parseInt(req.getParameter("curPage"));
                }
                String search = req.getParameter("search");
                System.out.println(dao.getSearchRecordCount(search));
                List<BoardDTO> search_data = dao.searchBy(search,curPage* Constants.recordCountPerPage-(Constants.recordCountPerPage-1),curPage*Constants.recordCountPerPage);

                req.getSession().setAttribute("latestPage",curPage);
                req.setAttribute("recordTotalCount",dao.getSearchRecordCount(search));
                req.setAttribute("recordCountPerPage",Constants.recordCountPerPage);
                req.setAttribute("naviCountPerPage",Constants.naviCountPerPage);
                req.getSession().setAttribute("state",false);
                req.getSession().setAttribute("board_data",search_data);
                req.setAttribute("search",search);
                req.getRequestDispatcher("/board.jsp").forward(req,res);
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