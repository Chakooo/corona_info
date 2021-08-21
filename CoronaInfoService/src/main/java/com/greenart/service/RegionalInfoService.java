package com.greenart.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.greenart.mapper.RegionalInfoMapper;
import com.greenart.vo.CoronaInfoVO;
import com.greenart.vo.CoronaSidoInfoVO;
import com.greenart.vo.CoronaVaccineInfoVO;
import com.greenart.vo.CoronaWeeksVO;
import com.greenart.vo.VaccineWeeksVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionalInfoService {
    @Autowired
    RegionalInfoMapper mapper;
    public CoronaSidoInfoVO selectRegionalCoronaInfo(String region, String date){
        return mapper.selectRegionalCoronaInfo(region, date);
    }
    public CoronaInfoVO selectCoronaInfoRegionTotal(String date){
        return mapper.selectCoronaInfoRegionTotal(date);
    }
    public CoronaVaccineInfoVO selectCoronaVaccineInfo (String region , String date){
        return mapper.selectCoronaVaccineInfo(region, date);
    }
    public String selectDangerAge(String date){
        return mapper.selectDangerAge(date);
    }
    public List<CoronaWeeksVO> selectRegionalCoronaTwoWeeks(String region, String date){
        return mapper.selectRegionalCoronaTwoWeeks(region, date);
    }
    public List<VaccineWeeksVO> selectRegionalVaccineTwoWeeks(String region, String date){
        return mapper.selectRegionalVaccineTwoWeeks(region, date);
    }
    public List<VaccineWeeksVO> selectVaccineInfo (String date){

        Calendar now = Calendar.getInstance();
        Calendar standard =Calendar.getInstance();


        standard.set(Calendar.HOUR_OF_DAY,11);
        standard.set(Calendar.MINUTE,0);
        standard.set(Calendar.SECOND,0);

        if(now.getTimeInMillis() < standard.getTimeInMillis()){
            now.add(Calendar.DATE,-1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String dt = formatter.format(now.getTime());
        return mapper.selectVaccineInfo(dt);
    }
}
