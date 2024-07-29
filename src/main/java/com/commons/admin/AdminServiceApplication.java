package com.commons.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Import(DataSourceConfig.class)
public class AdminServiceApplication {

	public static void main(String[] args) {
		System.out.println("Java version: " + System.getProperty("java.version"));
		SpringApplication.run(AdminServiceApplication.class, args);
	}
	
	
//	@Autowired
//    private DataSourceConfig dataSourceConfig;
// 
// @Bean
// public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
//        DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("src/main/resources/sql-script.sql")));
//        return initializer;
// }
// 
// @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
//        dataSource.setUrl(dataSourceConfig.getUrl());
//        dataSource.setUsername(dataSourceConfig.getUsername());
//        dataSource.setPassword(dataSourceConfig.getPassword());
//        return dataSource;
//    }

}
