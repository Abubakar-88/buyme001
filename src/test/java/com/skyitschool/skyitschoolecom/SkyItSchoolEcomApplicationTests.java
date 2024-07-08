package com.skyitschool.skyitschoolecom;

import com.skyitschool.skyitschoolecom.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkyItSchoolEcomApplicationTests {
	@Autowired
	private ProductRepository repo;



	@Test
	void contextLoads() {
	}

}
