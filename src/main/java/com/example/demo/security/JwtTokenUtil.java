package com.example.demo.security;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.VO.LoginVO;
//import jdk.internal.access.JavaNetUriAccess;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Map;

/**
 * @author 12mango
 * @since 2021/4/7
 */
@Component
public class JwtTokenUtil {

    // 用于JWT进行签名加密的秘钥
    private static String SECRET = "@!#%27832%$^54";

    public String getToken(LoginVO user,String userType) {
        return JWT.create().withAudience(user.getLoginUsername()).withSubject(userType)
                .sign(Algorithm.HMAC256(SECRET));
    }

}
