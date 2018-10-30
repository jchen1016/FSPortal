package com.decathlon.finance.taxreport.service;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

public interface HttpRequestService {
    public String client(String url, HttpMethod method, MultiValueMap<String, String> params);
}
