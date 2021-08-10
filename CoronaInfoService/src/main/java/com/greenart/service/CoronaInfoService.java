package com.greenart.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.greenart.mapper.CoronaInfoMapper;
import com.greenart.vo.CoronaInfoVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaInfoService {
    @Autowired
    CoronaInfoMapper mapper;
    public void insertCoronaInfo(CoronaInfoVO vo){
        mapper.insertCoronaInfo(vo);
    }
    public CoronaInfoVO selectTodayCoronaInfo(){
        //10시 30분에 스케쥴링을 돌려놓아서 10 : 29은 이전날짜의 데이터를 가져온다.
        Calendar start = Calendar.getInstance();
        Calendar end =Calendar.getInstance();

        System.out.println(start);
        System.out.println(end);
        // start.set(Calendar.HOUR_OF_DAY,10);
        // start.set(Calendar.MINUTE,30);
        // start.set(Calendar.SECOND,0);
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(now);
        System.out.println(date);


        CoronaInfoVO data = mapper.selectCoronaInfoByDate(date);
        Integer accEXamCnt = data.getAccExamCnt();
        Integer decideCnt = data.getDecideCnt();

        DecimalFormat dFormatter = new DecimalFormat("###,###");
        String strAccExamCnt = dFormatter.format(accEXamCnt);
        String strDecideCnt = dFormatter.format(decideCnt);

        data.setStrAccExamCnt(strAccExamCnt);
        data.setStrDecideCnt(strDecideCnt);

        System.out.println(data);

        return data;

    }
    
}
