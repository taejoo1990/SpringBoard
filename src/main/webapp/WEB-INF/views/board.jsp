<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="loginId"
       value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'Logout'}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/menu.css'/>">
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<div style="text-align:center">
    <h2 id="headLine">${mode=="write"?'作成' : "読み取り"}</h2>
    <form id="form" method="post">
        <div class="">
            <input name="page" hidden value="${param.page}"/>
            <input name="pageSize" hidden value="${param.pageSize}"/>
            <div class="header">
                <div>
                <label for="bno">bno</label>
                <input type="text" name="bno" id="bno" value="${boardDto.bno}"
                       } ${mode=="write"?'hidden' : "readonly"}>
                </div>
                <div>
                <label for="title">タイトル</label>
                <input type="text" name="title" id="title"
                       value="${boardDto.title}" ${mode=="write"?'placeholder="タイトル入力"' : "readonly"}>
                </div>
            </div>
            <div>
                <textarea name="content" id="content" cols="30"
                          rows="10" ${mode=="write"?'' : "readonly"}>${boardDto.content}</textarea>
            </div>
            <div>
                <button type="button" id="wrtie" class="btn-write" 　
                        onclick="register()" ${mode=="write"?'' : 'hidden'}  >登録
                </button>
                <button type="button" id="modifyBtn" class="btn-write"
                        onclick="modify()" ${mode=="write"?'hidden' : ""} >修正
                </button>
                <button type="button" id="remove" class="btn-write"
                        onclick="deleteThis()" ${mode=="write"?'hidden' : ""}>削除
                </button>
                <button type="button" id="listBtn" class="btn-write" onclick="moveList()">リスト</button>
            </div>
    </form>
</div>
<script>
    const msg = "${param.msg}";
    if (msg === "WRT_ERR") alert("登録失敗");

    function modify() {
        document.getElementById("title").removeAttribute("readonly");
        document.getElementById("content").removeAttribute("readonly");
        document.getElementById("remove").setAttribute("hidden","hidden");
        const modifyBtn = document.getElementById("modifyBtn");
        modifyBtn.innerHTML="登録";
        modifyBtn.setAttribute("onclick","confirmModify()");

    }
    function confirmModify(){
        console.log("method called")
        const form = document.getElementById("form");
        form.setAttribute("action", "<c:url value='/board/modify'/>")
        form.submit();
    }

    function moveList() {
        location.href = "<c:url value="/board/list?page=${param.page}&pageSize=${param.pageSize}"/>";
    }

    function deleteThis() {
        const form = document.getElementById("form");
        form.setAttribute("action", "<c:url value="/board/remove"/>")
        if (!confirm("削除実行")) return;
        form.submit();
    }

    function register() {
        const form = document.getElementById("form");
        form.setAttribute("action", "<c:url value="/board/write"/>")
        form.submit();
    }
</script>
</body>
</html>