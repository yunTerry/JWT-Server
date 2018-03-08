package com.terry.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Calendar;
import java.util.Date;

/***
 * *
 * 名称：     Util
 * 作者：     Terry
 * 创建时间：  on 2018/1/30.
 * 说明：     
 * *
 ***/

public class Util {


    public static String key = "123d@$";


    public static String getToken(String id) {
        try {
            return Jwts.builder()
                    .claim("role", "admin")
                    .setSubject(id)
                    //token有效期3天
                    .setExpiration(getExdate(3))
                    .signWith(SignatureAlgorithm.HS512, Util.key)
                    .compact();
        } catch (Exception exception) {
            return null;
        }
    }

    private static Date getExdate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static boolean verify(String token) {
        if (token == null) {
            throw new JwtException("token lost");
        }
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    public static String getUid(String token) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (SignatureException e) {
            return "";
        }
    }

}
