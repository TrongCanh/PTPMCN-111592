package com.sapo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class SapoSpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SapoSpaApplication.class, args);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		return scheduler;
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
