package com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownLoadListener;


import android.os.Process;
import android.util.Log;


import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.Config;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownService;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public class DownloadThread implements Runnable {

    public static final String TAG = "DownloadThread";

    private final Config config;
    private final DownInfo downInfo;
    private InputStream inputStream;
    private   HttpDownService httpService;
    private Subscriber subscriber;

    public DownloadThread(DownInfo downInfo,
                          HttpDownService httpService,
                          Config config) {
        this.config = config;
        this.downInfo = downInfo;
        this.httpService=httpService;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        //    while (!(downloadInfo.isPause() || downloadThreadInfo.isThreadDownloadSuccess())) {

        checkPause();
        try {
            executeDownload();
        } catch (Exception e) {

            //        if (retryDownloadCount >= config.getRetryDownloadCount()) {
            downInfo.setState(DownState.ERROR.ERROR);

//            downloadResponse.onStatusChanged(downloadInfo);
//            downloadResponse.handleException(e);
            //        }
            //
            //        retryDownloadCount++;
        }
        //    checkPause();
        //    }
    }

    private void executeDownload() {
            if(httpService==null){
                return;
            }
        Subscription subscribe=null;
        try {

            /*得到rx对象-上一次下載的位置開始下載*/
             subscribe = httpService.download("bytes=" + downInfo.getReadLength() + "-", downInfo.getUrl())
                    /*指定线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*失败后的retry配置*/
                    .retryWhen(new RetryWhenNetworkException())
                    /*读取下载写入文件*/
                    .map(new Func1<ResponseBody, DownInfo>() {
                        @Override
                        public DownInfo call(ResponseBody responseBody) {
                            if (downInfo.getState() == DownState.PAUSE) {
                                return downInfo;
                            }
                            writeCaches(responseBody, new File(downInfo.getSavePath()), downInfo);
                            return downInfo;
                        }
                    })
                    /*回调线程*/
                    .observeOn(AndroidSchedulers.mainThread())
                    /*数据回调*/
                    .subscribe(subscriber);


//            final URL url = new URL(downloadThreadInfo.getUri());
//            httpConnection = (HttpURLConnection) url.openConnection();
//            httpConnection.setConnectTimeout(config.getConnectTimeout());
//            httpConnection.setReadTimeout(config.getReadTimeout());
//            httpConnection.setRequestMethod(config.getMethod());
//            long lastStart = downloadThreadInfo.getStart() + lastProgress;
//            if (downloadInfo.isSupportRanges()) {
//                httpConnection.setRequestProperty("Range",
//                        "bytes=" + lastStart + "-" + downloadThreadInfo.getEnd());
//            }
//            final int responseCode = httpConnection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_PARTIAL
//                    || responseCode == HttpURLConnection.HTTP_OK) {
//                inputStream = httpConnection.getInputStream();
//                RandomAccessFile raf = new RandomAccessFile(downloadInfo.getPath(), "rwd");
//
//                raf.seek(lastStart);
//                final byte[] bf = new byte[1024 * 4];
//                int len = -1;
//                int offset = 0;
//                while (true) {
//                    checkPause();
//                    len = inputStream.read(bf);
//                    if (len == -1) {
//                        break;
//                    }
//                    raf.write(bf, 0, len);
//                    offset += len;
//
//                    //          synchronized (downloadProgressListener) {
//                    downloadThreadInfo.setProgress(lastProgress + offset);
//                    downloadProgressListener.onProgress();
//                    //          }
//
//                    Log.d(TAG,
//                            "downloadInfo:" + downloadInfo.getId() + " thread:" + downloadThreadInfo.getThreadId()
//                                    + " progress:"
//                                    + downloadThreadInfo.getProgress()
//                                    + ",start:" + downloadThreadInfo.getStart() + ",end:" + downloadThreadInfo
//                                    .getEnd());
//                }
//
//                //downloadInfo success
//                downloadProgressListener.onDownloadSuccess();
//            } else {
//                throw new DownloadException(DownloadException.EXCEPTION_SERVER_SUPPORT_CODE,
//                        "UnSupported response code:" + responseCode);
//            }
            checkPause();
        }  catch (Exception e) {

        } finally {
            if (subscribe != null) {
                subscribe.unsubscribe();
            }
        }
    }

    private void checkPause() {
        if (downInfo.getState()==DownState.PAUSE) {

        }
    }

    /**
     * Download thread progress listener.
     */
    public interface DownloadProgressListener {

        void onProgress();

        void onDownloadSuccess();
    }

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public void writeCaches(ResponseBody responseBody, File file, DownInfo info) {

        try {

            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new HttpTimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpTimeException(e.getMessage());
        }
    }
}
