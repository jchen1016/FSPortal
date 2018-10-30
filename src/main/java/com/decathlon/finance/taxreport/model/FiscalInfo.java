package com.decathlon.finance.taxreport.model;


import io.swagger.annotations.ApiModel;
import java.io.Serializable;

public class FiscalInfo{
    private Integer id;

    private Long fiscalno;

    private String fiscalname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFiscalno() {
        return fiscalno;
    }

    public void setFiscalno(Long fiscalno) {
        this.fiscalno = fiscalno;
    }

    public String getFiscalname() {
        return fiscalname;
    }

    public void setFiscalname(String fiscalname) {
        this.fiscalname = fiscalname;
    }
}