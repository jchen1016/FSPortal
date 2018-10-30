package com.decathlon.finance.taxreport.mapper;

import com.decathlon.finance.taxreport.model.FiscalInfo;

import java.util.List;

public interface FiscalInfoMapper {
    int insert(FiscalInfo record);

    int insertSelective(FiscalInfo record);

    List selectAllFiscalInfo();
}