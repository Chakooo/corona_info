package com.coronaService.mapper;

import java.util.List;

import com.coronaService.vo.CoronaInfoVO;
import com.coronaService.vo.CoronaSidoInfoVO;
import com.coronaService.vo.CoronaVaccineInfoVO;
import com.coronaService.vo.CoronaWeeksVO;
import com.coronaService.vo.VaccineWeeksVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegionalInfoMapper {
    public CoronaSidoInfoVO selectRegionalCoronaInfo (String region , String date);
    public CoronaInfoVO selectCoronaInfoRegionTotal(String date);
    


    //SELECT VACCINEINFO BY REGION  
    public CoronaVaccineInfoVO selectCoronaVaccineInfo (String region , String date);

    public String selectDangerAge(String date);
    public List<CoronaWeeksVO> selectRegionalCoronaTwoWeeks(String region, String date);
    public List<VaccineWeeksVO> selectRegionalVaccineTwoWeeks(String region, String date);
    public List<VaccineWeeksVO> selectVaccineInfo (String date);

    
}
