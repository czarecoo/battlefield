package com.czareg.battlefield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableTransactionManagement
public class BattlefieldApplication {

    public static void main(String[] args) {
        Locale.setDefault(ENGLISH);
        SpringApplication.run(BattlefieldApplication.class, args);
    }
}
