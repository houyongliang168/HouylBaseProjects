package com.base.hyl.houylbaseprojects.download.core;


import com.base.hyl.houylbaseprojects.download.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.download.exception.DownloadException;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public interface DownloadResponse {

    void onStatusChanged(DownloadInfo downloadInfo);

    void handleException(DownloadException exception);
}
