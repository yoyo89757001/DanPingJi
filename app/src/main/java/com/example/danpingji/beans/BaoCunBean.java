package com.example.danpingji.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Administrator on 2017/9/14.
 */
@Entity
public class BaoCunBean {
    @Id
    @NotNull
    private Long id;
    private String cameraIP;
    private String zhuji;
    private String zhangHuID;
    private String zhangHuName;
    private boolean isBangDing;
    @Generated(hash = 1694315294)
    public BaoCunBean(@NotNull Long id, String cameraIP, String zhuji,
            String zhangHuID, String zhangHuName, boolean isBangDing) {
        this.id = id;
        this.cameraIP = cameraIP;
        this.zhuji = zhuji;
        this.zhangHuID = zhangHuID;
        this.zhangHuName = zhangHuName;
        this.isBangDing = isBangDing;
    }
    @Generated(hash = 1469853663)
    public BaoCunBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCameraIP() {
        return this.cameraIP;
    }
    public void setCameraIP(String cameraIP) {
        this.cameraIP = cameraIP;
    }
    public String getZhuji() {
        return this.zhuji;
    }
    public void setZhuji(String zhuji) {
        this.zhuji = zhuji;
    }
    public String getZhangHuID() {
        return this.zhangHuID;
    }
    public void setZhangHuID(String zhangHuID) {
        this.zhangHuID = zhangHuID;
    }
    public String getZhangHuName() {
        return this.zhangHuName;
    }
    public void setZhangHuName(String zhangHuName) {
        this.zhangHuName = zhangHuName;
    }
    public boolean getIsBangDing() {
        return this.isBangDing;
    }
    public void setIsBangDing(boolean isBangDing) {
        this.isBangDing = isBangDing;
    }

}
