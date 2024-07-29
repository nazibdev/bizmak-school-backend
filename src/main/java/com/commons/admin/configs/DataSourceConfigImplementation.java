package com.commons.admin.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

//@Import(DataSourceConfig.class)
public class DataSourceConfigImplementation {

//	 	@Autowired
//	    private DataSourceConfig dataSourceConfig;
//	 
//		 @Bean
//		 public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//		        DataSourceInitializer initializer = new DataSourceInitializer();
//		        initializer.setDataSource(dataSource);
//		        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("sql-script.sql")));
//		        return initializer;
//		 }
//	 
//	 	@Bean
//	    public DataSource dataSource() {
//	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	        dataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
//	        dataSource.setUrl(dataSourceConfig.getUrl());
//	        dataSource.setUsername(dataSourceConfig.getUsername());
//	        dataSource.setPassword(dataSourceConfig.getPassword());
//	        return dataSource;
//	    }
}
