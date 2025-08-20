package com.lucius.bitgain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BitGainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitGainApplication.class, args);
    }

}
