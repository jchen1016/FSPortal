package com.decathlon.finance.taxreport.model;

public class StoreInfo {
    private Integer id;

    private Long storeno;

    private String storename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getStoreno() {
        return storeno;
    }

    public void setStoreno(Long storeno) {
        this.storeno = storeno;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename == null ? null : storename.trim();
    }
}