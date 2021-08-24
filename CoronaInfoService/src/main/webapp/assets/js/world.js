// world.js
$(function () {

    $("#worldInfo").addClass("currently");

    let ctx = $("#world_chart")

    var world_chart = new Chart(ctx, {
        type: "line",
        option: {
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





    getWorldInfo('confirmed', 'all')

    $("#status_select").change(function () {
        let status = $("#status_select").find("option:selected").val();
        let term = $("#month_select").find("option:selected").val();
        getWorldInfo(status, term)
    })
    $("#month_select").change(function () {
        let status = $("#status_select").find("option:selected").val();
        let term = $("#month_select").find("option:selected").val();
        getWorldInfo(status, term)
    })




    $.ajax({
        type: "get",
        url: "/api/corona/world/" + getToday(),
        success: function (r) {
            console.log(r)
            $("#natDefCnt").html(r.data.worldDefSum);
            $("#defIncrease").html(r.data.worldDefGap + "↑");
            $("#natDeathCnt").html(r.data.worldDeathSum);
            $("#deathIncrease").html(r.data.worldDeathGap + "↑");
            $("#todayDef").html(r.data.worldDefGap + "명");



            if (r.data.yesterdayDiff.charAt(0) == '-') {
                fixDiff = r.data.yesterdayDiff.substr(1);
                $("#versus_yesterday").html("vs 어제<span id='yesterday_down'>" + fixDiff + "↓</span>");
                $("#yesterday_down").css({
                    "font-size": "16px",
                    "color": "blue",
                    "background-color": " rgb(188, 173, 255)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left": "30px",
                    "display": "inline-block",
                    "width": "100px"
                })
            } else {
                $("#versus_yesterday").html("vs 어제<span id='yesterday_up'> " + r.data.yesterdayDiff + "↑</span>");
                $("#yesterday_up").css({
                    "font-size": "16px",
                    "color": "#f22",
                    "background-color": " rgb(248, 207, 207)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left": "30px",
                    "display": "inline-block",
                    "width": "100px"
                })
            }

            if (r.data.weeksDiff.charAt(0) == '-') {
                fixDiff = r.data.weeksDiff.substr(1);
                $("#versus_weeksAgo").html("vs 1주전<span id='weeks_down'>" + fixDiff + "↓</span>");
                $("#weeks_down").css({
                    "font-size": "16px",
                    "color": "blue",
                    "background-color": " rgb(188, 173, 255)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left": "20px",
                    "display": "inline-block",
                    "width": "100px"
                })
            } else {
                $("#versus_weeksAgo").html("vs 1주전<span id='weeks_up'> " + r.data.weeksDiff + "↑</span>");
                $("#weeks_up").css({
                    "width": "200px",
                    "font-size": "16px",
                    "color": "#f22",
                    "background-color": " rgb(248, 207, 207)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left": "20px",
                    "display": "inline-block",
                    "width": "100px"

                })
            }
        }
    })

    function getWorldInfo(status, term) {

        $.ajax({
            type: "get",
            url: "/api/corona/chart/" + getToday() + "/" + term,
            success: function (r) {
                console.log(r.chartDate);
                console.log(r.chartDef);
                console.log(r.chartDeath);
                console.log(r.chartDate.length);


                world_chart.data.datasets = new Array();
                if (status == 'confirmed') {
                    world_chart.data.labels = r.chartDate;
                    world_chart.data.datasets.push({
                        label: '코로나 누적 확진',
                        data: r.chartDef,
                        backgroundColor: ['red']
                    })
                    world_chart.update();
                }
                if (status == 'death') {
                    world_chart.data.labels = r.chartDate;
                    world_chart.data.datasets.push({
                        label: '코로나 누적 확진',
                        data: r.chartDeath,
                        backgroundColor: ['red'],
                    })
                    world_chart.update();

                }
            }
        })


        $.ajax({
            type: "get",
            url: "/api/corona/national/"+getToday(),
            success: function (r) {
                console.log(r)

                for (let i = 0; i<r.data.length; i++) {

                    
                    if(r.data[i].yesterDayDeathDiff == '0') {
                        r.data[i].yesterDayDeathDiff = ''
                    }
                    else{
                        r.data[i].yesterDayDeathDiff = comma(r.data[i].yesterDayDeathDiff)+'↑'
                    }
                    if(r.data[i].yesterDayDefDiff == '0') {
                        r.data[i].yesterDayDefDiff = ''
                    }
                    else{
                        r.data[i].yesterDayDefDiff = comma(r.data[i].yesterDayDefDiff)+'↑'
                    }
                    let tag = 
                        '<div class="nat_box">'+
                        '<p class="sido_name"><img src="/assets/images/'+i+'.gif">'+r.data[i].nationNm+'</p>' +
                        '</div>' +
                        '<div class="nat_box">' +
                        '<p class="nat_first">' +
                        '<p class="sidoAccCnt" style="margin-left:5px;">'+comma(r.data[i].natDefCnt)+'</p>' +
                        '<span class="sidoIncCnt" style="margin-left:5px">'+comma(r.data[i].yesterDayDefDiff)+'</span>' +
                        '</p>' +
                        '</div>' +
                        '<div class="nat_box">' +
                        '<p class="nat_first"> ' +
                        '<p class="sidoAccCnt" style="margin-left:5px">'+comma(r.data[i].natDeathCnt)+'</p>' +
                        '<span class="sidoIncCnt" style="margin-left:5px">'+comma(r.data[i].yesterDayDeathDiff)+'</span>' +
                        '</p>' +
                        '</div>';                        
                        $(".nat_area").append(tag);
                    }
                }
            }
        )
    }



    function comma(x) {
        return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    function getToday() {
        let date = new Date();
        let strDate =
            date.getFullYear() + "-" + leadingZero(date.getMonth() + 1) + "-" + leadingZero(date.getDate());
            console.log(strDate)
        return strDate;
    }
    // 날짜가져올때 0 추가표시
    function leadingZero(n) {
        return n < 10 ? "0" + n : "" + n;
    }

})