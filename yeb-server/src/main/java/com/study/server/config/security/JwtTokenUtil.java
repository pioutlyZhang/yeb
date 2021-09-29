package com.study.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final String CLAIM_KEY_USERNAME = "sub";
    private final String CLAIM_KEY_CREATED = "created";
//    密钥
    @Value("${jwt.secret}")
    private String secret;

//    @Value("jwt.tokenHeader")
//    private String tokenHeader;

//    超时时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> map = new HashMap<>();
        map.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        map.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(map);
    }

    /**
     * 从荷载中获取username
     * @param token
     * @return
     */
    public String getUserName(String token){
        String username;

        try {
            Claims claims = getClaimsForToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;

    }

    /**
     * 从token中获取荷载
     * @param token
     * @return
     */
    private Claims getClaimsForToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

    /**
     * 根据荷载生成token
     * @param map
     * @return
     */
    private String generateToken(Map map){
        System.out.println(secret);
        System.out.println(expiration);
        return Jwts.builder()
                .setClaims(map)  //设置荷载
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(generateExpirationDate()) //设置过期时间
                .compact();
    }

    /**
     * 设置token的超时时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration*1000);
    }

    /**
     * 判断token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token,UserDetails userDetails){

        String userName = getUserName(token);
        if(!isTokenExpired(token)){
            return userDetails.getUsername().equals(userName);
        }
        return false;
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        if(getClaimsForToken(token)==null){
            return true;
        }
        Date expireToken = getTokenExpiredTime(token);
        //判断expireToken是否在当前时间前面
        if(expireToken==null){
            return false;
        }
        return expireToken.before(new Date());

    }

    /**
     * 获取token的过期时间
     * @param token
     * @return
     */
    private Date getTokenExpiredTime(String token) {
        Claims claims = getClaimsForToken(token);
        if(claims==null){
            return null;
        }
        return claims.getExpiration();
    }

    /**
     * 判断是否可以被刷新
     * @param token
     * @return
     */
    public boolean canFlashToken(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String flashToken(String token){
        Claims claims = getClaimsForToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

}
