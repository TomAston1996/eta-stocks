package com.tomaston.etastocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EtaStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtaStocksApplication.class, args);
	}

}
