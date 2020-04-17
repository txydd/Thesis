package com.cxq.viewer.domain;

public class Entropy {
    private String id;

    private String edata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEdata() {
        return edata;
    }

    public void setEdata(String edata) {
        this.edata = edata == null ? null : edata.trim();
    }
}