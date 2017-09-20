package com.comba.android.combacommon.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.comba.android.combacommon.utils.CipherUtil;
import com.lifeofcoding.cacheutlislibrary.CacheUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by chenhailin on 2017/4/27.
 *
 * usage --https://github.com/westlinkin/CacheUtilsLibrary/blob/master/README_zhCN.md
 */

public class FileService {
        private  static  final  String TAG=FileService.class.getName();
    public static  void initFileService(Context context){
        CacheUtils.configureCache(context);//数据类对象存储
    }
    /**
     * 复制文件
     */
    public static void CopyFile(String oldPath, String newPath) throws IOException {
        int byteread = 0;
        InputStream inStream = new FileInputStream(oldPath);
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1024 * 5];
        while ((byteread = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
        }
        fs.flush();
        fs.close();
        inStream.close();
    }

    /**
     * 输出私有文件
     */
    public static boolean OutputPrivateFile(File fromFile, String toFolder) {
        if (!fromFile.exists()) {
            System.out.println("源文件不存在");
            return false;
        }
        try {
            String oFilePath = fromFile.getAbsolutePath();
            oFilePath = oFilePath.substring(oFilePath.lastIndexOf("/") + 1);
            File destDir = new File(toFolder);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            File destFile = new File(destDir.getAbsoluteFile() + "/" + oFilePath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            CopyFile(fromFile.getAbsolutePath(), destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 获取本地图片并删除
     */
    public static Bitmap getLocalPicDel(Context context, String urlStr, String imagePath, boolean isDel) {
        if (null == urlStr)
            return null;
        String fileName = CipherUtil.toMd5(urlStr.getBytes());
        Bitmap bitmap = null;
        File file = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try {
            if (isExternalStorageExist() && isFileExists(imagePath + File.separator + fileName)) {
                file = new File(imagePath + File.separator + fileName);
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
                if (isDel) {
                  delHeadFile(imagePath + File.separator + fileName);
                }
            } else {
                Toast.makeText(context,"检查SD卡是否安装",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (baos != null)
                    baos.close();
                if (bais != null)
                    bais.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 判断SD卡中文件是否存在
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isFileExists(String filePath) {
        return new File(filePath).exists();
    }


    /**
     * SD卡正确挂载并且处于可读写状态
     *
     * @return boolean
     */
    public static boolean isExternalStorageExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 下载图片,自定义存储路径
     *
     * @param urlStr
     *            图片地址
     * @param pathType
     *            1小图，2大图
     * @return Bitmap
     */
    public static Bitmap downloadPicSaveByPathType(String urlStr, int pathType, String imagePath, String bigImagePath) {
        if (null == urlStr || urlStr.length() < 8)
            return null;

        String fileName = CipherUtil.toMd5(urlStr.getBytes());
        Bitmap bitmap = null;
        File file = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;

        String pathStr = (pathType == 1 ? imagePath : bigImagePath);

        try {
            if (isExternalStorageExist() && isFileExists(pathStr + File.separator + fileName)) {
                file = new File(pathStr + File.separator + fileName);
                fis = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(fis);
            } else {
                bitmap = downloadPic(urlStr);
                if (bitmap != null) {
                    if (isExternalStorageExist()) {
                        baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        bais = new ByteArrayInputStream(baos.toByteArray());
                        writeFileToSDFromInput(pathStr, fileName, bais);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (baos != null)
                    baos.close();
                if (bais != null)
                    bais.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return bitmap;
    }



    /**
     * 传入地址，删除文件
     */
    public static void delHeadFile(String uri) {
        File f = new File(uri);
        if (f.exists()) {
            f.delete();
        }
    }


    /**
     * 保存文件到SD卡
     *
     * @param path
     * @param fileName
     * @param is
     */
    public static boolean writeFileToSDFromInput(String path, String fileName, InputStream is) {
        File file = null;
        FileOutputStream fos = null;
        try {
            file = new File(path);
            if (!file.exists())
                file.mkdirs();
            file = new File(path + File.separator + fileName);
            file.createNewFile();

            fos = new FileOutputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 下载图片资源
     */
    public static Bitmap downloadPic(String url) {
        URL _url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            _url = new URL(url);
            conn = (HttpURLConnection) _url.openConnection();
            conn.setConnectTimeout(30 * 1000);
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "【NetHelper.downloadPic.url】" + url);
        return bitmap;
    }



}












