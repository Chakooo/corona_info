package com.greenart.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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
    

    public Integer selectWorldCoronaSum(String date){
        Calendar now = Calendar.getInstance();
        Calendar standard =Calendar.getInstance();


        standard.set(Calendar.HOUR_OF_DAY,3 );
        standard.set(Calendar.MINUTE,0);
        standard.set(Calendar.SECOND,0);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            now.add(Calendar.DATE,-1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dt = formatter.format(now.getTime());

        Integer cnt = mapper.selectWorldCoronaSum(dt);

        DecimalFormat dFormat = new DecimalFormat("###,###,###");
        String formatCnt = dFormat.format(cnt);

        Integer worldCoronaCnt = Integer.parseInt(formatCnt);
        
        return  worldCoronaCnt;
    }
}
