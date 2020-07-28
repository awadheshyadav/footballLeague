package com.league.football.beans;

public class Coaches {
	private final String coach_name;
	private final String coach_country;
	private final Integer coach_age;
	
	public Coaches(String coach_name , String coach_country, Integer coach_age)
	{
		this.coach_name=coach_name;
		this.coach_country=coach_country;
		this.coach_age=coach_age;
	}

	public String getCoach_name() {
		return coach_name;
	}

	public String getCoach_country() {
		return coach_country;
	}

	public Integer getCoach_age() {
		return coach_age;
	}
}
