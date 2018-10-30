package com.decathlon.finance.taxreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VoucherInfo {
    private String year;
    private String month;
    private String day;
    private String companyname;
    private String doctype;
    private String docnum;
    private String manager;
    private String recheck;
    private String builder;
    private JRBeanCollectionDataSource tabledata;
}
