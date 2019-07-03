package com.base.hyl.houylbaseprojects.databing.msgBase.base;



import com.base.hyl.houylbaseprojects.Constant;
import com.base.hyl.houylbaseprojects.databing.msgBase.bean.RequestBaseBean;

import rx.Subscriber;

public class BaseSubcriber<T> extends Subscriber<RequestBaseBean<T>> {
    private SubcriberListener<T>  listener;

    public BaseSubcriber setOwnListener(SubcriberListener<T> listener) {
        this.listener = listener;
        return this;
    }



    public SubcriberListener<T> getListener() {
        return listener;
    }

    public void setListener(SubcriberListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {
        if(listener!=null){
            listener.onCompleted();
        }

    }

    @Override
    public void onError(Throwable e) {
        if(listener!=null){
            listener.onError(e);
        }

    }

    @Override
    public void onNext(RequestBaseBean<T> mRequestBaseBean) {
        if(listener!=null&&mRequestBaseBean!=null){
            switch (mRequestBaseBean.getRspCode()){
                case  Constant.GET_DATA_SUCCESS:
                    listener.onSuccess(mRequestBaseBean.getInfo());
                    break;
                    default:
                        listener.onFail(mRequestBaseBean.getRspDesc()+"");
            }
        }

    }

    public interface SubcriberListener<T>{

        public void onCompleted() ;


        public void onError(Throwable e);

        public void onSuccess(T info);

        public void onFail(String info);
    }
}
