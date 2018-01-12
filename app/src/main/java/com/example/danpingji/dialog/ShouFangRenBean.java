package com.example.danpingji.dialog;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */

public class ShouFangRenBean {


    /**
     * createTime : 1515577220095
     * dtoResult : 0
     * modifyTime : 1515577220095
     * objects : [{"accountId":10000041,"accountName":"体验中心","avatar":"","createTime":1515562560000,"department":"技术部","dtoResult":0,"email":"","gender":0,"id":10000824,"jobStatus":0,"job_number":"","modifyTime":1515562560000,"name":"副总","pageNum":0,"pageSize":0,"phone":"","photo_ids":"20180110/1515562533390.jpg","remark":"","sid":0,"status":1,"subject_type":0,"title":""}]
     * pageNum : 0
     * pageSize : 0
     * sid : 0
     * totalNum : 0
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
         * accountId : 10000041
         * accountName : 体验中心
         * avatar :
         * createTime : 1515562560000
         * department : 技术部
         * dtoResult : 0
         * email :
         * gender : 0
         * id : 10000824
         * jobStatus : 0
         * job_number :
         * modifyTime : 1515562560000
         * name : 副总
         * pageNum : 0
         * pageSize : 0
         * phone :
         * photo_ids : 20180110/1515562533390.jpg
         * remark :
         * sid : 0
         * status : 1
         * subject_type : 0
         * title :
         */

        private int accountId;
        private String accountName;
        private String avatar;
        private long createTime;
        private String department;
        private int dtoResult;
        private String email;
        private int gender;
        private int id;
        private int jobStatus;
        private String job_number;
        private long modifyTime;
        private String name;
        private int pageNum;
        private int pageSize;
        private String phone;
        private String photo_ids;
        private String remark;
        private int sid;
        private int status;
        private int subject_type;
        private String title;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getDtoResult() {
            return dtoResult;
        }

        public void setDtoResult(int dtoResult) {
            this.dtoResult = dtoResult;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getJobStatus() {
            return jobStatus;
        }

        public void setJobStatus(int jobStatus) {
            this.jobStatus = jobStatus;
        }

        public String getJob_number() {
            return job_number;
        }

        public void setJob_number(String job_number) {
            this.job_number = job_number;
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

        public String getPhoto_ids() {
            return photo_ids;
        }

        public void setPhoto_ids(String photo_ids) {
            this.photo_ids = photo_ids;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public int getSubject_type() {
            return subject_type;
        }

        public void setSubject_type(int subject_type) {
            this.subject_type = subject_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
