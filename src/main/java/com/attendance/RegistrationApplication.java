package com.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@EnableOAuth2Sso
@SpringBootApplication
public class RegistrationApplication{

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

}
