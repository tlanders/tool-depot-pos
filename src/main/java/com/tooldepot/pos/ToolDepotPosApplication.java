package com.tooldepot.pos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class ToolDepotPosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolDepotPosApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeEntities() {
        return args -> log.debug("Initializing entities...");
    }
}
