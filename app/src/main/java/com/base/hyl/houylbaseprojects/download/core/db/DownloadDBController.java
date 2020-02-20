package com.base.hyl.houylbaseprojects.download.core.db;



import com.base.hyl.houylbaseprojects.download.core.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.download.core.domain.DownloadThreadInfo;

import java.util.List;

/**
 */

public interface DownloadDBController {

    List<DownloadInfo> findAllDownloading();

    List<DownloadInfo> findAllDownloaded();

    DownloadInfo findDownloadedInfoById(String id);
    /* 通过 uri 查找数据*/
     DownloadInfo findDownloadedInfoByUri(String uri);
    void pauseAllDownloading();

    void createOrUpdate(DownloadInfo downloadInfo);

    void createOrUpdate(DownloadThreadInfo downloadThreadInfo);

    void delete(DownloadInfo downloadInfo);

    void delete(DownloadThreadInfo download);
}
