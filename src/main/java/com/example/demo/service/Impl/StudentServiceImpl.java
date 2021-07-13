package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.LoginVO;
import com.example.demo.VO.StudentVO;
import com.example.demo.service.StuCouService;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.*;
import com.example.demo.mapper.*;
import com.example.demo.entity.*;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * @author 12mango
 * @since 2021/4/5
 */
@Service("StudentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper){
        this.studentMapper=studentMapper;
    }

    public StudentVO getStuInfo(String sid){
        Map<String, String> SEX = new HashMap<String,String>();
        SEX.put("0", "女");
        SEX.put("1", "男");
        Student tmp = studentMapper.selectOne(Wrappers .<Student>lambdaQuery().eq(Student::getSid, sid));
        return new StudentVO().setSid(tmp.getSid())
                .setName(tmp.getName())
                .setSex(SEX.get(tmp.getSex()))
                .setSchool(tmp.getSchool())
                .setMajor(tmp.getMajor())
                .setPhone(tmp.getPhone())
                .setPassword(tmp.getPassword())
                .setEmail(tmp.getEmail());
    }

    public String logTry(LoginVO loginField){
        Student tmp = studentMapper.selectOne(Wrappers .<Student>lambdaQuery().eq(Student::getSid, loginField.getLoginUsername()).eq(Student::getPassword,loginField.getLoginPassword()));
        //Student tmp = studentMapper.selectOne(Wrappers .<Student>lambdaQuery().eq(Student::getSid, loginField.getLoginUsername()));
        //System.out.println(tmp.getPassword());
        //System.out.println(loginField.getLoginPassword());
        if(tmp==null){
            return "error";
        }
        else{
            return "ok";
        }
    }


    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT = "80";//或"80"

    public boolean verifyCode(String sid,String email,String code){
        Jedis jedis = new Jedis("localhost",6379);

        String trueCode = jedis.get(sid);
        if(trueCode.equals(code)){
            System.out.println("success");
            Student stu = new Student();
            stu.setEmail(email);
            studentMapper.update(stu,Wrappers .<Student>lambdaQuery().eq(Student::getSid,sid));
            return true;
        }
        return false;
    }

    public boolean sendCode(String sid,String email){

        final Properties props = new Properties();

        Jedis jedis = new Jedis("localhost",6379);
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String code = str.toString();
        jedis.set(sid, code);
        jedis.expire(sid, 1200);
        jedis.close();
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
        props.put("mail.smtp.port", ALIDM_SMTP_PORT);

        // 发件人的账号，填写控制台配置的发信地址,比如xxx@xxx.com
        props.put("mail.user", "12mango@albatross.herowharf.cn");
        // 访问SMTP服务时需要提供的密码(在控制台选择发信地址进行设置)
        props.put("mail.password", "SMtp707771838");

        Session mailSession = Session.getInstance(props, authenticator);

//        mailSession.setDebug(true);
        //UUID uuid = UUID.randomUUID();
        //final String messageIDValue = "<" + uuid.toString() + ">";
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession){
            //@Override
            //protected void updateMessageID() throws MessagingException {
            //设置自定义Message-ID值
            //setHeader("Message-ID", messageIDValue);
            //}
        };
        try {
            // 设置发件人邮件地址和名称。填写控制台配置的发信地址,比如xxx@xxx.com。和上面的mail.user保持一致。名称用户可以自定义填写。
            InternetAddress from = new InternetAddress("12mango@albatross.herowharf.cn", "芒果");
            // 设置邮件标题
            message.setSubject("假装是武汉大学教务系统");
            // 设置邮件的内容体
            message.setContent("实际上是我数据库的作业，验证码是："+code+" 有效期20分钟", "text/html;charset=UTF-8");

            // 设置收件人邮件地址，比如yyy@yyy.com
            InternetAddress to = new InternetAddress(email);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            message.setFrom(from);
            Transport.send(message);
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
            return false;
        }
        return true;
    }
}
