<%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-09-06
  Time: 오전 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
</head>
<body>
    <fieldset>
        <legend>
            AJAX Example01
        </legend>
        <button id="ajax01">단순 요청</button>
    </fieldset>
    <fieldset>
        <legend>
            AJAX Example02
        </legend>
        <button id="ajax02">값을 담아서 요청</button>
    </fieldset>
    <fieldset>
        <legend>
            AJAX Example03
        </legend>
        <button id="ajax03">서버로부터 응답 받기</button>
        <input type="text" id="ajax_input01">
    </fieldset>
    <fieldset>
        <legend>
            AJAX Example04
        </legend>
        <button id="ajax04">배열 요청</button>
    </fieldset>
    <fieldset>
        <legend>
            AJAX Example05
        </legend>
        <button id="ajax05">객체 요청</button>
    </fieldset>
<script>
    //AJAX : Asynchronous Javascript and XML
    //페이지 전환없이 서버에 데이터 보내는 방법 - Infinity Scrolling, ID 실시간 중복 검사, RestFul Service
    //1. Form + submit
    //2. Request URL에 GET방식으로
    //3. window.open으로
    $("#ajax01").on("click",function (){
        $.ajax({
            url:"/exam01.ajax"
        });
    });
    $("#ajax02").on("click",function (){
        $.ajax({
            url:"/exam02.ajax",
            data:{
                param1:10,
                param2:"Hi"
            },
            type:"post"
        });
    });
    $("#ajax03").on("click",function (){
        $.ajax({
            url:"/exam03.ajax"
        }).done(function (res){
            $("#ajax_input01").val(res);
        });
    });
    $("#ajax04").on("click",function (){
        $.ajax({
            url:"/exam04.ajax"
        }).done(function (res){
            // let arr = JSON.parse(res);
            // console.log(arr[0]);
        });
    });
    $("#ajax05").on("click",function (){
        $.ajax({
            url:"/exam05.ajax"
        }).done(function (res){

        });
    });
</script>
</body>
</html>