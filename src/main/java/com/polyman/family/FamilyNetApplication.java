package com.polyman.family;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.polyman.common.base.bean.BizException;

@SpringBootApplication
public class FamilyNetApplication {

	public static void main(String[] args) throws BizException {
		ConfigurableApplicationContext context=SpringApplication.run(FamilyNetApplication.class, args);
		System.out.println("run ===============>");	
	}
}
