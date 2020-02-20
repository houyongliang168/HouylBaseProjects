package com.base.common.net;

/**
 * Created by renshiqian on 2018/10/30.
 */

public class NetWorkStateChange {
    private int mConnectType;

    public NetWorkStateChange(int type) {
        mConnectType = type;
    }

    /**
     * 获取网络连接类型（4G还是wifi）
     *
     * @return ConnectivityManager.TYPE_MOBILE或者别的
     */
    public int getConnectType() {
        return mConnectType;
    }

}
