<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.BoardDTO" %><%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-29
  Time: 오후 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<table border="1" align="center" width = "80%">
  <tr>
    <td colspan="5" align="center"> <strong>자유 게시판</strong> </td>
  </tr>
  <tr height = "20" align="center">
    <td width="5%">  </td>
    <td width="60%">제목</td>
    <td>작성자</td>
    <td>날짜</td>
    <td>조회</td>
  </tr>

    <c:forEach var="board" items="${board_data}" varStatus="status">
  <tr>

    <td>${ board.seq }</td>
    <td>
      <c:set var="seq" value="${board.seq}" />
      <a href="/addViewCount.board?seq=${seq}">
          ${board.title}
      </a>

    </td>
    <td>${ board.writer }</td>
    <td>${ board.write_date }</td>
    <td>${ board.view_count }</td>

  </tr>
  </c:forEach>

  <tr height = "20">
    <td colspan="5" align="center" class="navigation">

    </td>
<%--    <td colspan="5" align="center">1 2 3 4 5 6 7 8 9 10</td>--%>
  </tr>
  <tr>
    <td colspan="2" align="right"><input type="button" value="돌아가기" id="home_btn"></td>
    <td colspan="3" align="right"><input type="button" value="작성하기" id="write_btn"></td>
  </tr>
</table>
<div class="container">
  <div class="row">
    <div class="col-12">
      <input type="text" placeholder="검색" name="search" id="search_input">
      <button type="button" id="search_btn">검색</button>
    </div>
  </div>
</div>
<script>
  $("#home_btn").on("click",function (){
    location.href = "/index.jsp";

  });
  $("#write_btn").on("click",function (){
    location.href = "/writeBoard.board";
    // window.close();
    // window.open("/writeBoard.board");
  });
  $("#search_btn").on("click",function (){
    let str = $("#search_input").val();
    location.href = "/searchBoard.board?search="+str+"&curPage="+1;
  });
  let recordTotalCount = ${recordTotalCount};
  let recordCountPerPage = ${recordCountPerPage};
  let naviCountPerPage = ${naviCountPerPage};

  console.log(recordCountPerPage,recordTotalCount,naviCountPerPage);
  let totalPageCount = 0; //만들어질 페이지의 개수
  if(recordTotalCount % recordCountPerPage > 0){
    totalPageCount = Math.ceil(recordTotalCount /  recordCountPerPage);
    console.log(totalPageCount);
  }else{
    totalPageCount = Math.ceil(recordTotalCount /  recordCountPerPage);
    console.log(totalPageCount);
  }
  let currentPage = ${latestPage};
  if( currentPage < 1){
    currentPage = 1;
  }else if(currentPage > totalPageCount){
    currentPage = totalPageCount;
  }
  console.log(currentPage);
  let startNavi = Math.floor((currentPage-1)/naviCountPerPage)*naviCountPerPage+1;
  console.log("test : "+(currentPage-1)/naviCountPerPage);
  let endNavi = startNavi+naviCountPerPage-1;

  if(endNavi > totalPageCount){
    endNavi = totalPageCount;
  }
  console.log("start : "+startNavi+" end : "+endNavi);
  let needPrev = true;
  let needNext = true;
  let str = "${search}";
  console.log(str);
  if(startNavi == 1){
    needPrev = false;
  }
  if(endNavi == totalPageCount){
    needNext = false;
  }
  console.log(startNavi,endNavi);
  if(needPrev){
    $(".navigation").append("<a href='/searchBoard.board?curPage="+(startNavi-1)+"&search="+str+"' id='prev'>< </a>");
  }
  for(let i= startNavi; i<=endNavi; i++){
    $(".navigation").append("<a href='/searchBoard.board?curPage="+i+"&search="+str+"'>"+i+" </a>");
  }
  if(needNext){
    $(".navigation").append("<a href='/searchBoard.board?curPage="+(endNavi+1)+"&search="+str+"' id='next'> ></a>");
  }

  // if(state){
  //
  // }else{
  //   let needPrev = true;
  //   let needNext = true;
  //   if(startNavi == 1){
  //     needPrev = false;
  //   }
  //   if(endNavi == totalPageCount){
  //     needNext = false;
  //   }
  //   console.log(startNavi,endNavi);
  //   if(needPrev){
  //     console.log(" < movetoboard");
  //     $(".navigation").append("<a href='/moveToBoard.board?curPage="+(startNavi-1)+"' id='prev'>< </a>");
  //   }
  //   for(let i= startNavi; i<=endNavi; i++){
  //     $(".navigation").append("<a href='/moveToBoard.board?curPage="+i+"'>"+i+"</a> ");
  //   }
  //   if(needNext){
  //     console.log(" > movetoboard");
  //     $(".navigation").append("<a href='/moveToBoard.board?curPage="+(endNavi+1)+"' id='next'> ></a>");
  //   }
  // }

  //필요 정보(네비게이션 구현까지)
  //curPage(Controller에서), Total Record ammount(DAO에서), Show Record per Page(Constants에서), Navigator Nums(1-10..)(Constants에서)

</script>
</body>
</html>
