package com.udacity.CatFactsRESTfulAPI;

import com.udacity.CatFactsRESTfulAPI.entity.Cat;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class CatFactsResTfulApiApplication {

	private static final Logger log = LoggerFactory.getLogger(CatFactsResTfulApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CatFactsResTfulApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			Cat cat = restTemplate.getForObject(
					"https://cat-fact.herokuapp.com/facts/random", Cat.class);
			log.info(cat.toString());
		};
	}
}
