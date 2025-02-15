package com.app.employeeManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

	@Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {

        return args -> {
        	repository.save(new Employee("Bilbo Baggins", "bbaggings2@gmail.com"));
        	repository.save(new Employee("Frodo Baggins", "fbaggings2@gmail.com"));
        };
    }

}
