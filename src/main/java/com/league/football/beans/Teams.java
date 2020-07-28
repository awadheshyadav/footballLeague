package com.league.football.beans;

public class Teams {

	private String team_key;
	private String team_name;
	private String team_badge;
	Players[] players;
	Coaches coaches;
	public String getTeam_key() {
		return team_key;
	}
	public void setTeam_key(String team_key) {
		this.team_key = team_key;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	public String getTeam_badge() {
		return team_badge;
	}
	public void setTeam_badge(String team_badge) {
		this.team_badge = team_badge;
	}
	public Players[] getPlayers() {
		return players;
	}
	public void setPlayers(Players[] players) {
		this.players = players;
	}
	public Coaches getCoaches() {
		return coaches;
	}
	public void setCoaches(Coaches coaches) {
		this.coaches = coaches;
	}
	
	
}
