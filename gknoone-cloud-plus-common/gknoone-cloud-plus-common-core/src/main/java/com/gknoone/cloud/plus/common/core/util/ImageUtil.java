package com.gknoone.cloud.plus.common.core.util;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * 图片工具类
 *
 * @author noone
 * @date 2019-04-08 11:28
 */
public class ImageUtil {
    /**
     * 对在线图片进行Base64位编码
     * @param imageUrl 图片URL
     * @param fileType 图片类型
     * @return Base64编码字符串
     */
    public static String encodeImage2Base64(URL imageUrl, String fileType)  {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = getByteArrayOutputStream(fileType, byteArrayOutputStream, ImageIO.read(imageUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(byteArrayOutputStream.toByteArray());
    }


    /**
     * 对本地图片进行Base64位编码
     * @param imageFile 图片位置
     * @param fileType 图片类型
     * @return Base64编码字符串
     */
    public static String encodeImage2Base64(File imageFile, String fileType){
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = getByteArrayOutputStream(fileType, byteArrayOutputStream, ImageIO.read(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * 图片转字符编码
     * @param fileType 文件类型
     * @param byteArrayOutputStream 字符编码输出流
     * @param read 读取Buffer
     * @return 图片的字符编码
     */
    private static ByteArrayOutputStream getByteArrayOutputStream(String fileType, ByteArrayOutputStream byteArrayOutputStream, BufferedImage read) {
        try {
            BufferedImage bufferedImage = read;
            byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,fileType,byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }
    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     * @param base64 base64编码的图片信息、
     */
    public static void decodeBase642Image(String base64, String path,
                                          String imgName) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decode(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
