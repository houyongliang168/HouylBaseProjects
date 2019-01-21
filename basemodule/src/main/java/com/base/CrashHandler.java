package com.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QunCheung on 2017/1/22.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();
    //文件价名称
    private static final String TAG = "crash";
    //文件名称
    private static final String FILE_NAME = "crashlog";
    //文件后缀
    private static final String FORMAT = ".txt";
    //存储位置文件夹
    private String logdir;
    //根据时间,定期上传日志
    public final static int TIME = 1;
    //根据日志大小,上传日志
    public final static int MEMORY = 2;
    //根据,时间,日志大小,上传日志
    public final static int TIME_AND_MEMORY = 3;
    //type
    private int TYPE = 3;
    //first init
    private boolean isFirstInit;
    //first init string
    private final static String IS_FIRST_INIT = "is_first_init";
    //first init time
    private final static String FIRST_INIT_TIME = "first_init_time";
    //时间差
    private int TIME_SIZE;
    //设置的时间间隔
    private int DAYS = 7;

    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     * @param type    上传模式
     */
    public void init(Context context, int type) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //设置文件保存路径
        logdir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + TAG;
        this.TYPE = type;
        //时间存储
        saveOrGetTimeType();
    }

    /**
     * 初始化
     *
     * @param context 默认按照日志大小,控制日志上传
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        //设置文件保存路径
        logdir = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "crash_log";
        this.TYPE = 3;
        //时间存储
        saveOrGetTimeType();
    }

    /**
     * 获取保存时间信息
     */
    private void saveOrGetTimeType() {
        SharedPreferences crash = mContext.getSharedPreferences("crash", 1);
        isFirstInit = crash.getBoolean(IS_FIRST_INIT, true);
        if (isFirstInit) {
            SharedPreferences.Editor edit = crash.edit();
            edit.putBoolean(IS_FIRST_INIT, false);
            edit.putLong(FIRST_INIT_TIME, getTimeToLong());
            edit.commit();
        } else {
            long firstTime = crash.getLong(FIRST_INIT_TIME, 0);
            long currentTime = getTimeToLong();
            TIME_SIZE = (int) ((currentTime - firstTime) / 86400);
        }

    }

    /**
     * 获取时间
     */
    private long getTimeToLong() {
        try {
            long time = Calendar.getInstance().getTimeInMillis();
            return time;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.i(TAG, "uncaughtException: " + e.toString());
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "出错了~~~", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //收集设备参数信息
        collectDeviceInfo(mContext);

        //保存日志文件
        saveCrashInfoToFile(ex);

        return true;
    }

    /**
     * 并不应该每次崩溃都进行日志上传
     *
     * @param file
     */
    private void svaeCrashInfoToServer(File file) {
        // TODO: 2017/1/22 文本大小过大,必须上传
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "日志已经很大了,应该上传服务器", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.i(TAG, "collectDeviceInfo: " + e.toString());
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.i(TAG, "collectDeviceInfo: " + e.toString());
            }
        }
    }

    /**
     * 保存错误信息到文件中,应该清楚过长时间的错误信息
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfoToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String times = sdf.format(date);
        sb.append("---------------------sta--------------------------");
        sb.append("crash at time: " + times);
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        MyLog.wtf("houyl","bug result : "+result);
        //可以追加用户信息
        sb.append(addUserInfo());
        sb.append("--------------------end---------------------------");
        MyLog.wtf("houyl","Error: "+sb.toString());
        File file = getFilePath(logdir, FILE_NAME + FORMAT);
        switch (TYPE) {
            case 1:

            case 2:
                if (checkIsTimeToPush()) {
                    //保存日志到服务器
                    svaeCrashInfoToServer(file);
                }
            case 3:
                //检查日志是否过大
                if (checkFileIsToBig(file)) {
                    //保存日志到服务器
                    svaeCrashInfoToServer(file);
                    //写入本地,清空文本
                    // TODO: 2017/2/27 如果写本地则打开注释
                     WriteContentTypt(file,sb,true);
                } else {
                    //写入本地,追加文本
                      WriteContentTypt(file,sb,false);
                }
                break;
            default:
                break;
        }
        //放开以上注释,请删除此行
//        Log.i(TAG, "WriteContentTypt: " + sb.toString());
        return null;
    }

    /**
     * 按照时间周期上传日志
     */
    private boolean checkIsTimeToPush() {
        if (TIME_SIZE >= DAYS) {
            return true;
        } else {
            return false;
        }
    }

    private String addUserInfo() {
        return "UserInfo:\n";
    }

    private boolean checkFileIsToBig(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int fileSize = fis.available();
            if (fileSize > 1024 * 1024 * 5) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @return
     */
    public static File getFilePath(String filePath,
                                   String fileName) {
        File file = new File(filePath, fileName);
        if (file.exists()) {
            return file;
        } else {
            file = null;
            makeRootDirectory(filePath);
            try {
                file = new File(filePath + File.separator + fileName);
            } catch (Exception e) {
                //  Auto-generated catch block
                e.printStackTrace();
            }
            return file;
        }
    }

    /**
     * 创建根目录
     *
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 写入本地文件
     *
     * @param file    文件
     * @param sb      内容
     * @param isClean 是否清空,true:清空,false:保留
     */
    public void WriteContentTypt(File file, StringBuffer sb, boolean isClean) {
        try {
            FileOutputStream fos = new FileOutputStream(file, !isClean);
            fos.flush();
            for (char c : sb.toString().toCharArray()) {
                fos.write(c);
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "WriteContentTypt: " + sb.toString());
    }
}
