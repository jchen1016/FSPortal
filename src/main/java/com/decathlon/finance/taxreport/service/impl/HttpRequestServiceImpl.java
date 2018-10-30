package com.decathlon.finance.taxreport.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.decathlon.finance.taxreport.service.HttpRequestService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Service(value = "httpRequestService")
public class HttpRequestServiceImpl implements HttpRequestService {
    @Override
    public String client(String url, HttpMethod method, MultiValueMap<String, String> params) {
        JSONArray totalJS = new JSONArray();
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ik1BSU4ifQ.eyJzY29wZSI6WyJwcm9maWxlIiwib3BlbmlkIl0sImNsaWVudF9pZCI6IlBFWF9HRVhfQ2hpbmEiLCJpc3MiOiJpZHBkZWNhdGhsb24iLCJqdGkiOiJjblJEVFBwTHowIiwic3ViIjoiSkNIRU45NCIsInVpZCI6IkpDSEVOOTQiLCJleHAiOjE1MzY5MjA4Mjl9.YuvWQUiChSNU0Q2sLto9ffEIglJ_8j4O3EeCPfADmMfXhXKVqD4Xx8AUScxIDXWAuO-Z6Zb-hbx2p52jLKWN9SkdsWjIsa2hkJQUpqyd_F7jIXjiLSr1-IbDYYsJPzyOHwB-sLolkw6UQTPISF3gONem9gs3eK0DdPV00zB-TLDcaOP46nlOe8XSfEfpCnwdUTTNUZoOyHxookcRFvkiAlSbBhH9HVRD0fK-kE1jQJFAJnEwPA1eD5aedOHqmz2nk2c-MjUobcHNDb99mOOhiviXMUfnncdtZ0aBa6GMAiHglHkqJEr6A__LXz9Qi9XmiFf7jQwY63G3xJ6xw4ubwQ");
        headers.add("x-api-key","5123af9b-9347-460e-bbb3-89c28d0c75b2");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        //  执行HTTP请求
        String tempUrl = url.replaceAll("startPage","0");
        tempUrl = tempUrl.replaceAll("endPage",String.valueOf(49));
        ResponseEntity<String> response = client.exchange(tempUrl, method, requestEntity, String.class);
        String res =  response.getBody();
        JSONObject  jo = JSONObject.parseObject(res);
        int count = jo.getInteger("num_found")-1;
        int round = 0;
        if(count%49 == 0)
        {
            round = count/49;
        }
        else
        {
            round = count/49 +1;
        }
        int index = 0;
        int end = 50;
        for(int i = 0 ;i<round; i++)
        {
            tempUrl = url.replaceAll("startPage",String.valueOf(index));
            tempUrl = tempUrl.replaceAll("endPage",String.valueOf(end));
            jo = JSONObject.parseObject(client.exchange(tempUrl, method, requestEntity, String.class).getBody());
            JSONArray js = (JSONArray)jo.get("content");
            totalJS.addAll(js);
            index += 50;
            if(i+2 == round)
            {
                end = count+2;
            }
            else
            {
                end += 50;
            }
        }

        return totalJS.toJSONString();
    }


}
