package com.league.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FootBallLeagueApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootBallLeagueApplication.class, args);
	}
	
	public SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(FootBallLeagueApplication.class);
	}

}
