//package com.trail;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.Cloud;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.core.env.ConfigurableEnvironment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class profileLoader {
//	
//	@Autowired
//	private ApplicationContext ctx;
//	Cloud cloud = null;
//	 @PostConstruct
//	public void profileload()
//	{	ConfigurableEnvironment appEnvironment = (ConfigurableEnvironment) ctx.getEnvironment();
//		 try{
//	CloudFactory cloudFactory = new CloudFactory();
//	 cloud = cloudFactory.getCloud();
//		 }
//		 catch(Exception e)
//		 {}
//System.out.println(ctx.toString() + "this is the application context");
//	if (cloud == null) {
//		appEnvironment.addActiveProfile("local");
//	} 
//	
////	final AnnotationConfigApplicationContext appContext =  new AnnotationConfigApplicationContext();
////	appContext.getEnvironment().setActiveProfiles( "local" );
//////	appContext.register( com.initech.ReleaserConfig.class );
////	appContext.refresh();
//	}
//
//}
