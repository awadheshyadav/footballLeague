package com.league.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.league.football.beans.LeagueStatus;
import com.league.football.services.FootBallLeagueServiceImpl;

@RestController
@Configuration
@PropertySource(ignoreResourceNotFound=true, value="classpath:application.properties")
public class FootBallLeagueApplicationController {
	@Autowired
	Environment Env;
	private static final Logger logger = LoggerFactory.getLogger(FootBallLeagueApplicationController.class);
	StringBuffer requestValidation=new StringBuffer(Constants.EMPTY_STRING);
	@RequestMapping(value="/leagueStatus", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public String leagueStatus(@RequestBody LeagueStatus request)
	{
		String response = Constants.EMPTY_STRING;
		response = _requestValidation(request);
		if(!Constants.EMPTY_STRING.equals(response))
			return response;
		
		{
			String token = Env.getProperty(Constants.TOKEN_STRING);
			String baseUrl=Env.getProperty(Constants.baseUrl_STRING);
			FootBallLeagueServiceImpl serviceImpl= new FootBallLeagueServiceImpl(baseUrl,token);
			response=serviceImpl.getTeamStatus(request.getCountry_name()
					,request.getLeague_name(), request.getTeam_name());
			return response;
		}
	}

	private String _requestValidation(LeagueStatus request) {
		// TODO Auto-generated method stub
		if(request.getCountry_name()!=null 
				&& !Constants.EMPTY_STRING.equals(request.getCountry_name()))
			logger.info("Value of Country Name::"+request.getCountry_name());
		else
			requestValidation.append(Constants.VALIDATION_STRING+"\n"+Constants.COUNTRY_STRING);
		if(request.getLeague_name()!=null 
				&& !Constants.EMPTY_STRING.equals(request.getLeague_name()))
			logger.info("Value of League Name::"+request.getLeague_name());
		else
		{
			if(Constants.EMPTY_STRING.equals(requestValidation.toString()))
				requestValidation.append(Constants.VALIDATION_STRING+"\n"+Constants.LEAGUE_STRING);
			else
				requestValidation.append("\n"+Constants.LEAGUE_STRING);
		}
		if(request.getTeam_name()!=null 
				&& !Constants.EMPTY_STRING.equals(request.getTeam_name()))
			logger.info("Value of Team Name::"+request.getTeam_name());
		else
		{
			if(Constants.EMPTY_STRING.equals(requestValidation.toString()))
				requestValidation.append(Constants.VALIDATION_STRING+"\n"+Constants.TEAM_STRING);
			else
				requestValidation.append("\n"+Constants.TEAM_STRING);
		}
		return requestValidation.toString();
	}
	
}
