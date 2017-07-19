package company.co.kr.project03.model;

import java.io.Serializable;

/**
 * Created by Dongjin on 2017. 7. 19..
 */

public class Restaurant implements Serializable{
    String title;
    String address;
    String phoneNum;
    String comment;

    double latitude;
    double longitude;

    public Restaurant(){}

    public Restaurant(String title, String address, String phoneNum, String comment, double latitude, double longitude) {
        this.title = title;
        this.address = address;
        this.phoneNum = phoneNum;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", comment='" + comment + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
