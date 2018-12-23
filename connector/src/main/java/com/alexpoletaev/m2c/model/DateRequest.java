package com.alexpoletaev.m2c.model;

public class DateRequest {
    private DateTransfer dateTransfer;

    public DateRequest(DateTransfer dateTransfer) {
        this.dateTransfer = dateTransfer;
    }

    public DateTransfer getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(DateTransfer dateTransfer) {
        this.dateTransfer = dateTransfer;
    }
}
