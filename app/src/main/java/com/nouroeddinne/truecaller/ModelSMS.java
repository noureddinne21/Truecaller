package com.nouroeddinne.truecaller;

public class ModelSMS {

    private String adress;
    private String msg;
    private String data;

    public ModelSMS(String adress, String msg, String data) {
        this.adress = adress;
        this.msg = msg;
        this.data = data;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
