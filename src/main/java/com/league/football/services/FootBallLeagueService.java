package com.league.football.services;

public interface FootBallLeagueService {
	public static final String EMPTY_STRING="";
	public static final String COMMA_STRING=",";
	public static final String COLLON_STRING=":";
	public static final String ZERO_STRING="0";
	public static final String ENCODE_UTF_STRING="utf-8";
	public static final String COUNTRY_STRING="/?action=get_countries";
	public static final String LEAGUE_STRING="/?action=get_leagues&country_id=";
	public static final String STANDINGS_STRING="/?action=get_standings&league_id=";
	public static final String APIKEY_STRING="&APIkey=";
	public static final String COUNTRY_NOT_FOUND_STRING="NO_COUNTRY_FOUND";
	public static final String COUNTRY_OBJECT="countries";
	public String getCountry();
	public String getLeagues(String country_id);
	public String getStandings(String league_id);
}
