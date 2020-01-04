package com.benyuan.bootdemo.lib.util;

import org.springframework.util.DigestUtils;

import java.util.Objects;

/**
 * MD5加密工具类
 */
public class Md5Util {
    private static final String MD5_SALT = "bbyy2019";

    /**
     * 获取MD5十六进制加密密文
     *
     * @param password 密码明文
     * @return 密文
     */
    public static String encodeHex(String password) {
        password += MD5_SALT;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 验证MD5十六进制加密密文
     *
     * @param ciphertext 密文
     * @param password   明文
     * @return 是否通过验证
     */
    public static boolean verifyHex(String ciphertext, String password) {
        password += MD5_SALT;
        return Objects.equals(ciphertext, DigestUtils.md5DigestAsHex(password.getBytes()));
    }
}
