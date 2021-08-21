<%@page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css"
        integrity="sha512-aOG0c6nPNzGk+5zjwyJaoRUgCdOrfSDhmMID2u4+OIslr0GjpLKo7Xm0Ao3xmpM4T8AmIouRkqwj1nrdVsLKEQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.structure.min.css"
        integrity="sha512-oM24YOsgj1yCDHwW895ZtK7zoDQgscnwkCLXcPUNsTRwoW1T1nDIuwkZq/O6oLYjpuz4DfEDr02Pguu68r4/3w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.theme.min.css"
        integrity="sha512-9h7XRlUeUwcHUf9bNiWSTO9ovOWFELxTlViP801e5BbwNJ5ir9ua6L20tEroWZdm+HFBAWBLx2qH4l4QHHlRyg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />


    <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@3.5.0/dist/chart.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
        integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="/assets/js/world.js"></script>

    <link rel="stylesheet" href="/assets/css/world.css">
    <title>Document</title>
</head>
<div class="container">
    <%@include file="/WEB-INF/views/includes/menu.jsp"%>
    <div class="right_area">
        <div class="data_row">
            <h1>세계 코로나 확진자수</h1>
            <div class="def_sum">
                <p>확진자</p>
                <p><span id="natDefCnt">-</span> <span id="defIncrease">-</span> </p>
            </div>
            <div class="death_sum">
                <p>사망자</p>
                <p><span id="natDeathCnt">-</span> <span id="deathIncrease">-</span> </p>
            </div>
        </div>
        <div class="data_row">
            <h1>오늘의 확진자수</h1>
            <div class="def_today">
                <p><span id="todayDef">-</span></p>
            </div>
            <div class="versus">
                <p><span id="versus_yesterday">-</span></p>
                <p><span id="versus_weeksAgo">-</span> </p>
            </div>
        </div>

        <div class="chart_row">
            <h1>차트로 보는 세계코로나 상황</h1>

            <div class="chart_area">
                <select id="status_select">
                    <option value="confirmed">확진자</option>
                    <option value="death">사망자</option>
                </select>
                <select id="month_select">
                    <option value="all">전체</option>
                    <option value="3month">최근 3달</option>
                    <option value="month">최근 한달</option>
                </select>
                <canvas id=world_chart></canvas>
            </div>
        </div>
        <div class="nat_box_wrap">
            <div class="nat_box">
                <p class="nat_name_gubun">나라</p>
            </div>
            <div class="nat_box">
                <p class="nat_first">
                    <span class="nat_gubun" style="margin-left:5px">확진자</span>
                </p>
            </div>
            <div class="nat_box">
                <p class="nat_first">
                    <span class="nat_gubun" style="margin-left:5px">사망자</span>
                </p>
            </div>
        </div>

        <div class="nat_area">

        </div>

    </div>

</div>


</body>

</html>