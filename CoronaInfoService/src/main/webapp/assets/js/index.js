// index.js
$(function(){
    let ctx= $("#regional_status");
    let regionalChart = new Chart(ctx,{
        type:'bar',
        options:{
            responsive:false
        },
        data:{
            labels:['서울','경기','대구','인천','부산','경남','경북','충남','강원','대전','충북','광주','울산','전북','전남','제주','세종'],
            datasets:[{
                label:"2021-08-09 신규확진",
                data:[321,86,32,231,33,45,112,223,23,22,54,47,87,66,2,45,125],
                backgroundColor: ["#D3D1FF", "#C7EDD5", "#FFF8DB", "#E9C9C9", "#c45850", "#BDC5FC"],
                borderColor: ["pink", "pink", "pink", "pink", "pink", "pink"]
                
            }]
        }
    })

    let ctx2 =$("#confirmed_chart");
    let confirmed_chart = new Chart(ctx2,{
        type:"pie",
        options:{
            responsive:false
        }, data:{
            labels:["확진","음성"],
            datasets:[{
                label:"확진/음성",
                data:[100,200],
                backgroundColor: ["#E9C9C9", "#FFF8DB"],
                borderColor: ["rgb(248, 223, 242)", "rgb(248, 223, 242)"]
            }]
        }        
    })
    let ctx3 =$("#vaccine_chart");
    let vaccine_chart = new Chart(ctx3,{
        type:"bar",
        options:{
            responsive:false
        }, data:{
            labels:['서울','경기','대구','인천','부산','경남','경북','충남','강원','대전','충북','광주','울산','전북','전남','제주','세종'],
            datasets:[{
                label:"2021-08-09 1차 접종현황",
                data:[321,86,32,231,33,45,112,223,23,22,54,47,87,66,2,45,125],
                backgroundColor: ["#E9C9C9"],
                borderColor: ["rgb(248, 223, 242)"]                
            },
            {
               
                    label:"2021-08-09 2차 접종현황",
                    data:[23,222,33,111,27,56,324,221,12,22,51,232,23,78,24,123,222],
                    backgroundColor: ["#FFF8DB"],
                    borderColor: ["rgb(248, 223, 242)"]
                    
                }]        
    
        
        }       
    })
  })