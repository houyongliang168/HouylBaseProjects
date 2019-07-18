package com.base.hyl.houylbaseprojects.download.db;



import com.base.hyl.houylbaseprojects.download.domain.DownloadInfo;
import com.base.hyl.houylbaseprojects.download.domain.DownloadThreadInfo;

import java.util.List;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/23.
 */

public interface DownloadDBController {

    List<DownloadInfo> findAllDownloading();

    List<DownloadInfo> findAllDownloaded();

    DownloadInfo findDownloadedInfoById(String id);

    void pauseAllDownloading();

    void createOrUpdate(DownloadInfo downloadInfo);

    void createOrUpdate(DownloadThreadInfo downloadThreadInfo);

    void delete(DownloadInfo downloadInfo);

    void delete(DownloadThreadInfo download);
}
