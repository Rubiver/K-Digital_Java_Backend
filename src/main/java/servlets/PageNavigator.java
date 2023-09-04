package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PageNavigator {
    public static void main(String[] args) throws Exception{
        //페이징처리 방법.

        //필요한 정보 : 전체 레코트 개수, 한 페이지에 노출시킬 레코드 수량, 한 페이지에 노출시킬 페이지 네비게이터 수량, 햔재 위치한 페이지 번호
        String sql = "insert into board values(null, ?, ?, ?, default,default)";
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","java","java");
        PreparedStatement pst = con.prepareStatement(sql);
        for(int i=0; i< 140; i++){
            pst.setString(1,"tester"+i);
            pst.setString(2,"title"+i);
            pst.setString(3,"contents");
            pst.addBatch();
        }
        pst.executeBatch();
        con.close();

//        int recordTotalCount = 5;
//        int recordCountPerPage = 10;
//        int naviCountPerPage = 10;
//        int totalPageCount = 0; //만들어질 페이지의 개수
//        if(recordTotalCount % recordCountPerPage > 0){
//            totalPageCount = recordTotalCount / recordCountPerPage + 1;
//        }else{
//            totalPageCount = recordTotalCount / recordCountPerPage;
//        }
//        int currentPage = 1;
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
//        if(needPrev){
//            System.out.print("< ");
//        }
//        for(int i= startNavi; i<=endNavi; i++){
//            System.out.print(i +" ");
//        }
//        if(needNext){
//            System.out.print(" >");
//        }
    }
}
