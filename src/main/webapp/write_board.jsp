<%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-30
  Time: 오전 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
</head>
<body>
<form action="/writeContents.board">
<table border="1">
  <tr align="center">
    <td colspan="2"><strong>자유게시판 글 작성하기</strong></td>
  </tr>
  <tr>
    <td align="center">
      <select name="tag">
        <option value="뉴스">뉴스</option>
        <option value="유머">유머</option>
        <option value="신고">신고</option>
      </select>
    </td>
    <td>
      <input type="text" name="title" placeholder="글 제목을 입력하세요." style="width: 600px;">
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <textarea placeholder="글 내용을 입력하세요" name="contents" style="width: 900px;height: 500px;"></textarea>
    </td>
  </tr>
  <tr>
    <td align="right" colspan="2">
      <input type="button" value="목록으로" id="return_btn">
      <input type="submit" value="작성하기" id="write_btn">
    </td>
  </tr>
</table>
</form>
<script>
  $("#return_btn").on("click",function (){
    location.href = "/moveToBoard.board";
  });
</script>
</body>
</html>
