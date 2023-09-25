package com.geethamsoft.NearByBusinessOpportunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NearByBusinessOpportunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearByBusinessOpportunityApplication.class, args);
	}

}
