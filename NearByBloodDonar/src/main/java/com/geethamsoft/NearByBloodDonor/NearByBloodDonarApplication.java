package com.geethamsoft.NearByBloodDonor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NearByBloodDonarApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearByBloodDonarApplication.class, args);
	}

}
