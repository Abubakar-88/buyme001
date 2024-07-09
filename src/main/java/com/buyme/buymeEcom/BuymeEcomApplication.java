package com.buyme.buymeEcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.skyitschool.skyitschoolecom.repository")
@EntityScan({"com.skyitschool.skyitschoolecom.entity"})
public class BuymeEcomApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuymeEcomApplication.class, args);
		System.out.println("Hello world");
	}

}
