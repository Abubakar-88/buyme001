package com.buyme.buymeEcom;

import com.buyme.buymeEcom.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkyItSchoolEcomApplicationTests {
	@Autowired
	private ProductRepository repo;



	@Test
	void contextLoads() {
	}

}
