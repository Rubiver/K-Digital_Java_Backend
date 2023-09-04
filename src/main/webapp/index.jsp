<%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-28
  Time: 오전 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
            border: 1px black solid;
        }
        th{
            text-align: center;
        }
        td{
            max-width: 500px;
            width: 100%;
            display: flex;
            justify-content: center;
            align-content: center;
        }
    </style>
</head>
<body>
<c:choose>
    <c:when test="${login == null}">
        <section>
            <form action="/Login.member" method="post">
                <table align="center" border="1">
                    <tr>
                        <td>
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="User ID" aria-label="Username" aria-describedby="basic-addon1" name="id" id="id">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="input-group mb-3">
                                <input type="password" class="form-control" placeholder="User PW" aria-label="Username" aria-describedby="basic-addon1" name="pw" id="pw">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input type="submit" value="로그인" id="login_btn" class="btn btn-primary">
                            <input type="button" value="회원가입" id="sign_btn" class="btn btn-primary">
                        </td>
                    </tr>
                </table>
            </form>
        </section>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="row">
                <div class="col-8">
                    <h1>Login page</h1>
                </div>
                <div class="col-4">
                    <div class="row">
                        <div class="col-12">
                            <p>ID : ${login}</p>
                            <p>Name : ${name}</p>
                        </div>
                        <div class="col-3">
                            <a href="/moveToBoard.board">게시판</a>
                        </div>
                        <div class="col-3">
                            <a href="/mypage.member">마이페이지</a>
                        </div>
                        <div class="col-3">
                            <a href="/Logout.member">로그아웃</a>
                        </div>
                        <div class="col-3">
                            <a href="#" id="delete">회원 탈퇴</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                Articles
            </div>
            <div class="row">
                Footer
            </div>
        </div>



    </c:otherwise>
</c:choose>


<script>
    let id = $("#id");
    let pw = $("#pw");
    let btn = $("#login_btn");

    $("#sign_btn").on("click",function (){
        location.href = "member/signup.jsp";
    });

    $("#delete").on("click",function (){
        let conf = confirm("정말로 삭제하시겠습니까?");
        if(conf){
            window.open("/deleteMember.member");
        }
    });
</script>
</body>
</html>
