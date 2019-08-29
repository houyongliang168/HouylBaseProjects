package com.base.hyl.houylbaseprojects.download.core.domain;

import android.support.annotation.IntDef;
import android.text.TextUtils;


import com.base.hyl.houylbaseprojects.download.bean.ClickBaseBean;
import com.base.hyl.houylbaseprojects.download.core.callback.DownloadListener;
import com.base.hyl.houylbaseprojects.download.core.exception.DownloadException;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 15/01/2017.
 */

public class DownloadInfo extends ClickBaseBean implements Serializable {

    /**
     *
     */
    public static final int STATUS_NONE = 0;
    /**
     *
     */
    public static final int STATUS_PREPARE_DOWNLOAD = 1;
    /**
     *
     */
    public static final int STATUS_DOWNLOADING = 2;
    /**
     *
     */
    public static final int STATUS_WAIT = 3;
    /**
     *
     */
    public static final int STATUS_PAUSED = 4;
    /**
     *
     */
    public static final int STATUS_COMPLETED = 5;
    /**
     *
     */
    public static final int STATUS_ERROR = 6;
    /**
     *
     */
    public static final int STATUS_REMOVED = 7;


    private transient DownloadListener downloadListener;
    private DownloadException exception;

    //--------------------The following fields require persistence.
    /**
     * Each download task id.
     */
    private String id;
    /**
     * Time to create a download task.
     */
    private long createAt;
    private String uri;
    private String path;
    private long size;
    private long progress;
    @DownloadStatus
    private int status;

    private String imgUrl;
    private String title;
    private String description;
    private String liveId;
    private int  isBanner=1;



    public int getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(int isBanner) {
        this.isBanner = isBanner;
    }

    public boolean isBanner() {
        return isBanner == 0;
    }

    public void setBanner(boolean isBanner) {
        this.isBanner = isBanner ? 0 : 1;
    }




    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    /**
     * Support multi-threaded download.
     */
    private int supportRanges;

    private List<DownloadThreadInfo> downloadThreadInfos;


    public DownloadException getException() {
        return exception;
    }

    public void setException(DownloadException exception) {
        this.exception = exception;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public DownloadListener getDownloadListener() {
        return downloadListener;
    }

    public void setDownloadListener(
            DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @DownloadStatus
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSupportRanges() {
        return supportRanges;
    }

    public void setSupportRanges(int supportRanges) {
        this.supportRanges = supportRanges;
    }

    public boolean isSupportRanges() {
        return supportRanges == 0;
    }

    public void setSupportRanges(boolean supportRanges) {
        this.supportRanges = supportRanges ? 0 : 1;
    }

    public List<DownloadThreadInfo> getDownloadThreadInfos() {
        return downloadThreadInfos;
    }

    public void setDownloadThreadInfos(List<DownloadThreadInfo> downloadThreadInfos) {
        this.downloadThreadInfos = downloadThreadInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DownloadInfo downloadInfo = (DownloadInfo) o;

        return id == downloadInfo.id;

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isPause() {
        return status == DownloadInfo.STATUS_PAUSED || status == DownloadInfo.STATUS_ERROR
                || status == STATUS_REMOVED;
    }

    /**
     * Download info status.
     */
    @IntDef({STATUS_NONE, STATUS_PREPARE_DOWNLOAD, STATUS_DOWNLOADING, STATUS_WAIT, STATUS_PAUSED,
            STATUS_COMPLETED, STATUS_ERROR, STATUS_REMOVED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadStatus {

    }

    /**
     * Download info builder.
     */
    public static final class Builder {

        private static final String DEFAULT_ENCODE = "utf-8";

        private String id;
        private long createAt = -1;
        private String url;
        private String path;

        public Builder() {

        }


        public Builder setCreateAt(long createAt) {
            this.createAt = createAt;
            return this;
        }


        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }


        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public void setId(String id) {
            this.id = id;
        }

        public DownloadInfo build() {
            DownloadInfo downloadInfo = new DownloadInfo();

            if (TextUtils.isEmpty(url)) {
                throw new DownloadException(DownloadException.EXCEPTION_URL_NULL, "uri cannot be null.");
            }

            downloadInfo.setUri(url);

            if (TextUtils.isEmpty(path)) {
                throw new DownloadException(DownloadException.EXCEPTION_PATH_NULL, "path cannot be null.");
            }

            downloadInfo.setPath(path);

            if (createAt == -1) {
                setCreateAt(System.currentTimeMillis());
            }

            downloadInfo.setId(url);

            if (TextUtils.isEmpty(id)) {
                downloadInfo.setId(url);
            }

            return downloadInfo;
        }

    }
}
