package com.commons.admin.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * This class was created using below link - 
 * https://www.bezkoder.com/spring-boot-swagger-3/
 */


@Configuration
public class SwaggerConfiguration {

	@Value("${bcamp.openapi.dev-url}")
	  private String devUrl;

	  @Value("${bcamp.openapi.prod-url}")
	  private String prodUrl;

	  @Bean
	  public OpenAPI myOpenAPI() {
	    Server devServer = new Server();
	    devServer.setUrl(devUrl);
	    devServer.setDescription("Server URL in Development environment");

	    Server prodServer = new Server();
	    prodServer.setUrl(prodUrl);
	    prodServer.setDescription("Server URL in Production environment");

	    Contact contact = new Contact();
	    contact.setEmail("bcamp@gmail.com");
	    contact.setName("BCAMP IT SOLUTIONS");
	    contact.setUrl("https://www.BCAMP.com");

	    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

	    Info info = new Info()
	        .title("BCAMP API")
	        .version("1.0")
	        .contact(contact)
	        .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.BCAMP.com/terms")
	        .license(mitLicense);

//	    List<String> servers = new ArrayList<>();
//	    , prodServer
//	    return new OpenAPI().info(info).servers(List.of(devServer));
	    return new OpenAPI().info(info).servers(Arrays.asList(devServer));
	  }
    
}
