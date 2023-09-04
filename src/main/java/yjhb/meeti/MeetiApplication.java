package yjhb.meeti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MeetiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetiApplication.class, args);
	}

}
