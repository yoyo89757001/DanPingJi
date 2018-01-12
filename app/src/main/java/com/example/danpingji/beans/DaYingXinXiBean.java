package com.example.danpingji.beans;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/9/21.
 */
@Entity
public class DaYingXinXiBean {
    @Id
    @NotNull
    private Long id;
    private int accountId;
    private String accountName;
    private String cardNumber;
    private String cardPhoto;
    private int countType;
    private int dtoResult;
    private int gender;
    private int idX;
    private String level;
    private long modifyTime;
    private String name;
    private String organ;
    private int pageNum;
    private int pageSize;
    private String phone;
    private String reason;
    private String scanPhoto;
    private int score;
    private int sid;
    private String source;
    private int status;
    private long visitDate;
    private String visitDepartment;
    private String visitIncident;
    private String visitPerson;
    private String biduijieguo;
    private String yuyueID;
    @Generated(hash = 120605730)
    public DaYingXinXiBean(@NotNull Long id, int accountId, String accountName,
            String cardNumber, String cardPhoto, int countType, int dtoResult,
            int gender, int idX, String level, long modifyTime, String name,
            String organ, int pageNum, int pageSize, String phone, String reason,
            String scanPhoto, int score, int sid, String source, int status,
            long visitDate, String visitDepartment, String visitIncident,
            String visitPerson, String biduijieguo, String yuyueID) {
        this.id = id;
        this.accountId = accountId;
        this.accountName = accountName;
        this.cardNumber = cardNumber;
        this.cardPhoto = cardPhoto;
        this.countType = countType;
        this.dtoResult = dtoResult;
        this.gender = gender;
        this.idX = idX;
        this.level = level;
        this.modifyTime = modifyTime;
        this.name = name;
        this.organ = organ;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.phone = phone;
        this.reason = reason;
        this.scanPhoto = scanPhoto;
        this.score = score;
        this.sid = sid;
        this.source = source;
        this.status = status;
        this.visitDate = visitDate;
        this.visitDepartment = visitDepartment;
        this.visitIncident = visitIncident;
        this.visitPerson = visitPerson;
        this.biduijieguo = biduijieguo;
        this.yuyueID = yuyueID;
    }
    @Generated(hash = 923867102)
    public DaYingXinXiBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAccountId() {
        return this.accountId;
    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return this.accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getCardNumber() {
        return this.cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardPhoto() {
        return this.cardPhoto;
    }
    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }
    public int getCountType() {
        return this.countType;
    }
    public void setCountType(int countType) {
        this.countType = countType;
    }
    public int getDtoResult() {
        return this.dtoResult;
    }
    public void setDtoResult(int dtoResult) {
        this.dtoResult = dtoResult;
    }
    public int getGender() {
        return this.gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public int getIdX() {
        return this.idX;
    }
    public void setIdX(int idX) {
        this.idX = idX;
    }
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public long getModifyTime() {
        return this.modifyTime;
    }
    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrgan() {
        return this.organ;
    }
    public void setOrgan(String organ) {
        this.organ = organ;
    }
    public int getPageNum() {
        return this.pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return this.pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getScanPhoto() {
        return this.scanPhoto;
    }
    public void setScanPhoto(String scanPhoto) {
        this.scanPhoto = scanPhoto;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getSid() {
        return this.sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public long getVisitDate() {
        return this.visitDate;
    }
    public void setVisitDate(long visitDate) {
        this.visitDate = visitDate;
    }
    public String getVisitDepartment() {
        return this.visitDepartment;
    }
    public void setVisitDepartment(String visitDepartment) {
        this.visitDepartment = visitDepartment;
    }
    public String getVisitIncident() {
        return this.visitIncident;
    }
    public void setVisitIncident(String visitIncident) {
        this.visitIncident = visitIncident;
    }
    public String getVisitPerson() {
        return this.visitPerson;
    }
    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }
    public String getBiduijieguo() {
        return this.biduijieguo;
    }
    public void setBiduijieguo(String biduijieguo) {
        this.biduijieguo = biduijieguo;
    }
    public String getYuyueID() {
        return this.yuyueID;
    }
    public void setYuyueID(String yuyueID) {
        this.yuyueID = yuyueID;
    }





}
