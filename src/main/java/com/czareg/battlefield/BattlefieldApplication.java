package com.czareg.battlefield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BattlefieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlefieldApplication.class, args);
	}

}
