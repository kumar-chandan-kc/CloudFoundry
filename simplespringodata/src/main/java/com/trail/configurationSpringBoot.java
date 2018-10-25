package com.trail;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

//import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


public class configurationSpringBoot {

//	@Configuration
//	@Profile("cloud")
//static class cloudconfiguration{
//		@Bean
//		public javax.sql.DataSource getDataSource() {
//				DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//				dataSourceBuilder.driverClassName("org.postgresql.Driver");
//				dataSourceBuilder.url("jdbc:postgresql://10.11.241.96:49703/postgres");
//				dataSourceBuilder.username("G2GQSQ78VBU7oJor");
//				dataSourceBuilder.password("N3zEk90yGoEuXQAg");
//				return dataSourceBuilder.build();
//			
////			CloudFactory cloudFactory = new CloudFactory();
////            Cloud cloud = cloudFactory.getCloud();
////            String serviceID = cloud.getServiceID();
////            return (DataSource) cloud.getServiceConnector(serviceID, DataSource.class, null);
//			} 
//	}
//	@Configuration
//	@Profile("cloud")
//	static class CloudDbConfig extends AbstractCloudConfig{
//	    
//	    /**
//	     * (Step 1) Parses the local environment variable VCAP_SERVICES (containing cloud information) and provides a
//	     * DataSource. The superclass {@link AbstractCloudConfig}, part of the Spring Cloud plugin, is used for this.
//	     */
//	    @Bean
//	    public DataSource dataSource() {
//	        /*
//	         * Load BasicDbcpPooledDataSourceCreator before TomcatJdbcPooledDataSourceCreator. Also see the following link
//	         * for a detailled discussion of this issue:
//	         * https://stackoverflow.com/questions/36885891/jpa-eclipselink-understanding-classloader-issues
//	         */
//	        List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
//	                "TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator",
//	                "TomcatDbcpPooledDataSourceCreator");
//	        DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
//	        return connectionFactory().dataSource(dbConfig);
//	    }
//	}
		
		@Configuration
		@Profile("default")
	static class localconfiguration extends AbstractCloudConfig{
//			@Bean
//			public javax.sql.DataSource getDataSource() {
//					DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//					dataSourceBuilder.driverClassName("org.postgresql.Driver");
//					dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
//					dataSourceBuilder.username("postgres");
//					dataSourceBuilder.password("root");
//					return dataSourceBuilder.build();
//				} 
		 
//			  @Bean
//			    public DataSource dataSource() {
//			        /*
//			         * Load BasicDbcpPooledDataSourceCreator before TomcatJdbcPooledDataSourceCreator. Also see the following link
//			         * for a detailled discussion of this issue:
//			         * https://stackoverflow.com/questions/36885891/jpa-eclipselink-understanding-classloader-issues
//			         */
//			        List<String> dataSourceNames = Arrays.asList("BasicDbcpPooledDataSourceCreator",
//			                "TomcatJdbcPooledDataSourceCreator", "HikariCpPooledDataSourceCreator",
//			                "TomcatDbcpPooledDataSourceCreator");
//			        DataSourceConfig dbConfig = new DataSourceConfig(dataSourceNames);
//			        return connectionFactory().dataSource(dbConfig);
//			    }
			  
			  @Bean
		    public DataSource inventoryDataSource() {
		        return connectionFactory().dataSource();
		   }
}
	
		
}
