package com.geethamsoft.NearByBuyAndSellProducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

public class NearByBuyAndSellProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearByBuyAndSellProductsApplication.class, args);
	}

}
