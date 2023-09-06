<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
</head>
<body>
<br/>
<form action="/upload.file" method="post" enctype="multipart/form-data">
    <input type="text" name="message"><br>
    <div>
        <span>파일첨부</span>
        <button id="add" type="button">+</button><br>
        <fieldset id="mainCon">
            <legend>첨부 목록</legend>
        </fieldset>
    </div>
    <input type="submit">
    <hr>
    <fieldset>
        <legend>
            File list
        </legend>
        <c:forEach var="file" items="${data}" varStatus="status">
<%--            ${file.seq}  /  <a href="/files/${file.sys_name}">${file.origin_name}</a>  /  ${file.sys_name}  /  ${file.board_seq} <br>--%>
            ${file.seq}  /  <a href="/download.file?sysname=${file.sys_name}&originname=${file.origin_name}">${file.origin_name}</a>  /  ${file.sys_name}  /  ${file.board_seq} <br>
        </c:forEach>
    </fieldset>
<%--    다운로드를 파일에 대한 다이렉트 링크 처리 시 코드구현이 매우 쉽지만 다운로드 권한 통제, 로그기록, 다운로드 시 브라우저ㅓ 액션 처리가 불가--%>
<%--    다운로드를 컨트롤러를 거쳐 처리할 경우 권한통제, 로그기록, 다운로드 액션처리가 가능하지만 코드 구현이 어려움.--%>
    <button type="button" id="load">파일 목록 불러오기</button>
</form>
<script>
    $("#load").on("click",function (){
        location.href = "/list.file";
    });
    let count = 0;
    $("#add").on("click",function(){
        if(++count > 5){
            alert("최대 5개 까지 입니다.");
            count=5;
            return false;
        }
        let p = $("<span>");
        let input = $("<input>");
        input.attr("type","file");
        input.attr("name","file"+count);

        let delbtn = $("<a>");
        delbtn.html("X");
        delbtn.addClass("del");
        delbtn.attr("href","#");
        // delbtn.css({
        //     "text-decoration": "underline",
        //     "color": "blue"
        // });
        // delbtn.hover(function(){
        //     $(this).css("cursor","pointer");
        // });
        p.append(input);
        p.append(delbtn);
        p.append("<br>");
        $("#mainCon").append(p);

        $(delbtn).on("click",function(){
            count = count-1;
            p.remove();
            console.log(count);
        });
    });
</script>
</body>
</html>