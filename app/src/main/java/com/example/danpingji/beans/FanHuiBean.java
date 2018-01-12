package com.example.danpingji.beans;

/**
 * Created by Administrator on 2017/9/20.
 */

public class FanHuiBean {


    /**
     * createTime : 1505893586382
     * dtoDesc : 该设备已经注册
     * dtoResult : -1
     * modifyTime : 1505893586382
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     */

    private long createTime;
    private String dtoDesc;
    private int dtoResult;
    private long modifyTime;
    private int pageNum;
    private int pageSize;
    private int sid;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDtoDesc() {
        return dtoDesc;
    }

    public void setDtoDesc(String dtoDesc) {
        this.dtoDesc = dtoDesc;
    }

    public int getDtoResult() {
        return dtoResult;
    }

    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }
}
