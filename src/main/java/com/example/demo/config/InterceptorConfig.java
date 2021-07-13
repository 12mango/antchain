package com.example.demo.config;

import com.example.demo.handler.AuthenticationInterceptor;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 12mango
 * @since 2021/4/7
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    public InterceptorConfig(StudentService studentService,StudentMapper studentMapper){
        this.studentService=studentService;
        this.studentMapper=studentMapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
    @Autowired
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(studentService,studentMapper);
    }
}