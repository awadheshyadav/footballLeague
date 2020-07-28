package com.league.football.beans;

public class TeamToDo {
	private final String team_id;
	private final String team_name;
	private final String overall_league_position;
	
	public TeamToDo(String team_id, String team_name, String overall_league_position)
	{
		this.team_id=team_id;
		this.team_name=team_name;
		this.overall_league_position=overall_league_position;
	}

	public String getTeam_id() {
		return team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public String getOverall_league_position() {
		return overall_league_position;
	}
	public String toString()
	{
		return "Team ID : "+team_id+"\t"
				+"Team Name : "+team_name+"\t"
				+"Overall League Position : "+overall_league_position+"\n";
	}
}
