package com.alexpoletaev.m2c.model;

import java.io.Serializable;

public class ReportResponseEntity implements Serializable {
    private String period;
    private String key;
    private String value;

    public ReportResponseEntity(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
