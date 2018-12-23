package com.alexpoletaev.m2c.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportResponse implements Serializable {
    private String label;
    private List<ReportResponseEntity> items = new ArrayList<>();

    public ReportResponse(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ReportResponseEntity> getItems() {
        return items;
    }

    public void setItems(List<ReportResponseEntity> items) {
        this.items = items;
    }
}
