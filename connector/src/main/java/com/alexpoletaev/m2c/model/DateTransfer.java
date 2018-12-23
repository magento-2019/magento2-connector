package com.alexpoletaev.m2c.model;

public class DateTransfer {
    private String fromDate;
    private String toDate;
    private String period;

    public DateTransfer(String fromDate, String toDate, String period) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.period = period;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
