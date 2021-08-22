package com.league.football.services;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.league.football.beans.Country;
import com.league.football.beans.Leagues;
import com.league.football.beans.Standings;
import com.league.football.beans.TeamToDo;

public class FootBallLeagueServiceImpl implements FootBallLeagueService{
	
	private static final Logger logger = LoggerFactory.getLogger(FootBallLeagueServiceImpl.class);
	@Autowired RestTemplate rt = new RestTemplate();
	@Nullable HttpEntity<String> httpEntity;
	private String apiKey;
	private String baseUrl;
	public FootBallLeagueServiceImpl(String baseRestUrl, String apiKeyCred)
	{
		baseUrl=baseRestUrl;
		apiKey=apiKeyCred.strip();
		try {
			String auth = apiKey+":";
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(ENCODE_UTF_STRING));
			String authHeader = "Basic " + new String(encodedAuth);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Accept", "application/json");
			headers.add("Authorization", authHeader);
			headers.add("APIkey", apiKey);
			httpEntity = new HttpEntity<String>(headers);
			logger.info("Inside FootBallLeagueServiceImpl Constructor, Header Set sucessfully");
			logger.info("Hi Manish, How are you?");
			logger.debug("Avyukt and Neo are good friends");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("FootBallLeagueServiceImpl Constructor throwing Exception"
			+e.getMessage());
		}
	}
	
	public String getTeamStatus(String countryName, String leagueName, String teamName)
	{
		String countries = EMPTY_STRING;
		StringBuffer response=new StringBuffer();
		String countryid=EMPTY_STRING;
		String leaguesString=EMPTY_STRING;
		String stringStandings=EMPTY_STRING;
			try {
			countries=getCountry();	
			Type listTypeCountry = new TypeToken<ArrayList<Country>>(){ }.getType();
			List<Country> countryList = new Gson().fromJson(countries,
					listTypeCountry);
			 
			List<Country> matchingCountry=countryList.stream()
			.filter((country)->country.getCountry_name().equals(countryName))
			.collect(Collectors.toList());
			if(matchingCountry.size()>0 && matchingCountry!=null)
			{
				countryid=matchingCountry.get(0).getCountry_id();
				response.append("Country ID>>"+countryid+"\t"+"Country Name>>"+countryName+"\n");
			}
			else
			{
				response.append("Country Name>>"+countryName+" not found in the list of Football League Database\n");
				return response.toString();
			}
	
			leaguesString=getLeagues(countryid);
			Type listTypeLeagues = new TypeToken<ArrayList<Leagues>>(){
				
			}.getType();
			List<Leagues> leaguesList = new Gson().fromJson(leaguesString, listTypeLeagues);
			List<Leagues> matchingLeague=leaguesList.stream()
					.filter((leagues)->leagues.getLeague_name().equals(leagueName))
					.collect(Collectors.toList());
			String leagueId=EMPTY_STRING;
			if(matchingLeague.size()>0 && matchingLeague!=null)
			{
				leagueId=matchingLeague.get(0).getLeague_id();
				response.append("League ID>>"+leagueId+"\t"+"League Name>>"+leagueName+"\n");
	
			}
			else
			{
				response.append("Country>>"+countryName+" not participated in the League>>"+leagueName+"\n");
				return response.toString();
			}
			stringStandings=getStandings(leagueId);
			Type listTypestandings = new TypeToken<ArrayList<Standings>>(){
				
			}.getType();
			List<Standings> standingList = new Gson().fromJson(stringStandings, listTypestandings);
			List<Standings> matchingStandings = standingList.stream()
					.filter((standings) -> leagueName.equals(standings.getLeague_name())
							&& countryName.equals(standings.getCountry_name())
							&& teamName.equals(standings.getTeam_name()))
					.collect(Collectors.toList());
			if(matchingStandings.size()>0 && matchingStandings!=null)
			{
				List<TeamToDo> teamtodo = matchingStandings.stream()
				.map(standings->{
					TeamToDo todo = new TeamToDo(standings.getTeam_id()
						,standings.getTeam_name()
						,standings.getOverall_league_position());
					return todo;
						}).collect(Collectors.toList());
				String teamStatus = teamtodo.stream()
						.map((teamToDo) -> teamToDo.toString())
						.collect(Collectors.joining());
				response.append(teamStatus);
				return response.toString();
			}
			else
			{
				response.append("No Team Played in this League with Name>>"+teamName);
				return response.toString();
			}
		}catch(Exception e)
		{
			logger.error("Error in the getTeamStatus() method of FootBallLeagueServiceImpl>>"
					+e.getMessage());
			if(!EMPTY_STRING.equals(response.toString()))
				return response.append("Error in the getTeamStatus() method of FootBallLeagueServiceImpl>>").toString();
			else
				return response.append("Error in the getTeamStatus() method of FootBallLeagueServiceImpl>>").toString();
		}
	}
	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		String finalApiUrl = baseUrl+FootBallLeagueService.COUNTRY_STRING
				+FootBallLeagueService.APIKEY_STRING+apiKey;
		URI url;
		String countries = new String();
		try {
			url=new URI(finalApiUrl);
			ResponseEntity<String> entity=rt.exchange(url, HttpMethod.POST, httpEntity, String.class);
			if(entity.getStatusCodeValue()!=200)
			{
				logger.error("getCountry Method of FootBallLeagueServiceImpl failed to get Country List::"
						+entity.getStatusCode().getReasonPhrase());
				countries=entity.getBody();
				return countries;
			}
			else
			{
				
				countries=entity.getBody();
				return countries;
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error("Error in the getCountry() method of FootBallLeagueServiceImpl>>"
					+e.getMessage());
		}
		return countries;
	}

	@Override
	public String getLeagues(String country_id) {
		// TODO Auto-generated method stub
		String finalApiUrl = baseUrl+FootBallLeagueService.LEAGUE_STRING+country_id
				+FootBallLeagueService.APIKEY_STRING+apiKey;
		URI url;
		String stringLeagues = EMPTY_STRING;
		try {
			url=new URI(finalApiUrl);
			ResponseEntity<String> entity=rt.getForEntity(url, String.class);
			if(entity.getStatusCodeValue()!=200)
			{
				logger.error("getLeague Method of FootBallLeagueServiceImpl failed to get League List::"
						+entity.getStatusCode().getReasonPhrase());
				stringLeagues=entity.getBody();
				return stringLeagues;
			}
			else
			{
				
				stringLeagues=entity.getBody();
				return stringLeagues;
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error("Error in the getLeagues() method of FootBallLeagueServiceImpl>>"
					+e.getMessage());
		}
		return stringLeagues;
	}

	@Override
	public String getStandings(String league_id) {
		// TODO Auto-generated method stub
		String finalApiUrl = baseUrl+FootBallLeagueService.STANDINGS_STRING+league_id
				+FootBallLeagueService.APIKEY_STRING+apiKey;
		URI url;
		String stringStandings = EMPTY_STRING;
		try {
			url=new URI(finalApiUrl);
			ResponseEntity<String> entity=rt.getForEntity(url, String.class);
			if(entity.getStatusCodeValue()!=200)
			{
				logger.error("getStandings Method of FootBallLeagueServiceImpl failed to get Standing List::"
						+entity.getStatusCode().getReasonPhrase());
				stringStandings=entity.getBody();
				return stringStandings;
			}
			else
			{
				
				stringStandings=entity.getBody();
				return stringStandings;
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error("Error in the getStandings() method of FootBallLeagueServiceImpl>>"
					+e.getMessage());
		}
		return stringStandings;
	}

}
