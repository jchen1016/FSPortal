package com.decathlon.finance.taxreport.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    private String code;
    private String company;
    private String year;
    private String month;
    private double balance_m;
    private double balance_dec_y;
    private double balance_last_m;
}
