package com.example.demo;

import com.example.demo.VO.LoginVO;
import com.example.demo.service.StudentService;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WhuIseApplication.class)
public class WhuIseApplicationTests {

	@Autowired
	StudentService studentService;

	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	@Test
	@PerfTest(invocations = 10000,threads = 10)
	public void test() {

		LoginVO loginVO = new LoginVO();
		loginVO.setLoginUsername("201901");
		loginVO.setLoginPassword("e10adc3949ba59abbe56e057f20f883e");
		System.out.println(studentService.logTry(loginVO));

	}

}
