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
    <input type="file" name="file"><br>
    <input type="submit">
    <fieldset>
        <legend>
            File list
        </legend>
        <c:forEach var="file" items="${data}" varStatus="status">
            ${file.seq}  /  <a href="/files/${file.sys_name}">${file.origin_name}</a>  /  ${file.sys_name}  /  ${file.board_seq} <br>
        </c:forEach>
    </fieldset>
    <button type="button" id="load">파일 목록 불러오기</button>
</form>
<script>
    $("#load").on("click",function (){
        location.href = "/list.file";
    });
</script>
</body>
</html>