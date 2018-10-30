package com.decathlon.finance.taxreport.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SubledgerItem {
    private String tmonth;
    private String tday;
    private String tcostcenter;
    private String tdocnum;
    private String texplain;
    private String tborrow;
    private String tloan;
    private String tflag;
    private String tbalance;
}
