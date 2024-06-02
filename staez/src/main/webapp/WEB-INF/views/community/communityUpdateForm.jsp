<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value='/resources/css/community/communityIncertForm.css'/>">
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/views/common/header.jsp" />
        <script src="<c:url value='/resources/js/api/communityapi.js'/>"></script>
        <script src="<c:url value='/resources/js/community/communityUpdateForm.js'/>"></script>
    </header>
    <main>
        <div id="community-wrapper">
            <ul id="community-nav" align="left"></ul>
            <form id="community-contents"  align="left" method="post">
                <input type="hidden" name="boardNo" value="${board.boardNo}">
                <input type="hidden" name="userNo" value="${loginUser.userNo}">
                <input type="hidden" name="genre" value="${genre}">
                <input type="hidden" name="division" value="${division}">
                <li>
                    <h3>제목</h3>
                    <input type="text" name="boardTitle" value="${board.boardTitle}">
                </li>
                <li id="community-genre">
                    <h3>장르</h3>
                </li>
                <li id="community-division">
                    <h3>구분</h3>
                </li>
                <li id="community-tag">
                    <h3>태그</h3>
                    <div id="community-search">
                        <button>
                            <img src="<c:url value='/resources/img/community/communityMain/search-icon.png'/>" alt="">
                        </button>
                        <input type="text" name="concertTitle" onkeydown="searchResult(this)" onkeyup="searchResult(this)" value="${concert.concertTitle}">
                        <input type="hidden" name="concertNo" value="${concert.concertNo}">
                    </div>
                    <ul class="search-list">
                    </ul>
                </li>
                <li>
                    <div id="summernote">
                        ${board.boardContent}
                    </div>
                </li>
                <li id="community-submit">
                    <button class="btn-staez purple" type="button"><h3>작성</h3></button>
                    <button class="btn-staez purple" type="button"><h3>목록</h3></button>
                </li>
            </form>
        </div>
    </main>
    <footer>
        <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </footer>
</body>
</html>