package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.mapper.FiscalInfoMapper;
import com.decathlon.finance.taxreport.model.FiscalInfo;
import com.decathlon.finance.taxreport.service.FiscalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="fiscalInfoService")
public class FiscalInfoServiceImpl implements FiscalInfoService {

    @Autowired
    private FiscalInfoMapper fiscalMapper;

    @Override
    public int addFiscalInfo(FiscalInfo fi) {
        return fiscalMapper.insertSelective(fi);
    }

    @Override
    public List getAllFiscalInfo() {
        return fiscalMapper.selectAllFiscalInfo();
    }

    @Override
    public Map<String,String> getFiscalInfoMaps()
    {
        Map<String,String> m = new HashMap<String,String>();
        List<FiscalInfo> lst = fiscalMapper.selectAllFiscalInfo();
        for(FiscalInfo f : lst)
        {
            m.put(f.getFiscalno().toString(),f.getFiscalname());
        }
        return m;
    }
}
