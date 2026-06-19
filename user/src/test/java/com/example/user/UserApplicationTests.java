package com.example.user;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(ZoneId.systemDefault());
	}

}
