package com.decathlon.finance.taxreport.service.impl;

import com.decathlon.finance.taxreport.mapper.StoreInfoMapper;
import com.decathlon.finance.taxreport.model.FiscalInfo;
import com.decathlon.finance.taxreport.model.StoreInfo;
import com.decathlon.finance.taxreport.service.StoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="storeInfoService")
public class StoreInfoServiceImpl implements StoreInfoService {

    @Autowired
    private StoreInfoMapper storeMapper;

    @Override
    public List getAllStoreInfo() {
        return storeMapper.selectAllStoreInfo();
    }

    @Override
    public Map<String, String> getStoreInfoMaps() {
        Map<String,String> m = new HashMap<String,String>();
        List<StoreInfo> lst = storeMapper.selectAllStoreInfo();
        for(StoreInfo s : lst)
        {
            m.put(s.getStoreno().toString(),s.getStorename());
        }
        return m;
    }
}
