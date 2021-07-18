package com.example.demo.handler;

import com.auth0.jwt.*;
import com.example.demo.common.annotations.PassToken;
import com.example.demo.common.annotations.TeacherOnly;
import com.example.demo.common.annotations.UserLoginToken;
import com.example.demo.exception.AuthException;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 12mango
 * @since 2021/4/7
 */

@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static String SECRET = "@!#%27832%$^54";

    @Autowired
    public AuthenticationInterceptor(){
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new CustomException("无token，需要登录", ExceptionCode.A0301); //用户未登录
                }
                Jedis jedis = new Jedis("localhost",6379);

                if(jedis.get(token) == null){
                    jedis.close();
                    throw new AuthException("token无效或已过期", ExceptionCode.A0220); //登录已过期
                }
                System.out.println(JWT.decode(token).getSubject().equals("user"));
                //if(!JWT.decode(token).getSubject().equals("student")){
                if(!JWT.decode(token).getSubject().equals("user")){
                    jedis.close();
                    throw new CustomException("无权限查看该页面", ExceptionCode.A0300); //无权限
                }
                jedis.close();
                return true;
            }
        }

        if (method.isAnnotationPresent(TeacherOnly.class)) {
            TeacherOnly teacherOnly = method.getAnnotation(TeacherOnly.class);
            if (teacherOnly.required()) {
                // 执行认证
                if (token == null) {
                    throw new CustomException("无token，需要登录", ExceptionCode.A0301); //用户未登录
                }
                Jedis jedis = new Jedis("localhost",6379);

                if(jedis.get(token) == null){
                    jedis.close();
                    throw new AuthException("token无效或已过期", ExceptionCode.A0220); //登录已过期
                }
                if(!JWT.decode(token).getSubject().equals("admin")){
                    jedis.close();
                    throw new CustomException("无权限查看该页面", ExceptionCode.A0300); //无权限
                }
                jedis.close();
                return true;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

}