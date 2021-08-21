package com.greenart.mapper;


import java.util.List;

import com.greenart.vo.CoronaWorldInfoVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorldInfoMapper {
    public void insertCoronaWorldInfo(CoronaWorldInfoVO vo);

    public CoronaWorldInfoVO selectWorldCoronaSum(String date);
    public CoronaWorldInfoVO selectWorldCoronaSumYesterDay(String date);
    public Integer selectDiffYesterday(String date);
    public Integer selectDiffWeeks(String date);
    public List<CoronaWorldInfoVO> selectChartInfo(String date, String term);

    public List<CoronaWorldInfoVO> selectNationalTodayInfo(String date);
    public List<CoronaWorldInfoVO> selectNationalYesterdayInfo(String date);

}
