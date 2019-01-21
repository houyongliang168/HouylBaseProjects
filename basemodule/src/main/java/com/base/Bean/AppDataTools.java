package com.base.Bean;

import android.content.Context;
import android.text.TextUtils;

import com.base.common.utils.SPUtils;


/**
 * Created by 41113 on 2018/7/10.
 */

public class AppDataTools {
    private AppDataBaseBean dataBase;


    private AppDataTools() {
    }

    private static class Bulid {
        private static final AppDataTools single = new AppDataTools();
    }

    public static AppDataTools getInstance() {
        return Bulid.single;
    }

    /*存取数据的方法*/
    public  void saveAppDataBaseBean2SP(Context context, AppDataBaseBean t) {
        SPUtils.getInstance().saveBean2SP(context,t);
    }

    /*存取基本的数据的方法*/
    public  void saveBaseAppDataBaseBean2SP(Context context, String agentName, String channel, String branchOffice, String staffNumber, String staffNum_Pin, String token) {
        dataBase=null;
        AppDataBaseBean bean=new AppDataBaseBean();
        bean.setName(TextUtils.isEmpty(agentName)?"":agentName);
        bean.setChannel(TextUtils.isEmpty(channel)?"":channel);
        bean.setOrgCode(TextUtils.isEmpty(branchOffice)?"":branchOffice);
        bean.setStaffNumber(TextUtils.isEmpty(staffNumber)?"":staffNumber);
        bean.setStaffNumber_Pin(TextUtils.isEmpty(staffNum_Pin)?"":staffNum_Pin);
        bean.setToken(TextUtils.isEmpty(token)?"":token);
        SPUtils.getInstance().saveBean2SP(context,bean);
    }

    /*清除基本数据的方法*/
    public  void cleanAppDataBaseBeanFromSP(Context context) {
        SPUtils.getInstance().remove(context,AppDataBaseBean.class);
    }




    /*获取数据的方法*/
    public String  getChannel(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getChannel())){
            return "";
        }else{
            return dataBase.getChannel();
        }
    }

    /*获取数据的方法*/
    public String  getBranchOffice(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getOrgCode())){
            return "";
        }else{
            return dataBase.getOrgCode();
        }
    }

    /*获取数据的方法*/
    public String  getStaffNumber(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getStaffNumber())){
            return "";
        }else{
            return dataBase.getStaffNumber();
        }
    }

    /*获取数据的方法*/
    public String  getStaffNumber_Pin(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getStaffNumber_Pin())){
            return "";
        }else{
            return dataBase.getStaffNumber_Pin();
        }
    }

    /*获取代理人名称的方法*/
    public String  getAgentName(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getName())){
            return "";
        }else{
            return dataBase.getName();
        }
    }

    /*获取数据的方法*/
    public String  getToken(Context context){

        if(dataBase==null){
            dataBase = SPUtils.getInstance().getBeanFromSP(context.getApplicationContext(), AppDataBaseBean.class);
        }
        if(dataBase==null){
            return "";
        }
        if(TextUtils.isEmpty(dataBase.getToken())){
            return "";
        }else{
            return dataBase.getToken();
        }
    }

}
