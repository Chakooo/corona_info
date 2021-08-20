package com.greenart.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CoronaWorldInfoVO {
private Integer seq;
private String areaNm;
private Date createDt;
private Integer natDeathCnt;
private Double natDeathRate;
private Integer natDefCnt;
private String nationNm;
private String nationNmEn;
}
