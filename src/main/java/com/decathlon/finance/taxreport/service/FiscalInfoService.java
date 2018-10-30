package com.decathlon.finance.taxreport.service;

import com.decathlon.finance.taxreport.model.FiscalInfo;

import java.util.List;
import java.util.Map;

public interface FiscalInfoService {
    int addFiscalInfo(FiscalInfo fi);

    List getAllFiscalInfo();

    Map<String,String> getFiscalInfoMaps();
}
