package com.trail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.Cloud;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.CloudFactory;



@SpringBootApplication

public class DemoApplication extends SpringBootServletInitializer {

//	@Autowired
//	static profileLoader loader;
	public static void main(String[] args) {
		Cloud cloud;
		try{
		CloudFactory cloudFactory = new CloudFactory();
		 cloud = cloudFactory.getCloud();
		}
		catch(Exception e)
		{cloud = null;}
		
		if(cloud!=null)
		{
		System.setProperty("spring.profiles.active", "cloud");
		}
		else
		{
			System.setProperty("spring.profiles.active", "local");	
		}
		SpringApplication.run(DemoApplication.class, args);
//				loader.profileload();
//				System.out.println("reached");
//		
	}
	
}
