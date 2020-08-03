package com.league.football.beans;

public class LeagueStatus {
	
	 private String country_name;
	 private String league_name;
	 private String team_name;
	public LeagueStatus(String country_name2, String league_name2, String team_name2) {
		
		// TODO Auto-generated constructor stub
		this.country_name=country_name2;
		this.league_name=league_name2;
		this.team_name=team_name2;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getLeague_name() {
		return league_name;
	}
	public void setLeague_name(String league_name) {
		this.league_name = league_name;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	 
}
