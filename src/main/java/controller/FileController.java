package controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("*.file")
public class FileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cmd = req.getRequestURI();
        System.out.println(cmd);
        //multipart/form-data 분석용 라이브러리
        // cos.jar - 기능적고 쓰기 쉬움, multiple file upload에 취약
        // apache commons file uplaod - 기능많고 쓰기 어려움.
        //realpath
        System.out.println(req.getServletContext().getRealPath(""));
        if(cmd.equals("/upload.file")){
            String uploadPath = req.getServletContext().getRealPath("files");
            File filePath = new File(uploadPath);
            if(!filePath.exists()){
                filePath.mkdir();
            }
            int maxFileSize = 1024 * 1024 * 10;
            MultipartRequest multi = new MultipartRequest(req,uploadPath,maxFileSize,"utf8",new DefaultFileRenamePolicy());
            String msg = req.getParameter("message");
            System.out.println("정상 작동 - "+msg);
        }else if(cmd.equals("/download.file")){

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }
}