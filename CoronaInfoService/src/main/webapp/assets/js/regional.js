// regional.js
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
    $("#regional").addClass("currently");

    var coronaWeeksChart = new Chart($("#accDecideChart"), {
        type: "line",
        options: {
            responsive: false,
            showValue: {
                fontSize: 12
            }
        },
        data: {
            labels: null,
            datasets: [{
                label: '코로나 누적 확진',
                data: null,
                backgroundColor: ['red']
            }]
        }
    })

    var vaccineWeekChart = new Chart($("#accVaccineChart"), {
        type: "line",
        options: {
            responsive: false
        },
        data: {
            labels: null,
            datasets: [{
                    label: '1차 접종 현황',
                    data: null,
                    backgroundColor: [
                        'blue'
                    ],
                },
                {
                    label: '2 차 접종 현황',
                    data: null,
                    backgroundColor: [
                        'red'
                    ],
                }
            ]
        }
    })

    $("#date").datepicker(); // js 라이브러리 사용
    $("#date").datepicker("setDate", new Date());
    let date = $("#date").val();
    $("#date").change(function () {
        // console.log($(this).val());
        let region = $("#region_select").find("option:selected").val();
        let date = $("#date").val();
        getCoronaSidoInfo(region, date);
        getCoronaVaccineInfo(region, date);
    })

    $("#region_select").change(function () {
        let region = $("#region_select").find("option:selected").val();
        let date = $("#date").val();
        getCoronaSidoInfo(region, date);
        getCoronaVaccineInfo(region, date);
    });

    getCoronaSidoInfo("서울",date)
    getCoronaVaccineInfo("서울",date)


    function getCoronaSidoInfo(sido, date) {
        let url = "http://localhost:8077/api/regional?region=" + sido;
        if (date != undefined && date != null && date != '') {
            url += "&date=" + date
        }
        $.ajax({
            type: "get",
            url: url,
            success: function (r) {
                console.log(r)


                if (r.coronaWeeksList != null) {
                    let coronaWeeksLabel = new Array();
                    let coronaWeeksData = new Array();
                    for (let i = 0; i < r.coronaWeeksList.length; i++) {
                        coronaWeeksLabel.push(r.coronaWeeksList[i].date);
                        coronaWeeksData.push(r.coronaWeeksList[i].defCnt)
                    }
                    coronaWeeksChart.data.datasets = new Array();
                    coronaWeeksChart.data.labels = coronaWeeksLabel;
                    coronaWeeksChart.data.datasets.push({
                        label: '코로나 누적 확진',
                        data: coronaWeeksData,
                        backgroundColor: ['red'],
                    })
                    coronaWeeksChart.update();
                    // var coronaWeeksChart = new Chart($("#accDecideChart"), {
                    //     type:"line",
                    //     options:{
                    //         responsive:false
                    //     },
                    //     data:{
                    //         labels:coronaWeeksLabel,
                    //         datasets:[{
                    //             label:'코로나 누적 확진',
                    //             data:coronaWeeksData,
                    //             backgroundColor:[
                    //                 '#D3D1FF'
                    //             ]
                    //         }]
                    //     }
                    // })

                    let vaccineWeeksLabel = new Array();
                    let vaccineWeeksAccFirst = new Array();
                    let vaccineWeeksAccSecond = new Array();
                    for (let i = 0; i < r.vaccineWeeksList.length; i++) {
                        vaccineWeeksLabel.push(r.vaccineWeeksList[i].date);
                        vaccineWeeksAccFirst.push(r.vaccineWeeksList[i].accFirstCnt)
                        vaccineWeeksAccSecond.push(r.vaccineWeeksList[i].accSecondCnt)                        
                    }
                    vaccineWeekChart.data.datasets = new Array();
                    vaccineWeekChart.data.labels = vaccineWeeksLabel;
                    vaccineWeekChart.data.datasets.push({
                        label: '1차 접종 현황',
                        data: vaccineWeeksAccFirst,
                        backgroundColor: ['blue'],
                    },
                    {
                        label: '2 차 접종 현황',
                        data: vaccineWeeksAccSecond,
                        backgroundColor: [
                            'red'
                        ],
                    }

                    )
                    vaccineWeekChart.update();

                    // var vaccineWeekChart = new Chart($("#accVaccineChart"), {
                    //     type: "line",
                    //     options: {
                    //         responsive: false
                    //     },
                    //     data: {
                    //         labels: vaccineWeeksLabel,
                    //         datasets: [{
                    //                 label: '1차 접종 현황',
                    //                 data: vaccineWeeksAccFirst,
                    //                 backgroundColor: [
                    //                     'pink'
                    //                 ],
                    //                 borderColor: ['yellow']
                    //             },
                    //             {
                    //                 label: '2 차 접종 현황',
                    //                 data: vaccineWeeksAccSecond,
                    //                 backgroundColor: [
                    //                     'red'
                    //                 ],
                    //                 borderColor: ['yellow']
                    //             }
                    //         ]
                    //     }

                    // })

                }






                if (r.dangerAge == null) {
                    $("#dangerAge").html("-");
                } else {


                    $("#dangerAge").html(r.dangerAge + "대");
                }
                if (r.data == null) {
                    $("#accDecideCnt").html("-");
                    $("#newDecideCnt").html("-");
                    $("#isolateCnt").html("-");
                    $("#clearIsolateCnt").html("-");
                    $("#covidDanger span").css("display", "none");
                    $("#covidDanger span").eq(0).css("display", "inline").css("color", "#66ff99");
                    return;
                }
                $("#accDecideCnt").html(comma(r.data.defCnt));
                $("#newDecideCnt").html(comma(r.data.incDec));
                $("#isolateCnt").html(comma(r.data.isolIngCnt));
                $("#clearIsolateCnt").html(comma(r.data.isolClearCnt));
                $("#covidDanger span").css("display", "none");
                let danger = r.data.incDec + r.data.diff;
                console.log(r.data.incDec+","+r.data.diff)
                if (danger >= 200) {
                    $("#covidDanger span").eq(3).css("display", "inline").css("color", "#ff0000");
                } else if (danger >= 100) {
                    $("#covidDanger span").eq(2).css("display", "inline").css("color", "#ffcc66");
                } else if (danger >= 10) {
                    $("#covidDanger span").eq(1).css("display", "inline").css("color", "#ffcc66");
                } else {
                    $("#covidDanger span").eq(0).css("display", "inline").css("color", "#66ff99");
                }
            }
        })
    }

    function getCoronaVaccineInfo(region, date) {
        let url = "http://localhost:8077/api/regional/vaccine?region=" + region;
        if (date != undefined && date != null && date != '') {
            url += "&date=" + date
        }
        $.ajax({
            type: "get",
            url: url,
            success: function (r) {
                console.log(r)
                if (r.status == false) {
                    $("#vaccineFirstCnt").html("-");
                    $("#vaccineSecondCnt").html("-");
                }


                $("#vaccineFirstCnt").html(r.formattedFirstCnt);
                $("#vaccineSecondCnt").html(r.formattedSecondCnt);
            }
        })
    }


    function comma(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
})