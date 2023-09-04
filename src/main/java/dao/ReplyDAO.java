package dao;

import dto.BoardDTO;
import dto.ReplyDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReplyDAO {

    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public ReplyDAO() {
        try {
            Context context = new InitialContext();
            Context ctx = (Context) context.lookup("java:comp/env");
            dataFactory = (DataSource) ctx.lookup("jdbc/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<ReplyDTO> getReply(int board_seq) throws Exception{
        List<ReplyDTO> list = new ArrayList<>();
        String sql = "select * from reply where board_seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setInt(1,board_seq);
            try(
                    ResultSet rs = pst.executeQuery();
                    ){
                while(rs.next()){
                    ReplyDTO dto = new ReplyDTO();
                    int seq = rs.getInt("seq");
                    String writer = rs.getString("writer");
                    String contents = rs.getString("contents");
                    Timestamp date = rs.getTimestamp("write_date");
                    int board_seq2 = rs.getInt("board_seq");
                    dto.setSeq(seq);
                    dto.setWriter(writer);
                    dto.setContents(contents);
                    dto.setWrite_date(date);
                    dto.setBoard_seq(board_seq2);
                    list.add(dto);
                }
            }
            return list;
        }
    }
    public int addReply(int board_seq, String writer, String contents) throws Exception{
        String sql = "insert into reply values(0,?,?,default,?);";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            System.out.println("test1");
            pst.setString(1,writer);
            pst.setString(2,contents);
            pst.setInt(3,board_seq);
            System.out.println("test2");
            return pst.executeUpdate();
        }
    }

    public int deleteReply(int reply_seq) throws Exception{
        String sql = "delete from reply where seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setInt(1,reply_seq);
            return pst.executeUpdate();
        }
    }

    public int updateReply(int reply_seq, String contents) throws Exception{
        String sql = "update reply set contents = ?, write_date = default where seq = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setString(1,contents);
            pst.setInt(2,reply_seq);
            return pst.executeUpdate();
        }
    }
}
