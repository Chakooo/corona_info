$(function () {

    $.ajax({
        type: "get",
        url: "/api/coronaInfo/today",
        success: function (r) {
            console.log(r)
            $("#accExamCnt").html(r.data.strAccExamCnt)
            $("#decideCnt").html(r.data.strDecideCnt)
            let ctx2 = $("#confirmed_chart")
            let confirmed_chart = new Chart(ctx2, {
                type: 'pie',
                options: {
                    responsive: false
                },
                data: {
                    labels: ["양성", "음성"],
                    datasets: [{
                        label: "양성/음성",
                        data: [r.data.decideCnt, r.data.examCnt-r.data.decideCnt],
                        backgroundColor: ["#D3D1FF","#C7EDD5","##FFF8DB"]
                    }]
                },
            })
        }
    })
    $.ajax({
        type:"get",
        url:"/api/coronaSido/today",
        success: function(r){
            console.log(r)
            let sidoName = new Array()
            let defCnt = new Array()

            for(let i = 0; i<6; i++){
                let tag = "<tbody class='region-tbody'></tbody>"
                $(".region_confirm_tbl").append(tag)
            }
            for(let i = 0; i<r.data.length; i++){
                let sido = r.data[i].gubun
                let cnt = r.data[i].incDec
                sidoName.push(sido)
                defCnt.push(cnt)

                //012 /3 =0.xxxx
                //345 /3 = 1.xxx
                //678 /3 = 2.xxx
                console.log(Math.floor(i/3));
                let page =Math.floor(i/3);
                let tag = 
                '<tr>' +
                    '<td>'+r.data[i].gubun+'</td>'+
                    '<td>'+r.data[i].defCnt+'</td>'+
                    '<td>'+r.data[i].incDec+' ▲</td>'+
                '</tr>'
                $(".region-tbody").eq(page).append(tag);
            }
            $(".region-tbody").eq(0).addClass("active");

            $("#region_next").click(function(){
                let currentPage = Number($(".current").html());
                currentPage++;
                if(currentPage > 6) currentPage = 6;
                $(".current").html(currentPage);
                $(".region-tbody").removeClass("active");
                $(".region-tbody").eq(currentPage-1).addClass("active");
                console.log(currentPage)
            })
            $("#region_prev").click(function(){
                let currentPage = Number($(".current").html());
                currentPage--;
                if(currentPage < 1) currentPage = 1;
                $(".current").html(currentPage);
                $(".region-tbody").removeClass("active");
                $(".region-tbody").eq(currentPage-1).addClass("active");
                console.log(currentPage)
            })

            let ctx = $("#regional_status")
            let regionalChart = new Chart(ctx, {
                type: 'bar',
                options: {
                    responsive: false
                },
                data: {
                    labels: sidoName,
                    datasets: [{
                        label: "2021-08-09 신규확진",
                        data: defCnt,
                        backgroundColor: ["#D3D1FF","#C7EDD5","#E9C9C9"]
                    }]
                }
            })
        }
    })

    let ctx3 = $("#vaccine_chart")
    let vaccineChart = new Chart(ctx3, {
        type: 'bar',
        options: {
            responsive: false
        },
        data: {
            labels: ["전국", "서울특별시", "부산광역시", "대구광역시", "광주광역시", "대전광역시", "인천광역시", "울산광역시",
                "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도",
                "제주특별자치도"
            ],
            datasets: [{
                    label: "2021-08-09 1차 접종현황",
                    data: [415, 408, 86, 65, 123, 88, 30, 68, 24, 42, 39, 19, 25, 21, 14, 11, 1],
                    backgroundColor: ["#D3D1FF","#C7EDD5","#E9C9C9"]
                },
                {
                    label: "2021-08-09 2차 접종현황",
                    data: [415, 408, 86, 65, 123, 88, 30, 68, 24, 42, 39, 19, 25, 21, 14, 11, 1],
                    backgroundColor: ["#D3D1FF","#C7EDD5","#E9C9C9"]
                }
            ]
        }
    })
})