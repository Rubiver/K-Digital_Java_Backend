<%@ page import="dto.MemberDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: kwon
  Date: 2023-08-29
  Time: 오전 9:24
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
            border: 0px solid black;
        }
        .container{
            border: 2px solid black;
            border-radius: 5px;
        }
        .row{
            margin-top: 20px;
        }
        .row div{
            display: flex;
            justify-content: center;
            align-content: center;
        }
        input{
            width: 100%;
        }


    </style>
</head>
<body>
<%--${data.get(0).getId()} 가능. --%>

<form action="/updateMember.member">
    <div class="container mt-5">
        <div class="row">
            <div class="col-2">
                가입 날짜 :
            </div>
            <div class="col-8">
                ${formedDate}
            </div>
            <div class="col-2">

            </div>
        </div>
        <div class="row">
            <div class="col-4">
                ID :
            </div>
            <div class="col-8">
                <input type="text" value='${data.get(0).getId()}' name="id" readonly>

            </div>
        </div>
        <div class="row">
            <div class="col-4">
                이름 :
            </div>
            <div class="col-8">
                <input type="text" name="name" readonly value=${data.get(0).getName()}>

            </div>
        </div>
        <div class="row">
            <div class="col-4">
                이메일 :
            </div>
            <div class="col-8">
                <input type="text" name="email" readonly value=${data.get(0).getEmail()}>

            </div>
        </div>
        <div class="row">
            <div class="col-3">
                우편번호 :
            </div>
            <div class="col">
                <input type="text" id="postcode" name="zipcode" readonly value=${data.get(0).getZipcode()}>
            </div>
            <div class="col-3">
                <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" class="btn btn-light">
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                주소 :
            </div>
            <div class="col-8">
                <input type="text" id="address" name="address1" readonly value='${data.get(0).getAddress1()}'>
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                상세주소 :
            </div>
            <div class="col-8">
                <input type="text" id="detailAddress" name="address2" readonly value='${data.get(0).getAddress2()}'>
            </div>

        </div>
        <div class="row">
            <div class="col-6 d-flex justify-content-center">
                <button type="button" class="btn btn-secondary" id="home_btn">홈페이지로</button>
            </div>
            <div class="col-6 d-flex justify-content-center">
                <button type="button" class="btn btn-secondary" id="update_btn">수정하기</button>
            </div>
        </div>
        <div class="row" id="update_div" style="display: none">
            <div class="col-6 d-flex justify-content-center">
                <input type="submit" class="btn btn-secondary w-50" value="수정 확정">

            </div>
            <div class="col-6 d-flex justify-content-center">
                <button type="button" class="btn btn-secondary" id="cancle_btn">취소</button>
            </div>
        </div>
    </div>
</form>
<c:forEach var="reply" items="${replies}" varStatus="status">
        <div class="row">
            <input class="col" id="reply_seq" name="reply" style="display: none" value="${reply.seq}">
            <input class="col" name="seq" style="display: none" value="${boardCon.seq}">
            <div class="col-3">
                    Writer : ${reply.writer}
            </div>
            <div class="col-6">
                    ${reply.contents}
            </div>
            <div class="col-2">
                <a href="/getReply.reply?seq=${reply.board_seq}">게시글로 가기</a>
            </div>
            <div class="col-1">
                    ${reply.write_date}
            </div>
        </div>
</c:forEach>
<script>
    $("#home_btn").on("click",function (){
        location.href = "/index.jsp";
    });

    $("#update_btn").on("click",function (){
        $("input").attr("readonly",false);
        console.log("asdasdasdas");
        $("#update_div").css("display","flex");
    });

    $("#cancle_btn").on("click",function (){
        location.reload();
    });

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
        }).open();}

</script>
</body>
</html>

<%--<%--%>
<%--    List<MemberDTO> data = (List<MemberDTO>)request.getSession().getAttribute("data");--%>
<%--    PrintWriter print = response.getWriter();--%>
<%--    print.println("<div class='container'>");--%>

<%--    for(MemberDTO dto : data){--%>
<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("가입 날짜 : "+dto.getSignup_date());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>

<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("ID : "+dto.getId());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>


<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("이름 : "+dto.getName());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>

<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("전화번호 : "+dto.getPhone());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>

<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("우편번호 : "+dto.getZipcode());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>

<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("주소 : "+dto.getAddress1());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>

<%--        print.println("<div class='row'>");--%>
<%--        print.println("<div class='col-12'>");--%>
<%--        print.println("상세 주소 : "+dto.getAddress2());--%>
<%--        print.println("</div>");--%>
<%--        print.println("</div>");--%>
<%--    }--%>

<%--    print.println("</div>");--%>
<%--%>--%>