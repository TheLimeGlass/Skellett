package com.gmail.thelimeglass.Stylishboards;

import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class StyleBoard {

	private Score score;
	private Scoreboard board;
	private Integer slot;
	private Team team;
	
	public StyleBoard(Score score, Scoreboard board, Integer slot, Team team) {
		this.score = score;
		this.board = board;
		this.slot = slot;
		this.team = team;
	}

	public Scoreboard getBoard() {
		return this.board;
	}
	
	public Score getScore() {
		return this.score;
	}

	public Integer getSlot() {
		return this.slot;
	}
	
	public Team getTeam() {
		return this.team;
	}
}