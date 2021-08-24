// vaccine.js

$.datepicker.setDefaults({
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년'
});

$(function () {





    // 카카오로부터 받아온 각각의 병원정보를 DB에 저장
    // 카카오맵 API 호출
    var mapContainer = document.getElementById('vaccine_map'), // 지도를 표시할 div 
        mapOption = {
            center: new kakao.maps.LatLng(37.56667, 126.97806), // 지도의 중심좌표
            level: 6 // 지도의 확대 레벨
        };

    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);
    var mapTypeControl = new kakao.maps.MapTypeControl();

    // 지도에 컨트롤을 추가해야 지도위에 표시됩니다
    // kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
    map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

    // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
    var zoomControl = new kakao.maps.ZoomControl();
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);









    // function getMarker(address, info) {
    //     var geocoder = new kakao.maps.services.Geocoder();
    //     // 주소로 좌표를 검색합니다
    //     geocoder.addressSearch(address, function (result, status) {

    //         // 정상적으로 검색이 완료됐으면 
    //         if (status === kakao.maps.services.Status.OK) {

    //             var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

    //             // 결과값으로 받은 위치를 마커로 표시합니다
    //             var marker = new kakao.maps.Marker({
    //                 map: map,
    //                 position: coords
    //             });
    //             var iwContent = '<div style="width:300px">' + info + '</div>';
    //             var infowindow = new kakao.maps.InfoWindow({
    //                 content: iwContent
    //             });
    //             kakao.maps.event.addListener(marker, 'mouseover', function () {
    //                 // 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
    //                 infowindow.open(map, marker);
    //             });

    //             // 마커에 마우스아웃 이벤트를 등록합니다
    //             kakao.maps.event.addListener(marker, 'mouseout', function () {
    //                 // 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
    //                 infowindow.close();
    //             });
    //         }




    //     });
    // }

    $.ajax({
        type: 'get',
        url: '/api/corona/medicalInfo',
        success: function (r) {
            console.log(r)

            var positions = [];

            for (let i = 0; i < r.medical_data.length; i++) {
                // getMarker(r.medical_data[i].orgZipaddr,r.medical_data[i].orgTlno)
                let data = {
                    title: r.medical_data[i].orgnm + " / " + r.medical_data[i].orgTlno,
                    latlng: new kakao.maps.LatLng(r.medical_data[i].location_b, r.medical_data[i].location_a)
                }
                positions.push(data)
            }

            console.log(positions)
            var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

            for (var i = 0; i < positions.length; i++) {

                // 마커 이미지의 이미지 크기 입니다
                var imageSize = new kakao.maps.Size(24, 35);

                // 마커 이미지를 생성합니다    
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                    map: map, // 마커를 표시할 지도
                    position: positions[i].latlng, // 마커를 표시할 위치
                    title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                    image: markerImage // 마커 이미지 
                });
            }
        }
    })

        $.ajax({
        type: 'get',
        url: '/api/corona/vaccineCenterInfo',
        success: function (r) {
            console.log(r)

            var positions = [];

            for (let i = 0; i < r.vaccineCenter_info.length; i++) {
                let data = {
                    title: r.vaccineCenter_info[i].facilityName + " / " + r.vaccineCenter_info[i].phoneNumber,
                    latlng: new kakao.maps.LatLng(r.vaccineCenter_info[i].location_a, r.vaccineCenter_info[i].location_b)
                }
                positions.push(data)
            }

            console.log(positions)
            var imageSrc = '/assets/images/783204.png'

            for (var i = 0; i < positions.length; i++) {

                // 마커 이미지의 이미지 크기 입니다
                var imageSize = new kakao.maps.Size(40, 40);

                // 마커 이미지를 생성합니다    
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                    map: map, // 마커를 표시할 지도
                    position: positions[i].latlng, // 마커를 표시할 위치
                    title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                    image: markerImage // 마커 이미지 
                });
            }
        }
    })
    













    function panTo(xCoordinate, yCoordinate) {
        // 이동할 위도 경도 위치를 생성합니다 
        var moveLatLon = new kakao.maps.LatLng(xCoordinate, yCoordinate);

        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
    }
    $("#region_select").change(function () {
        let region = $("#region_select").find("option:selected").val();
        console.log(region)
        if (region == '서울') {
            panTo(37.56667, 126.97806);
        }
        if (region == '인천') {
            panTo(37.45639, 126.70528);
        }
        if (region == '세종') {
            panTo(36.48750, 127.28167);
        }
        if (region == '대전') {
            panTo(36.35111, 127.38500);
        }
        if (region == '대구') {
            panTo(35.87222, 128.60250);
        }
        if (region == '울산') {
            panTo(35.53889, 129.31667);
        }
        if (region == '광주') {
            panTo(37.41750, 127.25639);
        }
        if (region == '부산') {
            panTo(35.17944, 129.07556);
        }
        if (region == '제주') {
            panTo(33.50000, 126.51667);
        }
    })

















    $("#vaccine").addClass("currently");

    $("#date").datepicker(); // js 라이브러리 사용
    $("#date").datepicker("setDate", new Date());
    $("#date").datepicker("option", "maxDate", new Date());
    $("#date").datepicker("option", "minDate", '2021-03-11');
    // 날짜를 설정해서 범위내에서만 선택할수 있도록 설정



    getVaccineInfo($("#date").val());

    $("#date").change(function () {
        getVaccineInfo($("#date").val());
    })

    function getVaccineInfo(date) {
        $.ajax({
            type: "get",
            url: "/api/vaccine/" + date,
            success: function (r) {
                if (r.vaccineList.length < 18) {
                    $("#accFirstCntAll").html("-");
                    $("#accSecondCntAll").html("-");
                    $("#incFirstCntAll").html("-");
                    $("#incSecondCntAll").html("-");
                    $(".sido_area").html("<h1 style='font-size:48px; padding:20px; color:#999; text-align:center;'>데이터가 없습니다.</h1>");
                    return;
                }
                // console.log(r)
                // 전체 DATA 보여주기
                r.vaccineList[0]
                $("#accFirstCntAll").html(comma(r.vaccineList[0].accFirstCnt))
                $("#accSecondCntAll").html(comma(r.vaccineList[0].accSecondCnt))
                $("#incFirstCntAll").html(comma(r.vaccineList[0].firstCnt + "▲"))
                $("#incSecondCntAll").html(comma(r.vaccineList[0].secondCnt + "▲"))
                $(".sido_area").html("");

                for (let i = 1; i < r.vaccineList.length; i++) {
                    let tag =
                        '<div class="sido_box">' +
                        '<p class="sido_name">' + r.vaccineList[i].sido + '</p>' +
                        '<p class="sidoFirst">' +
                        '<span>1차</span>' +
                        '<span class="sidoAccCnt" style="margin-left:5px">' + comma(r.vaccineList[i].accFirstCnt) + '</span>' +
                        '<span class="sidoIncCnt" style="margin-left:5px">' + comma(r.vaccineList[i].firstCnt) + '▲</span>' +
                        '</p>' +
                        '<p class="sidoSecond">' +
                        '<span>2차</span>' +
                        '<span class="sidoAccCnt" style="margin-left:5px">' + comma(r.vaccineList[i].accSecondCnt) + '</span>' +
                        '<span class="sidoIncCnt" style="margin-left:5px">' + comma(r.vaccineList[i].secondCnt) + '▲</span>' +
                        '</p>' +
                        '</div>';

                    $(".sido_area").append(tag);



                }

            }

        })
    }

})

// 숫자 3번쨰 콤마 정규표현식
function comma(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}