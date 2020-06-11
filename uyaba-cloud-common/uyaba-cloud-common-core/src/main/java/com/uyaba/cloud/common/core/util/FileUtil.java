package com.uyaba.cloud.common.core.util;

import com.uyaba.cloud.common.core.file.FileException;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 *
 * @author noone
 * @date 2019-07-02 18:04
 */
public class FileUtil {
    /**
     * buffer SIZE
     */
    private final static int SIZE = 1024 * 10;
    /**
     * 下载（单）文件
     * @param url url
     * @param response response
     */
    public static void downloadFile(String url, HttpServletResponse response) {
        if (url != null) {
            File file = new File(url);
            String fileName = file.getName();
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    OutputStream outputStream = response.getOutputStream();
                    int i = bufferedInputStream.read(buffer);
                    while (i != -1) {
                        outputStream.write(buffer, 0, i);
                        i = bufferedInputStream.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 下载zip压缩文件
     * @param savePath 保存路径
     * @param downloadFilePaths 下载文件路径
     * @param response response
     * @param reservedZip 是否保留zip包
     */
    public static void downloadZipFile(Path savePath, String[] downloadFilePaths, HttpServletResponse response, boolean reservedZip) {
        //设置zip文件的名字
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = format.format(new Date()) + ".zip";
        //zip文件存放路径
        Path targetPath = savePath.resolve(zipFileName);
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        File zipFile = targetPath.toFile();
        try {
            //构造最终压缩包的输出流
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (String downloadFilePath : downloadFilePaths) {
                //解码获取真实路径与文件名
                String realFileName = URLDecoder.decode(downloadFilePath, "UTF-8");
                File file = new File(realFileName);
                if (file.exists()) {
                    //将需要压缩的文件 格式化成输入流
                    fileInputStream = new FileInputStream(file);
                    /*
                    压缩条目不是具体独立的文件，而是压缩文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名
                    文件名和之前的重复就会导致文件被覆盖
                     */
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    //定位改压缩条目的位置，开始写入文件到压缩包中
                    zipOutputStream.putNextEntry(zipEntry);
                    bufferedInputStream = new BufferedInputStream(fileInputStream, SIZE);
                    int read;
                    byte[] buf = new byte[1024 * 10];
                    while ((read = bufferedInputStream.read(buf, 0, SIZE)) != -1) {
                        zipOutputStream.write(buf, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
                if (null != zipOutputStream) {
                    zipOutputStream.flush();
                    zipOutputStream.close();
                }
                if (null != fileInputStream) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (zipFile.exists()) {
            downloadFile(targetPath.toString(), response);
            if(reservedZip){
              boolean isDelete =  zipFile.delete();
              if(!isDelete){
                  throw new FileException("zip文件删除失败");
              }
            }
        }
    }
}
