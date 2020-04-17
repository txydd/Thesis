package com.cxq.viewer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@ServletComponentScan
@MapperScan("com.cxq.viewer.mapper")
@EnableScheduling
public class ViewerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		//SpringApplication.run(ViewerApplication.class, args);
		ConfigurableApplicationContext run = SpringApplication.run(ViewerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ViewerApplication.class);
	}




}
