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
    <link rel="stylesheet" href="/assets/css/vaccine.css">

    <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"
        integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=554bca796911f82958fd7dd065bcee72&libraries=services"></script>
        <link rel="shortcut icon" type="image/x-icon" href="/assets/images/free-icon-face-mask-3922438.png">
    <script src="/assets/js/vaccine.js"></script>



    <title>CORONA INFO</title>
</head>

<body>
    <div class="container">
        <%@include file="/WEB-INF/views/includes/menu.jsp"%>
        <div class="right_area">
            <div class="data_row">
                <h1><input id="date">?????? ?????? ?????? ??????</h1>
                <div class="vaccine_first">
                    <p>1??? ??????</p>
                    <p><span id="accFirstCntAll">-</span> <span id="incFirstCntAll">-</span> </p>
                </div>
                <div class="vaccine_second">
                    <p>2??? ??????</p>
                    <p><span id="accSecondCntAll">-</span> <span id="incSecondCntAll">-</span> </p>
                </div>
            </div>
            <div class="sido_area">

            </div>

            <div class="region_wrap">
                <select id="region_select">
                    <option value="??????">???????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">?????????????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">???????????????</option>
                    <option value="??????">?????????????????????</option>
                </select>               
                    <span><img src="/assets/images/783204.png"> ?????? ????????????</span>
                    <span><img src="/assets/images/rating.png"> ?????? ????????????</span>           
            </div>


            <div class="data_row1">

                <div id="vaccine_map" style="width:100%;height:500px;">
                </div>

            </div>
        </div>
    </div>
</body>

</html>
!