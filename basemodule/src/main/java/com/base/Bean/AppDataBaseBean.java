package com.base.Bean;

import java.util.List;

/**
 * Created by 41113 on 2018/7/10.
 */

public class AppDataBaseBean {
    private boolean isOA;////是否是内勤 默认为false
    private int id;
    private int totalPolicyNum;
    private int totalCustomerNum;
    private String token;
    private String staffNumber_Pin;//拼接的personInfo.getUserInfo().getChannel() + "_" + personInfo.getUserInfo().getOrgCode() + "_" + personInfo.getUserInfo().getStaffNumber();
    private String isBind;
    private String channel;
    private String staffNumber;
    private String orgCode;
    private String orgDesc;
    private String cardType;
    private String cardSeq;
    private String enterDate;
    private String workLife;

    private String highestEdu;
    private String mobile;
    private String gender;
    private String address;
    private String name;
    private String nickName;
    private String headUrl;
    private String password;
    private String rankDesc;
    private String honorDesc;
    private String agtype;
    private String ucmCompanyCode;
    private String ucmEmployNo;
    private String employeeNumber;// EHR工号
    private List<String> roles;// 角色权限

    public String getStaffNumber_Pin() {
        return staffNumber_Pin;
    }

    public void setStaffNumber_Pin(String staffNumber_Pin) {
        this.staffNumber_Pin = staffNumber_Pin;
    }


    public boolean isOA() {
        return isOA;
    }

    public void setOA(boolean OA) {
        isOA = OA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPolicyNum() {
        return totalPolicyNum;
    }

    public void setTotalPolicyNum(int totalPolicyNum) {
        this.totalPolicyNum = totalPolicyNum;
    }

    public int getTotalCustomerNum() {
        return totalCustomerNum;
    }

    public void setTotalCustomerNum(int totalCustomerNum) {
        this.totalCustomerNum = totalCustomerNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getIsBind() {
        return isBind;
    }

    public void setIsBind(String isBind) {
        this.isBind = isBind;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardSeq() {
        return cardSeq;
    }

    public void setCardSeq(String cardSeq) {
        this.cardSeq = cardSeq;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getWorkLife() {
        return workLife;
    }

    public void setWorkLife(String workLife) {
        this.workLife = workLife;
    }

    public String getHighestEdu() {
        return highestEdu;
    }

    public void setHighestEdu(String highestEdu) {
        this.highestEdu = highestEdu;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc;
    }

    public String getHonorDesc() {
        return honorDesc;
    }

    public void setHonorDesc(String honorDesc) {
        this.honorDesc = honorDesc;
    }

    public String getAgtype() {
        return agtype;
    }

    public void setAgtype(String agtype) {
        this.agtype = agtype;
    }

    public String getUcmCompanyCode() {
        return ucmCompanyCode;
    }

    public void setUcmCompanyCode(String ucmCompanyCode) {
        this.ucmCompanyCode = ucmCompanyCode;
    }

    public String getUcmEmployNo() {
        return ucmEmployNo;
    }

    public void setUcmEmployNo(String ucmEmployNo) {
        this.ucmEmployNo = ucmEmployNo;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AppDataBaseBean{" +
                "isOA=" + isOA +
                ", id=" + id +
                ", totalPolicyNum=" + totalPolicyNum +
                ", totalCustomerNum=" + totalCustomerNum +
                ", token='" + token + '\'' +
                ", staffNumber_Pin='" + staffNumber_Pin + '\'' +
                ", isBind='" + isBind + '\'' +
                ", channel='" + channel + '\'' +
                ", staffNumber='" + staffNumber + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgDesc='" + orgDesc + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardSeq='" + cardSeq + '\'' +
                ", enterDate='" + enterDate + '\'' +
                ", workLife='" + workLife + '\'' +
                ", highestEdu='" + highestEdu + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", password='" + password + '\'' +
                ", rankDesc='" + rankDesc + '\'' +
                ", honorDesc='" + honorDesc + '\'' +
                ", agtype='" + agtype + '\'' +
                ", ucmCompanyCode='" + ucmCompanyCode + '\'' +
                ", ucmEmployNo='" + ucmEmployNo + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", roles=" + roles +
                '}';
    }
}
