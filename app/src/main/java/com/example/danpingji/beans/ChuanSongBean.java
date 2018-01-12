package com.example.danpingji.beans;



import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/22.
 */

@Parcel
public class ChuanSongBean implements Serializable{
    public String name;
    public int type;
    public Long id;
    public String dianhua;
    public String beifangren;
    public String beifangshijian;
    public String shiyou;

    public ChuanSongBean(String name, int type, Long id, String dianhua, String beifangren, String beifangshijian, String shiyou) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.dianhua = dianhua;
        this.beifangren = beifangren;
        this.beifangshijian = beifangshijian;
        this.shiyou = shiyou;
    }

    public ChuanSongBean() {
    }

    @Override
    public String toString() {
        return "ChuanSongBean{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", dianhua='" + dianhua + '\'' +
                ", beifangren='" + beifangren + '\'' +
                ", beifangshijian='" + beifangshijian + '\'' +
                ", shiyou='" + shiyou + '\'' +
                '}';
    }

    public String getShiyou() {
        return shiyou;
    }

    public void setShiyou(String shiyou) {
        this.shiyou = shiyou;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDianhua() {
        return dianhua;
    }

    public void setDianhua(String dianhua) {
        this.dianhua = dianhua;
    }

    public String getBeifangren() {
        return beifangren;
    }

    public void setBeifangren(String beifangren) {
        this.beifangren = beifangren;
    }

    public String getBeifangshijian() {
        return beifangshijian;
    }

    public void setBeifangshijian(String beifangshijian) {
        this.beifangshijian = beifangshijian;
    }
}
