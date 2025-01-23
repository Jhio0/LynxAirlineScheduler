package com.example.Lynx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;
@ComponentScan(basePackages = "com.example.Lynx.Controller")
@ComponentScan(basePackages = "com.example.Lynx.Service")
@ComponentScan(basePackages = "com.example.Lynx.Entity")
@ComponentScan(basePackages = "com.example.Lynx.Repository")
@ComponentScan(basePackages = "com.example.Lynx.DTO")
@SpringBootApplication
public class LynxApplication {

	public LynxApplication(){}

	public static void main(final String[] args) {
		SpringApplication.run(LynxApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8081")
						.allowedOrigins("exp://192.168.1.109:8081")
						.allowedOrigins("http://192.168.1.109:8081")
						.allowedOrigins("http://192.168.1.13:8081")
						.allowedOrigins("exp://192.168.1.13:8081")
						.allowedMethods("*")
						.allowedHeaders("*");
			}
		};
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("GithubLookup-");
		executor.initialize();
		return executor;
	}

}
