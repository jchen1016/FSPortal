package com.decathlon.finance.taxreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VoucherItem {
    private String explanation;
    private String codedesc;
    private String correspondingat;
    private String curamount;
    private String excrat;
    private String debit;
    private String credit;
}
