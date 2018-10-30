package com.decathlon.finance.taxreport.mapper;

import com.decathlon.finance.taxreport.model.PersonInfo;

import java.util.List;

public interface PersonInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PersonInfo record);

    int insertSelective(PersonInfo record);

    PersonInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PersonInfo record);

    int updateByPrimaryKey(PersonInfo record);

    List<PersonInfo> selectAllPersonInfo();
}