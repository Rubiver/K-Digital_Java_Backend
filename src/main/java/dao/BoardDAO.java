package dao;

import common.EncryptPassword;
import contants.Constants;
import dto.BoardDTO;
import dto.MemberDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.sse.Sse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public BoardDAO() {
        try {
            Context context = new InitialContext();
            Context ctx = (Context) context.lookup("java:comp/env");
            dataFactory = (DataSource) ctx.lookup("jdbc/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<BoardDTO> getBoardContents(){
//        List<BoardDTO> list = null;
//        return list;
//    }
    public Timestamp getTimestamp(){
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        return timestamp;
    }
    public int insertBoard(BoardDTO dto) throws Exception{
        String sql = "insert into board values(?,?,?,?,?,?)";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
        ) {
            pstat.setInt(1,dto.getSeq());
            pstat.setString(2,dto.getWriter());
            pstat.setString(3,dto.getTitle());
            pstat.setString(4,dto.getContents());
            pstat.setTimestamp(5,getTimestamp());
            pstat.setInt(6,dto.getView_count());
            return pstat.executeUpdate();
        }
    }

    public int addViewcount(int seq) throws Exception{
        String sql = "update board set view_count = view_count + 1 where seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
                ){
            pstat.setInt(1,seq);
            int result = pstat.executeUpdate();
            return result;
        }

    }

    public List<BoardDTO> listBoard() {
        List<BoardDTO> list = new ArrayList<>();
        try {
            Connection con = dataFactory.getConnection();
            String query = "select * from board order by seq desc";
            System.out.println(query);
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int seq = rs.getInt("seq");
                String title = rs.getString("title");
                String writer = rs.getString("writer");
                Timestamp date = rs.getTimestamp("write_date");
                int count = rs.getInt("view_count");

                BoardDTO dto = new BoardDTO();
                dto.setSeq(seq);
                dto.setTitle(title);
                dto.setWriter(writer);
                dto.setWrite_date(date);
                dto.setView_count(count);
                list.add(dto);
            }
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public BoardDTO showBoard(int seq) throws Exception{
        String sql = "select * from board where seq = ?";
        System.out.println(sql);
        List<BoardDTO> result = new ArrayList<>();
        try{
            Connection con = dataFactory.getConnection();
            PreparedStatement pstat = con.prepareStatement(sql);
            pstat.setInt(1,seq);
            ResultSet rs = pstat.executeQuery();
           while(rs.next()){
                BoardDTO dto = new BoardDTO();
                int findSeq = rs.getInt("seq");
                String writer = rs.getString("writer");
                String title = rs.getString("title");
                String contents = rs.getString("contents");
                int viewCount = rs.getInt("view_count");
                Timestamp date = rs.getTimestamp("write_date");
                dto.setSeq(findSeq);
                dto.setWriter(writer);
                dto.setTitle(title);
                dto.setContents(contents);
                dto.setWrite_date(date);
                dto.setView_count(viewCount);
                result.add(dto);
                }
            rs.close();
            pstat.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.get(0);
    }

    public int deleteBoardContents(int seq) throws Exception {
        String sql = "delete from board where seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setInt(1, seq);
            int result = pst.executeUpdate();
            return result;
        }
    }

    public int updateBoardContents(String title, String contents, int seq) throws Exception {
        String sql = "update board set title = ?, contents = ?, write_date = ? where seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setString(1, title);
            pst.setString(2, contents);
            pst.setTimestamp(3, getTimestamp());
            pst.setInt(4, seq);
            int result = pst.executeUpdate();
            return result;
        }
    }

    public int getRecordCount() throws Exception{
        String sql = "select count(*) as 'count' from board";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                ){
            int result = 0;
            rs.next();
            return rs.getInt("count");
        }
    }

    public int getSearchRecordCount(String str) throws Exception{
        String sql = "select count(*) as 'count' from board where title like ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1,"%"+str+"%");
            try(
                    ResultSet rs = pst.executeQuery();
                    ){
                int result = 0;
                rs.next();
                return rs.getInt("count");
            }
        }
    }

//        select row_number() over (order by seq desc) as 'rn', board.* from board limit 10, 10;
//        select * from (select row_number() over (order by seq desc) as 'rn', board.* from board) a where a.rn between 11 and 20;
    public List<BoardDTO> selectBy(int start, int end) throws Exception{
        String sql = "select * from (select row_number() over (order by seq desc) as 'rn', board.* from board) a where a.rn between ? and ?";
        List<BoardDTO> list = new ArrayList<>();
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setInt(1,start);
            pst.setInt(2,end);
            try(
                    ResultSet rs = pst.executeQuery();
                    ){
                while(rs.next()){
                    int seq = rs.getInt("seq");
                    String title = rs.getString("title");
                    String writer = rs.getString("writer");
                    Timestamp date = rs.getTimestamp("write_date");
                    int count = rs.getInt("view_count");

                    BoardDTO dto = new BoardDTO();
                    dto.setSeq(seq);
                    dto.setTitle(title);
                    dto.setWriter(writer);
                    dto.setWrite_date(date);
                    dto.setView_count(count);
                    list.add(dto);
                }
                return list;
            }
        }
    }
    public List<BoardDTO> searchBy(String str, int start, int end) throws Exception {
//        String sql = "select * from (select row_number() over (order by seq desc) as 'rn', board.* from board) a where a.rn between ? and ?";
        String sql = "select * from (select row_number() over (order by seq desc) as 'rn', board.* from board where title like ? order by seq desc) a where a.rn between ? and ?";
        List<BoardDTO> list = new ArrayList<>();
        try (
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            pst.setString(1,"%"+str+"%");
            pst.setInt(2, start);
            pst.setInt(3, end);
            try (
                    ResultSet rs = pst.executeQuery();
            ) {
                while (rs.next()) {
                    int seq = rs.getInt("seq");
                    String title = rs.getString("title");
                    String writer = rs.getString("writer");
                    Timestamp date = rs.getTimestamp("write_date");
                    int count = rs.getInt("view_count");

                    BoardDTO dto = new BoardDTO();
                    dto.setSeq(seq);
                    dto.setTitle(title);
                    dto.setWriter(writer);
                    dto.setWrite_date(date);
                    dto.setView_count(count);
                    list.add(dto);
                }
                return list;
            }
        }
    }
}

//    public int[] getPageRange(int currentPage) throws Exception{
//        int recordTotalCount = this.getRecordCount();
//        int recordCountPerPage = 10;
//        int naviCountPerPage = 10;
//        int totalPageCount = 0; //만들어질 페이지의 개수
//        if(recordTotalCount % recordCountPerPage > 0){
//            totalPageCount = recordTotalCount / recordCountPerPage + 1;
//        }else{
//            totalPageCount = recordTotalCount / recordCountPerPage;
//        }
//        System.out.println("curPage(DAO) : "+currentPage);
//        if( currentPage < 1){
//            currentPage = 1;
//        }else if(currentPage > totalPageCount){
//            currentPage = totalPageCount;
//        }
//        int startNavi = (currentPage-1)/naviCountPerPage*naviCountPerPage+1;
//        int endNavi = startNavi+naviCountPerPage-1;
//
//        if(endNavi > totalPageCount){
//            endNavi = totalPageCount;
//        }
//        System.out.println("totalPage : "+totalPageCount);
//        System.out.println("getPageRange"+startNavi+" "+endNavi);
//        boolean needPrev = true;
//        boolean needNext = true;
//        if(startNavi == 1){
//            needPrev = false;
//        }
//        if(endNavi == totalPageCount){
//            needNext = false;
//        }
//
//        return new int[] {startNavi,endNavi};
//    }

//    public String getPageNavi(int currentPage) throws Exception{
//        int recordTotalCount = this.getRecordCount();
//        int recordCountPerPage = Constants.recordCountPerPage; //추후 삭제하고  recordCountPerPage 전부 바꿔야됨. 밑애도
//        int naviCountPerPage = Constants.naviCountPerPage;
//        int totalPageCount = 0; //만들어질 페이지의 개수
//        if(recordTotalCount % recordCountPerPage > 0){
//            totalPageCount = recordTotalCount / recordCountPerPage + 1;
//        }else{
//            totalPageCount = recordTotalCount / recordCountPerPage;
//        }
//
//        if( currentPage < 1){
//            currentPage = 1;
//        }else if(currentPage > totalPageCount){
//            currentPage = totalPageCount;
//        }
//        int startNavi = (currentPage-1)/naviCountPerPage*naviCountPerPage+1;
//        int endNavi = startNavi+naviCountPerPage-1;
//
//        if(endNavi > totalPageCount){
//            endNavi = totalPageCount;
//        }
//        System.out.println(startNavi+" "+endNavi);
//        boolean needPrev = true;
//        boolean needNext = true;
//        if(startNavi == 1){
//            needPrev = false;
//        }
//        if(endNavi == totalPageCount){
//            needNext = false;
//        }
//        StringBuilder sb = new StringBuilder();
//        if(needPrev){
//            sb.append("<a href='/moveToBoard.board?curPage="+(startNavi-1)+"'>< </a>");
//        }
//        for(int i= startNavi; i<=endNavi; i++){
//            sb.append("<a href='/moveToBoard.board?curPage="+i+"'>"+i+"</a> ");
//        }
//        if(needNext){
//            sb.append("<a href='/moveToBoard.board?curPage="+(endNavi+1)+"'> ></a>");
//        }
//        System.out.println(sb.toString());
//        return sb.toString();
//    }
