// world.js
$(function(){

    $.ajax({
        type:"get",
        url:"/api/corona/world/"+getToday(),
        success:function(r){
            console.log(r)
            $("#natDefCnt").html(r.data.worldDefSum);
            $("#defIncrease").html(r.data.worldDefGap+"↑");
            $("#natDeathCnt").html(r.data.worldDeathSum);
            $("#deathIncrease").html(r.data.worldDeathGap+"↑");
            $("#todayDef").html(r.data.worldDefGap+"명");       

          
            console.log(r.data.yesterdayDiff.charAt(0));
                        
            if(r.data.yesterdayDiff.charAt(0)== '-'){
                fixDiff= r.data.yesterdayDiff.substr(1);
                $("#versus_yesterday").html("vs 어제<span id='yesterday_down'>" + fixDiff+"↓</span>");     
                $("#yesterday_down").css({
                    "font-size":"16px",
                    "color":"blue",
                    "background-color":" rgb(188, 173, 255)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left":"30px",
                    "display": "inline-block",
                    "width": "100px"
                })
            }else{
                $("#versus_yesterday").html("vs 어제<span id='yesterday_up'> " + r.data.yesterdayDiff+"↑</span>");     
                $("#yesterday_up").css({
                "font-size":"16px",
                "color":"#f22",
                "background-color":" rgb(248, 207, 207)",
                "padding": "5px",
                "border-radius": "20px",
                "margin-left":"30px",
                "display": "inline-block",
                "width": "100px"
                    })
            }

            if(r.data.weeksDiff.charAt(0)== '-'){
                fixDiff= r.data.weeksDiff.substr(1);
                $("#versus_weeksAgo").html("vs 1주전<span id='weeks_down'>" + fixDiff+"↓</span>");     
                $("#weeks_down").css({
                    "font-size":"16px",
                    "color":"blue",
                    "background-color":" rgb(188, 173, 255)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left":"20px",
                    "display": "inline-block",
                    "width": "100px"
                })
            }else{
                $("#versus_weeksAgo").html("vs 1주전<span id='weeks_up'> " + r.data.weeksDiff+"↑</span>");       
                $("#weeks_up").css({
                    "width":"200px",
                    "font-size":"16px",
                    "color":"#f22",
                    "background-color":" rgb(248, 207, 207)",
                    "padding": "5px",
                    "border-radius": "20px",
                    "margin-left":"20px",
                    "display": "inline-block",
                    "width": "100px"

                })
            }
        }
    })
    $.ajax({
        type:"get",
        url:"/api/corona/chart/month"+getToday(),
        success:function(r){
            console.log(r)
        }
        
    })










    function getToday() {
        let date = new Date();
        let strDate =
            date.getFullYear() + "-" + leadingZero(date.getMonth() + 1) + "-" + leadingZero(date.getDate());
        return strDate;
    }
    // 날짜가져올때 0 추가표시
    function leadingZero(n) {
        return n < 10 ? "0" + n : "" + n;
    }
})  