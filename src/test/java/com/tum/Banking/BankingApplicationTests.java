package com.tum.Banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@SpringBootTest
class BankingApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Test
	void contextLoads() {

	}

}
