package com.decathlon.finance.taxreport.mapper;

import com.decathlon.finance.taxreport.model.StoreInfo;

import java.util.List;

public interface StoreInfoMapper {
    int insert(StoreInfo record);

    int insertSelective(StoreInfo record);

    List selectAllStoreInfo();
}