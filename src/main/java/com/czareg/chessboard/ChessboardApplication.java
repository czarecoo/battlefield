package com.czareg.chessboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ChessboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChessboardApplication.class, args);
	}

}
