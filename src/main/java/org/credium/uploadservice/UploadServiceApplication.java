package org.credium.uploadservice;

import org.credium.uploadservice.service.CacheService;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
@EnableAsync
@EnableScheduling
public class UploadServiceApplication {

	private final CacheService cacheService;

	public UploadServiceApplication(final CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public static void main(String[] args) {
		SpringApplication.run(UploadServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		this.cacheService.loadProjections();
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getDeclaredType().getName());
	}

}

