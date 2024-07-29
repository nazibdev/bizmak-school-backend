package com.commons.admin.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ApplicationEnvironmentConfigs {
	
	 	@Bean
	    @Profile("dev")
	    public String devBean() {
	        System.out.println("Development Bean created");
	        return "Development Bean";
	    }
	 	
	 	@Bean
	    @Profile("test")
	    public String testBean() {
	        System.out.println("Production Bean created");
	        return "Production Bean";
	    }

	    @Bean
	    @Profile("prod")
	    public String prodBean() {
	        System.out.println("Production Bean created");
	        return "Production Bean";
	    }

}
