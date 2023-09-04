package dao;

import common.EncryptPassword;
import dto.BoardDTO;
import dto.MemberDTO;
import dto.ReplyDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public MemberDAO() {
        try {
            Context context = new InitialContext();
            Context ctx = (Context) context.lookup("java:comp/env");
            dataFactory = (DataSource) ctx.lookup("jdbc/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MemberDTO> listMembers() {
        List<MemberDTO> list = new ArrayList<MemberDTO>();
        try {
            Connection con = dataFactory.getConnection();
            String query = "select * from members ";
            System.out.println(query);
            pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pw");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String zipcode = rs.getString("zipcode");
                String address1 = rs.getString("address1");
                String address2 = rs.getString("address2");
                Timestamp signup_date = rs.getTimestamp("signup_date");
                MemberDTO dto = new MemberDTO();
                dto.setId(id);
                dto.setPw(pwd);
                dto.setName(name);
                dto.setEmail(email);
                dto.setPhone(phone);
                dto.setZipcode(zipcode);
                dto.setAddress1(address1);
                dto.setAddress2(address2);
                dto.setSignup_date(signup_date);
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


    public boolean isIdExist(String id_check) throws SQLException {
        String sql = "select * from members where id = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ){
            pst.setString(1, id_check);
            try(ResultSet rs = pst.executeQuery();){ //select 쿼리 + 쿼리에 ?를 채워넣어야 하는 경우 2중 try with resource문을 사용해야함.
                boolean result = rs.next();
                return result;
            }
        }
    }
    public Timestamp getTimestamp(){
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        return timestamp;
    }
    public int insertMember(MemberDTO dto) throws Exception{
        String sql = "insert into members values(?,?,?,?,?,?,?,?,?)";
        String pw = EncryptPassword.getSHA512(dto.getPw());
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
        ) {
            pstat.setString(1,dto.getId());
            pstat.setString(2,pw);
            pstat.setString(3,dto.getName());
            pstat.setString(4,dto.getPhone());
            pstat.setString(5,dto.getEmail());
            pstat.setString(6,dto.getZipcode());
            pstat.setString(7,dto.getAddress1());
            pstat.setString(8,dto.getAddress2());
            pstat.setTimestamp(9, getTimestamp());
            return pstat.executeUpdate();
        }
    }
    public boolean canLogin(String checkid, String checkpw) throws Exception{
        String sql = "select * from members where id = ? and pw = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pstat = con.prepareStatement(sql);
        ){
            pstat.setString(1,checkid);
            pstat.setString(2,checkpw);
            try ( ResultSet rs = pstat.executeQuery(); ){
                while(rs.next()){
                    String id = rs.getString("id");
                    String pw = rs.getString("pw");
                    if(id.equals(checkid)){
                        if(pw.equals(checkpw)){
                            return true;
                        }
                        return false;
                    }
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
    }
    public int deleteMember(String memberID) throws Exception {
        String sql = "delete from members where id = ?";
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setString(1, memberID);
            int result = pst.executeUpdate();
            return result;
        }
    }

    public String getName(String id_check) throws SQLException {
        String result = "";
        String sql = "select name from members where id = ?";
        try (
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
        ) {
            pst.setString(1, id_check);

            try (ResultSet rs = pst.executeQuery();) { //select 쿼리 + 쿼리에 ?를 채워넣어야 하는 경우 2중 try with resource문을 사용해야함.

                while (rs.next()) {
                    result = rs.getString("name");
                }
            }
            return result;
        }
    }

    public List<MemberDTO> searchMembers(String check_id) {
        List<MemberDTO> list = new ArrayList<MemberDTO>();
        try {
            Connection con = dataFactory.getConnection();
            String query = "select * from members where id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,check_id);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    String id = rs.getString("id");
                    String pwd = rs.getString("pw");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String zipcode = rs.getString("zipcode");
                    String address1 = rs.getString("address1");
                    String address2 = rs.getString("address2");
                    Timestamp signup_date = rs.getTimestamp("signup_date");
                    MemberDTO dto = new MemberDTO();
                    dto.setId(id);
                    dto.setPw(pwd);
                    dto.setName(name);
                    dto.setEmail(email);
                    dto.setPhone(phone);
                    dto.setZipcode(zipcode);
                    dto.setAddress1(address1);
                    dto.setAddress2(address2);
                    dto.setSignup_date(signup_date);
                    list.add(dto);
                }
                rs.close();
                pstmt.close();
                con.close();
            }
        } catch (Exception e) {

        }
        return list;
    }
    public int updateMember(String col, String data, String id) throws Exception {
        String sql = "update members set "+col+" = ? where id = ?";
        System.out.println(sql);
        System.out.println(data+" "+id);
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = null;
        ){
            pst.setString(1, data);
            pst.setString(2, id);
            int result = pst.executeUpdate();
            return result;
        }
    }
    public List<ReplyDTO> getWritedReply(String id) throws Exception{
        String sql = "select * from reply where writer = ?";
        List<ReplyDTO> list = new ArrayList<>();
        try(
                Connection con = dataFactory.getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                ){
            pst.setString(1,id);
            try(
                    ResultSet rs = pst.executeQuery();
                    ){
                while(rs.next()){
                    ReplyDTO dto = new ReplyDTO();
                    int seq = rs.getInt("seq");
                    String writer = rs.getString("writer");
                    String contents = rs.getString("contents");
                    Timestamp date = rs.getTimestamp("write_date");
                    int board_seq = rs.getInt("board_seq");
                    dto.setSeq(seq);
                    dto.setBoard_seq(board_seq);
                    dto.setWriter(writer);
                    dto.setContents(contents);
                    dto.setWrite_date(date);
                    list.add(dto);
                }
            }
            return list;
        }
    }

}