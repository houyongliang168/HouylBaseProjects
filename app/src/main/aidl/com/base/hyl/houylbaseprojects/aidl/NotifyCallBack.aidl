// NotifyCallBack.aidl
package com.base.hyl.houylbaseprojects.aidl;

// Declare any non-default types here with import statements

interface NotifyCallBack {
    void notifyMainUiThread(in String name,boolean joinOrLeave);
}
