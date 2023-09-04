<%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-28
  Time: 오후 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="http://java.sun.com/jsp/jstl/sql" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        table{
            margin: auto;
            width: 70%;
            height: 70%;
        }
        div{
            border: 0px solid black;
            text-align: center;
        }

    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
</head>
<body>
<div class="container">
    <form id="sign" action="/addMember.member" method="post">
        <div class="row p-2">
            <div class="col fs-4">회원가입</div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col-10">
                <div class="input-group flex-nowrap">
                    <input type="text" class="form-control" placeholder="ID 입력" aria-label="Username" aria-describedby="addon-wrapping" id="id" name="id">
                </div>
            </div>
            <div class="col-2">
                <button id="check_btn" type="button">중복확인</button>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col-12">
                <div class="input-group flex-nowrap">

                    <input type="password" class="form-control" placeholder="PW 입력" aria-label="Username" aria-describedby="addon-wrapping" id="pw1" onkeyup="checkPW()">
                </div>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="password" class="form-control" placeholder="PW 입력 확인" aria-label="Username" aria-describedby="addon-wrapping" id="pw2" onkeyup="checkPW()" name="pw">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <span id="caution">안내 문구</span>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="text" class="form-control" placeholder="이름 입력" aria-label="Username" aria-describedby="addon-wrapping" id="name" name="name">
                </div>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="text" class="form-control" placeholder="전화번호 입력" aria-label="Username" aria-describedby="addon-wrapping" id="phone" name="phone">
                </div>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="text" class="form-control" placeholder="이메일 입력" aria-label="Username" aria-describedby="addon-wrapping" id="email" name="email">
                </div>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="text" class="form-control" placeholder="우편번호" aria-label="Username" aria-describedby="addon-wrapping" id="postcode" readonly name="zipcode">
                </div>
            </div>
            <div class="col">
                <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" class="btn btn-light"><br>
            </div>
            <div class="col"></div>
        </div>
        <div class="row p-2">
            <div class="col">
                <div class="input-group flex-nowrap">

                    <input type="text" class="form-control" placeholder="주소" aria-label="Username" aria-describedby="addon-wrapping" id="address" name="address1">
                </div>
            </div>
        </div>
        <div class="row p-2">
            <div class="col">
                <div class="input-group flex-nowrap">
                    <input type="text" class="form-control" placeholder="상세주소(선택)" aria-label="Username" aria-describedby="addon-wrapping" id="detailAddress" name="address2">
                </div>
            </div>
        </div>
        <div class="row p-2 d-flex justify-content-center align-items-center">
            <div class="col">
                <input type="submit" class="btn btn-light" id="regi_btn">
                <button id="refresh" class="btn btn-light">다시 작성하기</button>
            </div>
        </div>
    </form>
</div>
<hr>

<script>
    var idcheck = false;
    let id = document.getElementById("id");
    let pw1 = document.getElementById("pw1");
    let pw2 = document.getElementById("pw2");
    let name = document.getElementById("name");
    let phone = document.getElementById("phone");
    let email = document.getElementById("email");
    let caution = document.getElementById("caution");

    let idRegex = /^[\w\d]{7,13}$/;
    let pwRegex = /^([\w\d\W]){8,}$/;
    //let pwRegex = /^[\w\d]{8,}$/;
    //^([\w\d\W]){8,}$ 특수문자 포함 -> /\W/ 정규표현식을 한번 거쳐서 확인해도 됨.
    let nameRegex = /^[가-힣]{2,5}$/;
    let phoneRegex = /^010(-?[\d]{4}){2}$/;
    let emailRegex = /^([\w\d]+?)@(.+?)\.(com)?(net)?$/;

    let sign = document.getElementById("sign");
    let refresh = document.getElementById("refresh");

    caution.style.display = "none";

    $("#check_btn").on("click",function (){
        console.log(id.value);
        window.open("/idcheck.member?id="+id.value,"","width=300, height=200");
        idcheck = true;
    });

    sign.onsubmit = function(){
        if(idRegex.test(id.value)){
            console.log("통과");
        }else{
            console.log("아이디 비어있음");
            id.focus();
            return false;
        }

        if(pwRegex.test(pw1.value)){
            console.log("1차 통과");
            if(pwRegex.test(pw2.value)){
                console.log("2차 통과");
                if(pw1.value==pw2.value){
                    console.log("통과");
                    caution.style.display = "none";
                }else{
                    console.log("비밀번호 같지 않음.");
                    pw1.focus();
                    pw2.focus();
                    caution.style.display = "block";
                    return false;
                }
            }else{
                console.log("획안 패스워드 규칙에 맞지않음");
                pw2.focus();
                caution.style.display = "block";
                return false;
            }
        }else{
            console.log("패스워드 규칙에 맞지않음");
            caution.style.display = "block";
            return false;
        }

        if(nameRegex.test(name.value)){
            console.log("톹과");
        }else{
            console.log("이름 규칙에 맞지않음");
            name.focus();
            return false;
        }

        if(emailRegex.test(email.value)){
            console.log("통과");
        }else{
            console.log("이메일 규칙에 맞지않음");
            email.focus();
            return false;
        }
        if(phoneRegex.test(phone.value)){
            console.log("통과");
        }else{
            console.log("실패");
            phone.focus();
            return false;
        }
        if(document.getElementById('postcode').value == ""){
            console.log("우편번호 비어있음.");
            document.getElementById('postcode').focus();
            return false;
        }

        if(idcheck){
            return idcheck;
        }else{
            return idcheck;
        }

        if(idcheck){
            window.open("/addMember.member");
            // window.open("/addMember.member?id="+id.value+"&pw="+pw2.value+"&name="+name.value+"&phone="+phone.value+"&email="+email.value+"&zipcode="+
            //     document.getElementById('postcode').value+"&address1="+document.getElementById("address").value+"" +
            //     "&address2="+document.getElementById("detailAddress").value);
        }else{
            alert("중복검사를 해야합니다.");
        }

    }
    // $("#regi_btn").on("click",function (){
    //     if(idcheck){
    //         window.open("/addMember?id="+id.value+"&pw="+pw2.value+"&name="+name.value+"&phone="+phone.value+"&email="+email.value+"&zipcode="+
    //             document.getElementById('postcode').value+"&address1="+document.getElementById("address").value+"" +
    //             "&address2="+document.getElementById("detailAddress").value);
    //     }else{
    //         alert("중복검사를 해야합니다.");
    //     }
    //
    // });

    function checkPW(){
        if(pwRegex.test(pw1.value)){
            console.log("규칙 통과");
            if(pw1.value==pw2.value){
                console.log("통과");
                caution.style.display = "none";
            }else{
                console.log("비밀번호 같지 않음.");
                caution.innerHTML = "비밀번호 같지 않음.";
                caution.style.display = "block";
                return false;
            }
        }else{
            console.log("패스워드 규칙에 맞지않음");
            caution.innerHTML = "대소문자 및 숫자로 이루어진 비밀번호를 작성하세요.";
            caution.style.display = "block";
            return false;
        }
    }

    refresh.onclick = function(){
        id.value = "";
        pw1.value = "";
        pw2.value = "";
        name.value = "";
        phone.value = "";
        email.value = "";
        document.getElementById('postcode').value = "";
        document.getElementById("address").value = "";
        document.getElementById("detailAddress").value = "";
    }


    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }

                }

                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddress").focus();
            }
        }).open();


    }
</script>
</body>
</html>