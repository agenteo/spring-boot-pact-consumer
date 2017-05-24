package com.example.springBootPactConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootPactConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootPactConsumerApplication.class, args);
	}

	@Bean
	public SealOffersConfigurationProperties sealOffersConfigurationProperties(){
		return new SealOffersConfigurationProperties();
	}
}
