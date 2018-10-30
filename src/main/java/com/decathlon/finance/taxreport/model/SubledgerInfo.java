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
public class SubledgerInfo  {
    private String year;
    private String month;
    private String accountCode;
    private String accountDesc;
    private String companyinfo;
    private JRBeanCollectionDataSource tabledata;
}
