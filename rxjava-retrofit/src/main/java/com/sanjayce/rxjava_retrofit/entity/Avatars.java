package com.sanjayce.rxjava_retrofit.entity;

/**
 * Created by lenovo on 2018/9/1
 */

public class Avatars {
    private String small;

    private String large;

    private String medium;

    public void setSmall(String small){
        this.small = small;
    }

    public String getSmall(){
        return this.small;
    }
    public void setLarge(String large){
        this.large = large;
    }
    public String getLarge(){
        return this.large;
    }
    public void setMedium(String medium){
        this.medium = medium;
    }
    public String getMedium(){
        return this.medium;
    }

    @Override
    public String toString() {
        return "Avatars{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                '}';
    }
}
