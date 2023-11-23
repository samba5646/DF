package com.geethamsoft.NearByProfessionals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NearByProfessionalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearByProfessionalsApplication.class, args);
	}

}
