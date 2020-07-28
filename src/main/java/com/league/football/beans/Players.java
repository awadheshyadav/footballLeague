package com.league.football.beans;

public class Players {
	
	private final String player_key;
	private final String player_name;
	private final Integer player_number;
	private final String player_country;
	private final String player_type;
	private final Integer player_age;
	private final Integer player_match_played;
	private final Integer player_goals;
	private final Integer player_yellow_cards;
	private final Integer player_red_cards;
	private final String team_name;
	private final String team_key;
	
	public Players(String player_key, String player_name, Integer player_number
			, String player_country, String player_type, Integer player_age
			,Integer player_match_played, Integer player_goals
			,Integer player_yellow_cards, Integer player_red_cards
			, String team_name,String team_key)
	{
		this.player_key=player_key;
		this.player_name=player_name;
		this.player_number=player_number;
		this.player_country=player_country;
		this.player_type=player_type;
		this.player_age=player_age;
		this.player_match_played=player_match_played;
		this.player_goals=player_goals;
		this.player_yellow_cards=player_yellow_cards;
		this.player_red_cards=player_red_cards;
		this.team_name=team_name;
		this.team_key=team_key;
	}

	public String getPlayer_key() {
		return player_key;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public Integer getPlayer_number() {
		return player_number;
	}

	public String getPlayer_country() {
		return player_country;
	}

	public String getPlayer_type() {
		return player_type;
	}

	public Integer getPlayer_age() {
		return player_age;
	}

	public Integer getPlayer_match_played() {
		return player_match_played;
	}

	public Integer getPlayer_goals() {
		return player_goals;
	}

	public Integer getPlayer_yellow_cards() {
		return player_yellow_cards;
	}

	public Integer getPlayer_red_cards() {
		return player_red_cards;
	}

	public String getTeam_name() {
		return team_name;
	}

	public String getTeam_key() {
		return team_key;
	}
	
	
}
