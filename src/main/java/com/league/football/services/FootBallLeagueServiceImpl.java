package com.league.football.services;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			httpEntity = new HttpEntity<String>(headers);
			logger.info("Inside FootBallLeagueServiceImpl Constructor, Header Set sucessfully");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("FootBallLeagueServiceImpl Constructor throwing Exception"
			+e.getMessage());
		}
	}
	
	public String getTeamStatus(String countryName, String leagueName, String teamName)
	{
		String countries = EMPTY_STRING;
		countries=getCountry();
		Document doc=Jsoup.parse(countries);
		Elements elements = doc.getAllElements();
		JSONParser json = new JSONParser(countries);
		List<Country> countryList=new ArrayList<Country>();
		try {
			Map jsonObject = (Map)json.parse();
			countryList = (ArrayList<Country>)jsonObject.get(COUNTRY_OBJECT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Type listTypeCountry = new TypeToken<ArrayList<Country>>(){ }.getType();
		 * List<Country> countryList = new Gson().fromJson(elements.toString(),
		 * listTypeCountry);
		 */
		String countryid=countryList.stream()
		.filter((country)->country.getCountry_name().equals(countryName))
		.findFirst().get().getCountry_id();
		StringBuffer response=new StringBuffer();
		response.append("Country ID>>"+countryid+"\t"+"Country Name>>"+countryName+"\n");
		String leaguesString=EMPTY_STRING;
		leaguesString=getLeagues(countryid);
		Type listTypeLeagues = new TypeToken<ArrayList<Leagues>>(){
			
		}.getType();
		List<Leagues> leaguesList = new Gson().fromJson(leaguesString, listTypeLeagues);
		String leagueId=leaguesList.stream()
				.filter((leagues)->leagues.getLeague_name().equals(leagueName)).findFirst()
				.get().getLeague_id();
		response.append("League ID>>"+leagueId+"\t"+"League Name>>"+leagueName+"\n");
		String stringStandings=EMPTY_STRING;
		stringStandings=getStandings(leagueId);
		Type listTypestandings = new TypeToken<ArrayList<Leagues>>(){
			
		}.getType();
		List<Standings> standingList = new Gson().fromJson(stringStandings, listTypestandings);
		List<TeamToDo> teamtodo = standingList.stream()
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
	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		String finalApiUrl = baseUrl+FootBallLeagueService.COUNTRY_STRING
				+FootBallLeagueService.APIKEY_STRING+apiKey;
		URI url;
		String countries = new String();
		try {
			url=new URI(finalApiUrl);
			ResponseEntity<String> entity=rt.getForEntity(url, String.class);
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return stringStandings;
	}

}
