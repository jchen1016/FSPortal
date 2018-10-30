package com.decathlon.finance.taxreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SourceData{
    private String code1;
    private String subIca;
    private String code;
    private String catCodeDesc;
    private String company;
    private String costCenter;
    private String docNum;
    private String docType;
    private String batchType;
    private String date;
    private String amount;
    private String explain;
    private String year;
    private String month;
    private String day;
    private String explainRem;
    private String rem2;
}
