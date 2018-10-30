package com.decathlon.finance.taxreport.common;

public enum ResultEnum {
    UNKNOW_ERROR(-1,"unknow error"),
    SUCCESS(10000,"success"),
    DATA_IS_NULL(3,"data is null");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
