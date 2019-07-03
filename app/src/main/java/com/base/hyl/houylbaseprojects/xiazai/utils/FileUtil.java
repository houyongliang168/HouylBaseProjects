/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.base.hyl.houylbaseprojects.xiazai.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context contex) {
        File file = new File(Environment.getExternalStorageDirectory(), "pic.jpg");
        return file;
    }

    public static File getSaveFile(Context contex, String name) {
        File file = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
        return file;
    }


    public static File getSaveOtherFile(Context contex, String name) {
        String mFilePath = "/storage/emulated/0/com.hyl/" ;

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (Environment.getExternalStorageDirectory().getPath() != "") {
                mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "com.taikanglife.isalessystem"+ File.separatorChar  ;
            }
        } else {
            mFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar+"com.taikanglife.isalessystem"+ File.separatorChar ;
        }

//        File folder = Environment.getExternalStoragePublicDirectory(mFilePath);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
//        File file = new File(mFilePath, name + ".loan");//每次写入会覆盖旧数据
        File file = getFilePath(mFilePath, name + ".loan");//每次写入会覆盖旧数据

        return file;
    }


    /**
     * //TODO:上传成功后，删除文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        }
    }

    /**
     * //TODO:上传成功后，删除文件及文件夹
     *
     * @param pPath 文件目录
     */
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteFile(dir);
    }

/*删除文件的 其他方法*/

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param filePath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
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
                file.mkdirs();
            }
        } catch (Exception e) {

        }
    }




}
