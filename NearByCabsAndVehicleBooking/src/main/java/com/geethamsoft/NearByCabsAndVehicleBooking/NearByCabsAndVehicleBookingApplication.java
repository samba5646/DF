package com.geethamsoft.NearByCabsAndVehicleBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NearByCabsAndVehicleBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearByCabsAndVehicleBookingApplication.class, args);
	}

}
