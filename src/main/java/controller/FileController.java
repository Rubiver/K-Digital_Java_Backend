package controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import dao.UploadDAO;
import dto.UploadDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet("*.file")
public class FileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        System.out.println(cmd);
        UploadDAO dao = new UploadDAO();
        //multipart/form-data 분석용 라이브러리
        // cos.jar - 기능적고 쓰기 쉬움, multiple file upload에 취약
        // apache commons file uplaod - 기능많고 쓰기 어려움.
        //realpath
        System.out.println(req.getServletContext().getRealPath(""));
        try{
            if(cmd.equals("/upload.file")){
                String uploadPath = req.getServletContext().getRealPath("files");
                File filePath = new File(uploadPath);
                if(!filePath.exists()){
                    filePath.mkdir();
                }
                int maxFileSize = 1024 * 1024 * 10;
                MultipartRequest multi = new MultipartRequest(req,uploadPath,maxFileSize,"utf8",new DefaultFileRenamePolicy());

                Enumeration<String> names = multi.getFileNames();
                while(names.hasMoreElements()){
                    String filename = names.nextElement();
                    System.out.println(filename);
                    if(multi.getFile(filename) != null){
                        String origin_name  = multi.getOriginalFileName(filename);
                        String sys_name  = multi.getFilesystemName(filename);
                        UploadDTO dto = new UploadDTO(0,origin_name,sys_name,159);
                        int result = dao.uploadFile(dto);
                        if(result>0){
                            System.out.println("정상 입력");
                        }else{
                            System.out.println("업로드 실패");
                        }
                    }
                }
                res.sendRedirect("/index.jsp");
            }else if(cmd.equals("/download.file")){
                String sys_name = req.getParameter("sysname");
                String origin_name = req.getParameter("originname");
                String path = req.getServletContext().getRealPath("files");
                System.out.println(path+"\\"+sys_name);
                System.out.println(origin_name);

                //제어문으로 브라우저마다 인코딩 방식을 지정해줘야함.
                origin_name = new String(origin_name.getBytes("utf8"), "ISO-8859-1");
                //해당 방식은 크롬에서 사용하는 인코딩 방식임.

                res.setHeader("Content-Disposition","attachment; filename="+origin_name);

            }else if(cmd.equals("/list.file")){
                List<UploadDTO> data = dao.selectAll();
                System.out.println(data.size());
                req.setAttribute("data",data);

                req.getRequestDispatcher("/index.jsp").forward(req,res);
//                res.sendRedirect("/index.jsp");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}