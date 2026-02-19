package it.unisalento.music_virus_project.event_fundraising_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventFundraisingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventFundraisingServiceApplication.class, args);
	}
}
