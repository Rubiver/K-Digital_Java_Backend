package dao;

import dto.UploadDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UploadDAO {
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public UploadDAO() {
        try {
            Context context = new InitialContext();
            Context ctx = (Context) context.lookup("java:comp/env");
            dataFactory = (DataSource) ctx.lookup("jdbc/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int uploadFile(UploadDTO dto) throws Exception{
        String sql = "insert into files values(?,?,?,?)";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setInt(1,dto.getSeq());
            pst.setString(2,dto.getOrigin_name());
            pst.setString(3,dto.getSys_name());
            pst.setInt(4,dto.getBoard_seq());
            return pst.executeUpdate();
        }
    }
    public UploadDTO getFile(String ori_name) throws Exception{
        String sql = "select * from files where origin_name like ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setString(1,ori_name);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int seq = rs.getInt("seq");
            String origin_name = rs.getString("origin_name");
            String sys_name = rs.getString("sys_name");
            int board_seq = rs.getInt("board_seq");
            return new UploadDTO(seq,origin_name,sys_name,board_seq);

        }
    }

    public List<UploadDTO> selectAll() throws Exception{
        String sql = "select * from files";
        List<UploadDTO> list = new ArrayList<>();
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            try(
                    ResultSet rs = pst.executeQuery();
                    ){
                while(rs.next()){
                    UploadDTO dto = new UploadDTO();
                    int seq = rs.getInt("seq");
                    String origin_name = rs.getString("origin_name");
                    System.out.println(origin_name);
                    String sys_name = rs.getString("sys_name");
                    int board_seq = rs.getInt("board_seq");
                    dto.setSeq(seq);
                    dto.setOrigin_name(origin_name);
                    dto.setSys_name(sys_name);
                    dto.setBoard_seq(board_seq);
                    list.add(dto);
                }
            }
            return list;
        }
    }
}
