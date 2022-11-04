package com.lilblue.demo;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
// import com.lilblue.demo.services.*; 

@SpringBootApplication
public class DemoApplication
		implements CommandLineRunner, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	// @Autowired
	// private PlayerService service;

	// @Autowired
	// private ReactiveMongoTemplate mongoTemplate;

	// private static final Logger log =
	// LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * mongoTemplate.dropCollection("player").subscribe();
		 * 
		 * 
		 * Flux.just(new Player("Christiam", "Navarro"),
		 * new Player("Erick", "Caballero")).flatMap(player -> {
		 * player.setBirthDate(new Date());
		 * return service.save(player);
		 * })
		 * .subscribe(player -> log.info("Insert: " + player.getId() + " " +
		 * player.getName()));
		 */
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		if (System.getenv("PORT") != null) {
			factory.setPort(Integer.valueOf(System.getenv("PORT")));
		}
	}

}
