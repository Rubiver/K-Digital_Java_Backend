<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-31
  Time: 오전 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <style>
      *{
        box-sizing: border-box;
      }
      div{
        border: 1px solid black;
      }
    </style>
</head>
<body>
<script>
  window.onload = function(){

  }
</script>
<form action="/updateContents.board">
  <table border="1" align="center">
    <tr align="center">
      <td colspan="2"><strong>자유게시판 글</strong></td>
    </tr>
    <tr>
      <td align="center">
        글번호 : <input type="text" value="${boardCon.seq}" name="seq" readonly>
        작성자 : <input type="text" value="${boardCon.writer}" name="wrtier" readonly>
      </td>
      <td>
        <input type="text" name="title" placeholder="글 제목을 입력하세요." style="width: 600px;" value="${boardCon.title}">
      </td>
    </tr>
    <tr>
      <td colspan="2">
        <textarea placeholder="글 내용을 입력하세요" name="contents" style="width: 900px;height: 500px;">${boardCon.contents}</textarea>
      </td>
    </tr>
    <tr>
      <td align="right" colspan="2" class="btns">

        <c:choose>
          <c:when test="${login == boardCon.writer}">
            <input type="button" value="목록으로" id="return_btn">
            <input type="submit" value="수정" id="show_update_btn">
            <input type="button" value="삭제" id="delete_btn">
          </c:when>
          <c:otherwise>
            <input type="button" value="목록으로" id="return_btn">
          </c:otherwise>
        </c:choose>
      </td>
    </tr>
  </table>
</form>

<div class="container">
  <form action="/addReply.reply">
    <div class="row">
      <div class="col-12">
        댓글
      </div>
    </div>
    <div class="row">
      <div class="col-3">
        <input type="text" name="seq" id="seq" value="${boardCon.seq}" style="display: none">
        <span>Writer : </span>
        <input type="text" value='${login}' name="writer" id="writer">
      </div>
      <div class="col-8">
        <textarea name="contents" id="contents" class="w-100 h-50"></textarea>
      </div>
      <div class="col-1">
        <button id="reply_btn">작성</button>
      </div>
    </div>
</form>

    <c:forEach var="reply" items="${replies}" varStatus="status">
    <form action="/updateReply.reply" method="post">
      <div class="row">
        <input class="col" id="reply_seq" name="reply" style="display: none" value="${reply.seq}">
        <input class="col" name="seq" style="display: none" value="${boardCon.seq}">
        <div class="col-3">
            ${reply.writer}
        </div>
        <div class="col-7">
          <input type="text" value="${reply.contents}" name="contents" id="reply_contents" readonly style="width: 80%;height: 80%">
        </div>
        <div class="col-1">
            ${reply.write_date}
        </div>
        <div class="col-1">
          <c:choose>
            <c:when test="${login == reply.writer}">
              <button type="button" class="delete_reply">삭제</button>
              <button type="button" class="show_update_btn">수정</button>
              <button type="submit" class="update_reply" style="display: none">수정완료</button>
            </c:when>
            <c:otherwise>

            </c:otherwise>
          </c:choose>

        </div>
      </div>
    </form>
    </c:forEach>

  </div>

<script>
  $("#return_btn").on("click",function (){
    let check = "${latestPage}";
    if(check==""){
      location.href = "/moveToBoard.board?curPage=1";
    }else{
      location.href = "/moveToBoard.board?curPage=${latestPage}";
    }

    //
  });
  $("#update_btn").on("click",function (){
    let conf = confirm("수정 하시겠습니까?");
    if(conf) {
      window.open("/updateContents.board");
    }else{
      return 0;
    }
  });
  $("#delete_btn").on("click",function (){
    let conf = confirm("정말로 삭제하시겠습니까?");
    if(conf) {
      window.open("/deleteContents.board?seq=${boardCon.seq}");
    }else{
      return 0;
    }
  });
  $(".delete_reply").on("click",function (){
    let numberOfReply = $(this).parent().siblings("#reply_seq").text();
    let conf = confirm("댓글을 삭제하시겠습니까?");
    if(conf){
      location.href = "/deleteReply.reply?reply="+numberOfReply+"&seq=${boardCon.seq}";
    }
  });
  $(".show_update_btn").on("click",function (){
      $(this).siblings(".update_reply").css("display","flex");
      //$(".update_reply").css("display","flex");
      $("#reply_contents").attr("readonly",false);
      //$(this).siblings(".show_update_btn").css("display","none");
      $(".show_update_btn").css("display","none");
  });
  <%--$(".update_reply").on("click",function (){--%>
  <%--  let numberOfReply = $(this).parent().siblings("#reply_seq").text();--%>
  <%--  location.href = "/updateReply.reply?seq=${boardCon.seq}&reply="+numberOfReply+"&contents="+;--%>
  <%--});--%>

</script>
</body>
</html>
