package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.mapper.PersonInfoMapper;
import com.decathlon.finance.taxreport.model.PersonInfo;
import com.decathlon.finance.taxreport.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "personService")
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    PersonInfoMapper personInfoMapper;

    @Override
    public List<PersonInfo> getAllPersonInfo() {
        return personInfoMapper.selectAllPersonInfo();
    }
}
