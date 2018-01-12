package com.example.danpingji.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class YuYueBean {


    /**
     * createTime : 1505911432569
     * dtoResult : 0
     * modifyTime : 1505911432569
     * objects : [{"accountId":10000005,"accountName":"市电","address":"","audit":"0","cardNumber":"rt1505910806485","cardPhoto":"","countType":0,"createTime":1505910775000,"dtoResult":0,"gender":1,"id":10000155,"level":"","modifyTime":1505910775000,"name":"小陈","organ":"","pageNum":0,"pageSize":0,"phone":"","reason":"","scanPhoto":"20170920/1505910756677.jpg","score":0,"sid":0,"source":"手动来源","status":1,"visitDate":1505910720000,"visitDepartment":"111222","visitIncident":"已确认,电话联系被访人","visitPerson":""}]
     * pageIndex : 0
     * pageNum : 1
     * pageSize : 10
     * sid : 0
     * totalNum : 1
     */

    private long createTime;
    private int dtoResult;
    private long modifyTime;
    private int pageIndex;
    private int pageNum;
    private int pageSize;
    private int sid;
    private int totalNum;
    private List<ObjectsBean> objects;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<ObjectsBean> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectsBean> objects) {
        this.objects = objects;
    }

    public static class ObjectsBean {
        /**
         * accountId : 10000005
         * accountName : 市电
         * address :
         * audit : 0
         * cardNumber : rt1505910806485
         * cardPhoto :
         * countType : 0
         * createTime : 1505910775000
         * dtoResult : 0
         * gender : 1
         * id : 10000155
         * level :
         * modifyTime : 1505910775000
         * name : 小陈
         * organ :
         * pageNum : 0
         * pageSize : 0
         * phone :
         * reason :
         * scanPhoto : 20170920/1505910756677.jpg
         * score : 0
         * sid : 0
         * source : 手动来源
         * status : 1
         * visitDate : 1505910720000
         * visitDepartment : 111222
         * visitIncident : 已确认,电话联系被访人
         * visitPerson :
         */

        private int accountId;
        private String accountName;
        private String address;
        private String audit;
        private String cardNumber;
        private String cardPhoto;
        private int countType;
        private long createTime;
        private int dtoResult;
        private int gender;
        private Long id;
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

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAudit() {
            return audit;
        }

        public void setAudit(String audit) {
            this.audit = audit;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardPhoto() {
            return cardPhoto;
        }

        public void setCardPhoto(String cardPhoto) {
            this.cardPhoto = cardPhoto;
        }

        public int getCountType() {
            return countType;
        }

        public void setCountType(int countType) {
            this.countType = countType;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrgan() {
            return organ;
        }

        public void setOrgan(String organ) {
            this.organ = organ;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getScanPhoto() {
            return scanPhoto;
        }

        public void setScanPhoto(String scanPhoto) {
            this.scanPhoto = scanPhoto;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getVisitDate() {
            return visitDate;
        }

        public void setVisitDate(long visitDate) {
            this.visitDate = visitDate;
        }

        public String getVisitDepartment() {
            return visitDepartment;
        }

        public void setVisitDepartment(String visitDepartment) {
            this.visitDepartment = visitDepartment;
        }

        public String getVisitIncident() {
            return visitIncident;
        }

        public void setVisitIncident(String visitIncident) {
            this.visitIncident = visitIncident;
        }

        public String getVisitPerson() {
            return visitPerson;
        }

        public void setVisitPerson(String visitPerson) {
            this.visitPerson = visitPerson;
        }
    }
}
