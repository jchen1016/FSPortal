package com.decathlon.finance.taxreport.model;

public class PersonInfo {
    private Integer id;

    private String managerName;

    private String recheckName;

    private String builderName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
    }

    public String getRecheckName() {
        return recheckName;
    }

    public void setRecheckName(String recheckName) {
        this.recheckName = recheckName == null ? null : recheckName.trim();
    }

    public String getBuilderName() {
        return builderName;
    }

    public void setBuilderName(String builderName) {
        this.builderName = builderName == null ? null : builderName.trim();
    }
}