package com.coronaService.api;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.coronaService.service.RegionalInfoService;
import com.coronaService.vo.CoronaSidoInfoVO;
import com.coronaService.vo.CoronaVaccineInfoVO;
import com.coronaService.vo.CoronaWeeksVO;
import com.coronaService.vo.VaccineWeeksVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RegionalAPIController {
    @Autowired
    RegionalInfoService service;
    @GetMapping("/api/regional")
    public Map<String,Object> getRegionalInfo(@RequestParam String region, @RequestParam @Nullable String date){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        
        if(date ==null || date.equals("")){
            // Date now = new Date();
            // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // date = formatter.format(now);    

            Calendar now = Calendar.getInstance();
            Calendar standard = Calendar.getInstance();
            standard.set(Calendar.HOUR_OF_DAY, 11);
            standard.set(Calendar.MINUTE, 00);
            standard.set(Calendar.SECOND, 00);
    
            if (now.getTimeInMillis() < standard.getTimeInMillis()) {
                // 현재 접속시간이 기준시간 (11시) 보다 이전일 때
                // 하루 이전 날짜로 변경
                now.add(Calendar.DATE, -1);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(now.getTime());        
        }
        CoronaSidoInfoVO vo = service.selectRegionalCoronaInfo(region, date);
        resultMap.put("data", vo);      
                
        String dangerAge  = service.selectDangerAge(date);
        resultMap.put("dangerAge", dangerAge);

        List<CoronaWeeksVO> coronaWeeksList = service.selectRegionalCoronaTwoWeeks(region, date);
        resultMap.put("coronaWeeksList", coronaWeeksList);


        char[] c = region.toCharArray();
        region = "%"+c[0]+"%"+c[1]+"%";

        List<VaccineWeeksVO> vaccineWeeksList = service.selectRegionalVaccineTwoWeeks(region,date);
        resultMap.put("vaccineWeeksList", vaccineWeeksList);

        resultMap.put("status",true);
        

    


        
        return resultMap;
    }
    @GetMapping("api/regional/vaccine")
    public Map<String,Object> getRegionVaccineInfo(@RequestParam String region, @RequestParam @Nullable String date){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
            // 캐릭터 Array로  ['제' , '주'] 형태로 바꿈
            char[] c = region.toCharArray();
            System.out.println(c[0]);
            System.out.println(c[1]);
    
            region = "%"+c[0]+"%"+c[1]+"%";

            if(date ==null || date.equals("")){
                // Date now = new Date();
                // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                // date = formatter.format(now);    
    
                Calendar now = Calendar.getInstance();
                Calendar standard = Calendar.getInstance();
                standard.set(Calendar.HOUR_OF_DAY, 15);
                standard.set(Calendar.MINUTE, 30);
                standard.set(Calendar.SECOND, 00);
        
                if (now.getTimeInMillis() < standard.getTimeInMillis()) {
                    // 현재 접속시간이 기준시간 (15:30) 보다 이전일 때
                    // 하루 이전 날짜로 변경
                    now.add(Calendar.DATE, -1);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.format(now.getTime());        
            }         

            CoronaVaccineInfoVO vo = service.selectCoronaVaccineInfo(region, date);

            if(vo == null){
                resultMap.put("status",false);
                resultMap.put("data", null);  
                resultMap.put("message", "데이터가 없습니다 . ("+region+","+date+")");
                return resultMap;
            }
            

            DecimalFormat formatter = new DecimalFormat("###,###");
            String formattedFirstCnt= formatter.format(vo.getAccFirstCnt());
            String formattedSecondCnt=formatter.format(vo.getAccSecondCnt());


            resultMap.put("status",true);
            resultMap.put("formattedFirstCnt", formattedFirstCnt);
            resultMap.put("formattedSecondCnt", formattedSecondCnt);
            resultMap.put("data", vo);        
            return resultMap;
        }
        @GetMapping("/api/vaccine/{date}")
        public Map<String,Object> getVaccineInfo(
            @PathVariable String date
            ){
            Map<String,Object> resultMap = new LinkedHashMap<String,Object>();

            List<VaccineWeeksVO> list =service.selectVaccineInfo(date);
            resultMap.put("vaccineList", list);



            return resultMap;
        }

}
