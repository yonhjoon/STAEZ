<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>concert</title>

    <!-- 적용css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/concert/concertDetailView.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/concert/concertDetailInformationViewAjax.css">

    <!-- 달력 -->
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js'></script>
</head>

<body>
    <div>
        <jsp:include page="../common/header.jsp" />
    </div>

    <!-- 공연 navi -->
    <div>
        <ul class="concert-ul">
            <li><a href=""><span><h2>뮤지컬</h2></span></a></li>
            <li><a href=""><span><h2>클래식</h2></span></a></li>
            <li><a href=""><span><h2>국악</h2></span></a></li>
            <li><a href=""><span><h2>대중음악</h2></span></a></li>
            <li><a href=""><span><h2>연극</h2></span></a></li>
            <li><a href=""><span><h2>서커스/마술</h2></span></a></li>
            <li><a href=""><span><h2>기타</h2></span></a></li>
        </ul>
    </div>

    <!-- 공연상세페이지 위쪽(노란 블럭 안쪽) -->
    <section class="concert-detail-upper-section">
        <div class="concert-detail-upper-div concert-detail-body-div">
            <div>
                <img src="${pageContext.request.contextPath}/resources/img/concert/공연상세페이지임시.png" alt="">
            </div>
            <!-- 좋아요 버튼 구현하기
                 https://velog.io/@parkheeyeun/%EC%A2%8B%EC%95%84%EC%9A%94-%EB%B2%84%ED%8A%BC-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0 -->
            <div class="concert-detail-like">
                <div class="concert-detail-like-jim">
                    <img id="concert-detail-like-heartImg-background" src="${pageContext.request.contextPath}/resources/img/concert/heart-background.png" alt="">
                    <img id="concert-detail-like-heartImg" src="${pageContext.request.contextPath}/resources/img/concert/heart-header.png" alt="">
                    <span>127</span>
                </div>
                <div class="concert-detail-like-commu"><a href=""><span><b>천개의 파랑 커뮤니티로 가기</b></span></a></div>
            </div>
        </div>


        <div class="concert-detail-body-div">
            <table class="concert-detail-table">
                <tr>
                    <td><img id="concert-detail-starImg" src="${pageContext.request.contextPath}/resources/img/concert/tempStar.png" alt=""></td>
                </tr>
                <tr>                    
                    <td><h2>&lt;천개의 파랑&gt;</h2></td>
                <tr>
                <tr>                    
                    <td></td>
                <tr>
                    <td><b><span>공연기간&nbsp&nbsp</span></b><span>2024.05.12 ~ 2024.05.26</span></td>
                </tr>
                <tr>
                    <td><b><span>공연장소&nbsp&nbsp</span></b><span>예술의전당 CJ토월극장</span></td>
                </tr>
                <tr>
                    <td><b><span>공연시간&nbsp&nbsp</span></b><span>140분</span></td>
                </tr>
                <tr>
                    <td><b><span>관람연령&nbsp&nbsp</span></b><span>초등학생 이상</span></td>
                </tr>
                <tr>
                    <td><b><span>가격&nbsp&nbsp</span></b><span>R석 90,000원 | S석 60,000원 | A석 30,000원</span></td>
                    <!-- 가격을세로로 적고싶을 때 -->
                    <!-- <td colspan="2">
                        <table>
                            <tr>
                                <td><b><span>가격&nbsp&nbsp</span></b></td>
                                <td><span>R석 90,000원</span></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span>S석 60,000원</span></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><span>A석 30,000원</span></td>
                            </tr>
                        </table>
                    </td> -->
                </tr>

                    <td><b><span>주최&nbsp&nbsp</span></b><span>서울예술단</span></td>
                </tr>
            </table>
        </div>
        
        <!-- 달력구현 -->
            <div class="concert-calendar-container">        
				<div class="calendar">
					<div class="calendar-top">
						<button id="previous"></button>
						<h3></h3>
						<button id="next"></button>
					</div>
					<div class="days-area">
						<ul class="days">
							<li>일</li>
							<li>월</li>
							<li>화</li>
							<li>수</li>
							<li>목</li>
							<li>금</li>
							<li>토</li>
						</ul>
						<ul class="dates">
							<!-- 스크립트 이용해서 넣을 것 -->
						</ul>
					</div>
				</div>
			
                    <!-- <div class="concert-detail-body-div">
                        <div id='calendar'></div>
                            <script>
                                document.addEventListener('DOMContentLoaded', function() {
                                var calendarEl = document.getElementById('calendar');
                                var calendar = new FullCalendar.Calendar(calendarEl, {
                                    initialView: 'dayGridMonth'
                                });
                                calendar.render();
                                });
                            </script>
                        </div> -->

                <button class="reservation-button"><span>예매하기</span></button>
            </div>
    </section>

        <!-- 공연상세페이지 아래쪽 navi-->
    <section class="concert-detail-down-section">
        <ul class="concert-down-ul">
            <li><a href=""><h3>공연상세정보</h3></a></li>
            <li><a href=""><h3>판매정보</h3></a></li>
            <li><a href=""><h3>한줄평(35)</h3></a></li>
            <li><a href=""><h3>관람후기(10)</h3></a></li>
        </ul>
    </section>
    <hr class="concert-detail-hr">


<!-- 공연상세페이지 아래쪽: ajax(공연상세정보, 판매정보, 한줄평, 관람후기 내용) -->
<!-- 공연상세정보 -->
    <section>
        <div class="concert-detail-subject">
            <span><h3>공지사항</h3></span>
            <br>
            <img src="./천개의파랑 공지사항.png" alt="">
        </div>
        <br><br>
        <div class="concert-detail-subject">
            <span><h3>작품소개</h3></span>
            <br>
            <img src="./천개의파랑 작품소개.png" alt="">
        </div>
    </section>

	<div>
		<jsp:include page="../common/footer.jsp" />
	</div>

    
</body>
</html>