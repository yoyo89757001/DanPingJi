package com.example.danpingji.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class BuMenBeans {


    /**
     * createTime : 1514370579628
     * dtoResult : 0
     * modifyTime : 1514370579628
     * objects : [{"accountId":0,"accountName":"","createTime":{},"deptName":"技术部","dtoDesc":"","dtoResult":0,"id":0,"modifyTime":{},"pageNum":0,"pageSize":0,"sid":0,"status":0,"token":"","userAccount":""},{"accountId":0,"accountName":"","createTime":{},"deptName":"特邀嘉宾","dtoDesc":"","dtoResult":0,"id":0,"modifyTime":{},"pageNum":0,"pageSize":0,"sid":0,"status":0,"token":"","userAccount":""},{"accountId":0,"accountName":"","createTime":{},"deptName":"市公司领导","dtoDesc":"","dtoResult":0,"id":0,"modifyTime":{},"pageNum":0,"pageSize":0,"sid":0,"status":0,"token":"","userAccount":""},{"accountId":0,"accountName":"","createTime":{},"deptName":"省公司领导","dtoDesc":"","dtoResult":0,"id":0,"modifyTime":{},"pageNum":0,"pageSize":0,"sid":0,"status":0,"token":"","userAccount":""}]
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     * totalNum : 4
     */

    private long createTime;
    private int dtoResult;
    private long modifyTime;
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
         * accountId : 0
         * accountName :
         * createTime : {}
         * deptName : 技术部
         * dtoDesc :
         * dtoResult : 0
         * id : 0
         * modifyTime : {}
         * pageNum : 0
         * pageSize : 0
         * sid : 0
         * status : 0
         * token :
         * userAccount :
         */

        private int accountId;
        private String accountName;
        private CreateTimeBean createTime;
        private String deptName;
        private String dtoDesc;
        private int dtoResult;
        private int id;
        private ModifyTimeBean modifyTime;
        private int pageNum;
        private int pageSize;
        private int sid;
        private int status;
        private String token;
        private String userAccount;

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

        public CreateTimeBean getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBean createTime) {
            this.createTime = createTime;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public ModifyTimeBean getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(ModifyTimeBean modifyTime) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public static class CreateTimeBean {
        }

        public static class ModifyTimeBean {
        }
    }
}
