package com.lilblue.demo;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class DemoApplication
		implements CommandLineRunner, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

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

	@Bean
	public GroupedOpenApi employeesOpenApi(@Value("${springdoc.version}") String appVersion) {
		String[] paths = { "/api/player/**" };
		return GroupedOpenApi.builder().group("player")
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Player API").version(appVersion)))
				.pathsToMatch(paths)
				.build();
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		if (System.getenv("PORT") != null) {
			factory.setPort(Integer.valueOf(System.getenv("PORT")));
		}
	}

}
