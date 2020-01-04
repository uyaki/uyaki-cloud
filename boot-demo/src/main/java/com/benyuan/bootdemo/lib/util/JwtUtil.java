package com.benyuan.bootdemo.lib.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * jwt工具类
 */
public class JwtUtil {
    private static final String LEXICAL_BINARY = "bbyy2019bbyy2019bbyy2019bbyy2019bbyy2019bbyy2019";

    /**
     * 创建jwt
     *
     * @param subject 用户名
     * @return jwt
     */
    public static String createJWT(String subject) {
        return createJWT(subject, 60000000);
    }

    /**
     * 创建jwt
     *
     * @param subject   用户名
     * @param ttlMillis 超时时间
     * @return jwt
     */
    public static String createJWT(String subject, long ttlMillis) {
        //指定签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成服务端私钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(LEXICAL_BINARY);
        Key key = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                //设置唯一标识
                .setId(UUID.randomUUID().toString())
                //设置签发时间
                .setIssuedAt(now)
                //设置主体,即签发人
                .setSubject(subject)
                //设置签名秘钥和签名算法
                .signWith(key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 校验jwt
     *
     * @param jwt jwt
     * @return 校验结果
     */
    public static boolean checkJwt(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(LEXICAL_BINARY))
                    .parseClaimsJws(jwt).getBody();
            return true;
        } catch (Exception e) {
            //ExpiredJwtException
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取jwt对应的用户名
     *
     * @param jwt jwt
     * @return username
     */
    public static String getUserNameByJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(LEXICAL_BINARY))
                    .parseClaimsJws(jwt).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            //ExpiredJwtException
            throw e;
        }
    }


    /**
     * 获取jwt对应的用户id
     *
     * @param jwt jwt
     * @return 用户id
     */
    public static Long getUserByIdJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(LEXICAL_BINARY))
                    .parseClaimsJws(jwt).getBody();
            return Long.parseLong(claims.getId());
        } catch (Exception e) {
            //ExpiredJwtException
            throw e;
        }
    }


}
