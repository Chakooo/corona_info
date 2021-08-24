package com.greenart.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.greenart.mapper.WorldInfoMapper;
import com.greenart.vo.CoronaWorldInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorldInfoService {
    @Autowired WorldInfoMapper mapper;

    public void insertCoronaWorldInfo(CoronaWorldInfoVO vo){
        mapper.insertCoronaWorldInfo(vo);
    }
    

    public CoronaWorldInfoVO selectWorldCoronaSum(String date){
        Calendar now = Calendar.getInstance();
        Calendar standard =Calendar.getInstance();


        standard.set(Calendar.HOUR_OF_DAY,13);
        standard.set(Calendar.MINUTE,3);
        standard.set(Calendar.SECOND,0);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            now.add(Calendar.DATE,-1);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dt = formatter.format(now.getTime());
        System.out.println("날짜확인:"+dt);

        CoronaWorldInfoVO vo = mapper.selectWorldCoronaSum(dt); // 세계 코로나 확진자수 합
       
        CoronaWorldInfoVO yesterday_vo = mapper.selectWorldCoronaSumYesterDay(dt); // 오늘 확진자수 증가량       
        Integer getYesterday = mapper.selectDiffYesterday(dt); //어제 확진자수 증가량
        Integer getWeeks = mapper.selectDiffWeeks(dt);  // 일주일전 확진자수 증가량
        System.out.println(vo); 
        System.out.println("어제"+yesterday_vo); 


        

        if(vo.getNatDefCnt()==null){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());            
            vo = mapper.selectWorldCoronaSum(dt2);
            
        }
        if(yesterday_vo==null){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());       
            yesterday_vo = mapper.selectWorldCoronaSumYesterDay(dt2); // 오늘 확진자수 증가량       
            getYesterday = mapper.selectDiffYesterday(dt2); //어제 확진자수 증가량
           
        }
        if(getWeeks ==null){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());    

            getWeeks = mapper.selectDiffWeeks(dt2);
        }

        System.out.println(vo);
        System.out.println(yesterday_vo);
    
      

        Integer defGap = vo.getNatDefCnt() - yesterday_vo.getNatDefCnt();
        Integer deathGap =  vo.getNatDeathCnt()  - yesterday_vo.getNatDeathCnt();

        Integer yDiff = defGap - getYesterday;
        Integer wDiff = defGap - getWeeks;


        DecimalFormat dFormat = new DecimalFormat("###,###,###");

        
        String natDeathCnt= dFormat.format(vo.getNatDeathCnt());
        String natDefCnt =dFormat.format(vo.getNatDefCnt());

        String worldDefGap = dFormat.format(defGap);
        String worldDeathGap = dFormat.format(deathGap);

        String yesterdayDiff= dFormat.format(yDiff);
        String weeksDiff= dFormat.format(wDiff);


        vo.setWorldDefGap(worldDefGap);
        vo.setWorldDeathGap(worldDeathGap);
        vo.setWorldDeathSum(natDeathCnt);
        vo.setWorldDefSum(natDefCnt);
        vo.setYesterdayDiff(yesterdayDiff);
        vo.setWeeksDiff(weeksDiff);
        
        
        return  vo;
    }

    public Map<String,Object> selectChartInfo(String date, String term){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();

        Calendar now = Calendar.getInstance();
        Calendar standard =Calendar.getInstance();


        standard.set(Calendar.HOUR_OF_DAY,13);
        standard.set(Calendar.MINUTE,3);
        standard.set(Calendar.SECOND,0);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            now.add(Calendar.DATE,-1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dt = formatter.format(now.getTime());

        List<CoronaWorldInfoVO> list = mapper.selectChartInfo(dt, term);

        if(list == null){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());
            list = mapper.selectChartInfo(dt2, term);
        }


        List<String> chartDate = new ArrayList<>();
        List<Integer> chartDef = new ArrayList<>();
        List<Integer> chartDeath = new ArrayList<>();

        for (CoronaWorldInfoVO vo : list) {
            chartDate.add(vo.getChartDate());
            chartDef.add(vo.getNatDefCnt());
            chartDeath.add(vo.getNatDeathCnt());            
        }


        resultMap.put("chartDate", chartDate);
        resultMap.put("chartDef", chartDef);
        resultMap.put("chartDeath", chartDeath);
        return resultMap;
    }

    public List<CoronaWorldInfoVO> selectNationalTodayInfo(String date){

        Calendar now = Calendar.getInstance();
        Calendar standard =Calendar.getInstance();


        standard.set(Calendar.HOUR_OF_DAY,13);
        standard.set(Calendar.MINUTE,3);
        standard.set(Calendar.SECOND,0);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            now.add(Calendar.DATE,-1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dt = formatter.format(now.getTime());

        List<CoronaWorldInfoVO> vo = mapper.selectNationalTodayInfo(dt); // 국가별 오늘 확진자수
        List<CoronaWorldInfoVO> yesterday_vo = mapper.selectNationalYesterdayInfo(dt); // 국가별 어제 확진자수

        if(vo.size()==0){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());
            vo = mapper.selectNationalTodayInfo(dt2);            
        }
        if(yesterday_vo.size()==0){
            now.add(Calendar.DATE,-2);
            String dt2 = formatter.format(now.getTime());
            yesterday_vo = mapper.selectNationalYesterdayInfo(dt2);
        }

        
        for(int i = 0;i<vo.size() ; i++){
        Integer yesterDayDefDiff = vo.get(i).getNatDefCnt() - yesterday_vo.get(i).getNatDefCnt();
        Integer yesterDayDeathDiff = vo.get(i).getNatDeathCnt() - yesterday_vo.get(i).getNatDeathCnt();

        vo.get(i).setYesterDayDefDiff(yesterDayDefDiff);
        vo.get(i).setYesterDayDeathDiff(yesterDayDeathDiff);

        }

        return vo;
    }













}
