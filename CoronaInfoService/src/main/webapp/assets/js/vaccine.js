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

    $("#date").datepicker(); // js 라이브러리 사용
    $("#date").datepicker("setDate", new Date());
    $("#date").datepicker("option", "maxDate" , new Date());
    $("#date").datepicker("option", "minDate" , '2021-03-11');
    // 날짜를 설정해서 범위내에서만 선택할수 있도록 설정
    
    

    getVaccineInfo($("#date").val());

    $("#date").change(function(){
        getVaccineInfo($("#date").val());
    })

    function getVaccineInfo(date) {
        $.ajax({
            type: "get",
            url: "/api/vaccine/"+date,
            success: function (r) {
                if(r.vaccineList.length < 18){
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