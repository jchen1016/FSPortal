package com.decathlon.finance.taxreport.service;

import java.util.List;
import java.util.Map;

public interface StoreInfoService {
    List getAllStoreInfo();

    public Map<String,String> getStoreInfoMaps();
}
