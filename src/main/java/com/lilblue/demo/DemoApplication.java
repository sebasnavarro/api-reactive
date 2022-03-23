package com.lilblue.demo;

import java.util.Date;
import com.lilblue.demo.documents.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import com.lilblue.demo.services.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private PlayerService service;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("player").subscribe();

		Flux.just(new Player("Christiam", "Navarro"),
				new Player("Erick", "Caballero")).flatMap(player -> {
					player.setBirthDate(new Date());
					return service.save(player);
				})
				.subscribe(player -> log.info("Insert: " + player.getId() + " " + player.getName()));
	}
}
