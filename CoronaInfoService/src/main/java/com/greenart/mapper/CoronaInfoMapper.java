package com.greenart.mapper;

import java.util.List;

import com.greenart.vo.CoronaAgeInfoVO;
import com.greenart.vo.CoronaInfoVO;
import com.greenart.vo.CoronaSidoInfoVO;
import com.greenart.vo.CoronaVaccineInfoVO;
import com.greenart.vo.VaccineAppointedCenterVO;
import com.greenart.vo.VaccineMedicalCentarVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoronaInfoMapper {
    public void insertCoronaInfo(CoronaInfoVO vo);
    public CoronaInfoVO selectCoronaInfoByDate(String date);

    public void insertCoronaSido(CoronaSidoInfoVO vo);
    public List<CoronaSidoInfoVO> selectCoronaSidoByDate(String date);
    public void insertCoronaAge(CoronaAgeInfoVO vo);
    public List<CoronaAgeInfoVO> selectCoronaAgeInfo(String date); 
    public List<CoronaAgeInfoVO> selectCoronaGenInfo(String date);
    public void insertCoronaVaccineInfo(CoronaVaccineInfoVO vo);
    public List<CoronaVaccineInfoVO> selectCoronaVaccineInfo(String date);



    public void insertMedicalCenterInfo(VaccineMedicalCentarVO vo);
    public List<VaccineMedicalCentarVO> selectMedicalCenterInfo();


    public void updateLocation(VaccineMedicalCentarVO vo);

    public void insertVaccineCenterInfo(VaccineAppointedCenterVO vo);
    public List<VaccineAppointedCenterVO> selectVaccineCenterInfo(); 
}