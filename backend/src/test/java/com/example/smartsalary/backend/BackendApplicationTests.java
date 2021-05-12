package com.example.smartsalary.backend;

import com.example.smartsalary.backend.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private EmployeeController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
