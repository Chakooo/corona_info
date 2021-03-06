package com.coronaService.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.coronaService.mapper.CoronaInfoMapper;
import com.coronaService.vo.CoronaAgeInfoVO;
import com.coronaService.vo.CoronaInfoVO;
import com.coronaService.vo.CoronaSidoInfoVO;
import com.coronaService.vo.CoronaVaccineInfoVO;
import com.coronaService.vo.VaccineAppointedCenterVO;
import com.coronaService.vo.VaccineMedicalCentarVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoronaInfoService {
    @Autowired
    CoronaInfoMapper mapper;

    public void insertCoronaInfo(CoronaInfoVO vo) {
        mapper.insertCoronaInfo(vo);
    }

    public CoronaInfoVO selectTodayCoronaInfo() {
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        // Calendar end = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 12);
        standard.set(Calendar.MINUTE, 00);
        standard.set(Calendar.SECOND,0);

        // 2021-08-11 01:00:00 - 접속시간
        // 2021-08-11 10:30:00 - 세팅값
        // 현재시간이 세팅값보다 이전이라면, 전 날의 데이터를 뽑아주고
        // 현재시간이 세팅값보다 나중이라면, 오늘 데이터를 뽑아준다.
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (now.getTimeInMillis() < standard.getTimeInMillis()) {
            // 현재 접속시간이 기준시간 (10시 30분 10초) 보다 이전일 때
            // 하루 이전 날짜로 변경
            now.add(Calendar.DATE, -1);
        }
        String dt = formatter.format(now.getTime());
        
        CoronaInfoVO data = mapper.selectCoronaInfoByDate(dt);
        Integer accExamCnt = data.getAccExamCnt();
        Integer decideCnt = data.getDecideCnt();

        DecimalFormat dFormatter = new DecimalFormat("###,###,###");
        String strAccExamCnt = dFormatter.format(accExamCnt);
        String strDecideCnt = dFormatter.format(decideCnt);

        data.setStrAccExamCnt(strAccExamCnt);
        data.setStrDecideCnt(strDecideCnt);

        return data;
    }

    public void insertCoronaSido(CoronaSidoInfoVO vo) {
        mapper.insertCoronaSido(vo);
    }

    public List<CoronaSidoInfoVO> selectTodayCoronaSido() {
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 12);
        standard.set(Calendar.MINUTE, 00);
        standard.set(Calendar.SECOND, 00);

        if (now.getTimeInMillis() < standard.getTimeInMillis()) {
            // 현재 접속시간이 기준시간 (10시 30분 10초) 보다 이전일 때
            // 하루 이전 날짜로 변경
            now.add(Calendar.DATE, -1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());

        return mapper.selectCoronaSidoByDate(dt);
    }

    public List<CoronaSidoInfoVO> selectTodayCoronaSidoByDate(String date) {
        return mapper.selectCoronaSidoByDate(date);
    }

    public void insertCoronaAge(CoronaAgeInfoVO vo) {
        mapper.insertCoronaAge(vo);
    }

    public Map<String, Object> selectCoronaTodayAgeInfo() {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 16);
        standard.set(Calendar.MINUTE, 10);
        standard.set(Calendar.SECOND, 00);

        if (now.getTimeInMillis() < standard.getTimeInMillis()) {
            // 현재 접속시간이 기준시간보다 이전일때
            // 하루 전 날의 날짜로 변경하고 정보를 가져온다.
            now.add(Calendar.DATE, -1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());

        resultMap.put("dt", dt);
        resultMap.put("data", mapper.selectCoronaAgeInfo(dt));
        return resultMap;

    }

    public List<CoronaAgeInfoVO> selectCoronaTodayGenInfo() {
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 16);
        standard.set(Calendar.MINUTE, 10);
        standard.set(Calendar.SECOND, 00);

        if (now.getTimeInMillis() < standard.getTimeInMillis()) {
            // 현재 접속시간이 기준시간보다 이전일때
            // 하루 전 날의 날짜로 변경하고 정보를 가져온다.
            now.add(Calendar.DATE, -1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());
        return mapper.selectCoronaGenInfo(dt);
    }

    public List<CoronaAgeInfoVO> selectCoronaAgeInfo(String date) {
        return mapper.selectCoronaAgeInfo(date);

    }

    public List<CoronaAgeInfoVO> selectCoronaGenInfo(String date) {
        return mapper.selectCoronaGenInfo(date);
    }

    public void insertCoronaVaccineInfo(CoronaVaccineInfoVO vo) {
        mapper.insertCoronaVaccineInfo(vo);
    }

    public List<CoronaVaccineInfoVO> selectTodayCoronaVaccineInfo() {
        Calendar now = Calendar.getInstance();
        Calendar standard = Calendar.getInstance();
        standard.set(Calendar.HOUR_OF_DAY, 11);
        standard.set(Calendar.MINUTE, 00);
        standard.set(Calendar.SECOND, 00);

        if (now.getTimeInMillis() < standard.getTimeInMillis()) {
            // 현재 접속시간이 기준시간보다 이전일때
            // 하루 전 날의 날짜로 변경하고 정보를 가져온다.
            now.add(Calendar.DATE, -1);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dt = formatter.format(now.getTime());
        System.out.println(dt);

        return mapper.selectCoronaVaccineInfo(dt);
    }

    public List<CoronaVaccineInfoVO> selectCoronaVaccineInfo(String date) {
        
        return mapper.selectCoronaVaccineInfo(date);
    }
    public void insertMedicalCenterInfo(VaccineMedicalCentarVO vo){
        mapper.insertMedicalCenterInfo(vo);
    }
    public List<VaccineMedicalCentarVO> selectMedicalCenterInfo(){
        return mapper.selectMedicalCenterInfo();
    }
    public void updateLocation(VaccineMedicalCentarVO vo){
        mapper.updateLocation(vo);
    }
    public void insertVaccineCenterInfo(VaccineAppointedCenterVO vo){
        mapper.insertVaccineCenterInfo(vo);
    }
    public List<VaccineAppointedCenterVO> selectVaccineCenterInfo(){
        return mapper.selectVaccineCenterInfo();
    }
}
 