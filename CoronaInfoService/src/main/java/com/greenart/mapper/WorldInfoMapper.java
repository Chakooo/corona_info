package com.greenart.mapper;

import com.greenart.vo.CoronaWorldInfoVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorldInfoMapper {
    public void insertCoronaWorldInfo(CoronaWorldInfoVO vo);

    public Integer selectWorldCoronaSum(String date);
}
