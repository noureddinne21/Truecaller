package com.nouroeddinne.truecaller;

public class Model {

    private String name,number,nickname,homeEmail,workEmail,email,workPhoneNumber,homePhoneNumber,others;
    private byte[] img;

    public Model(String name, String number, String nickname, String homeEmail, String workEmail, String email, String workPhoneNumber, String homePhoneNumber, String others, byte[] img) {
        this.name = name;
        this.number = number;
        this.nickname = nickname;
        this.homeEmail = homeEmail;
        this.workEmail = workEmail;
        this.email = email;
        this.workPhoneNumber = workPhoneNumber;
        this.homePhoneNumber = homePhoneNumber;
        this.others = others;
        this.img = img;
    }

    public Model(String name, String number, byte[] img) {
        this.name = name;
        this.number = number;
        this.img = img;
    }

    public Model(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
