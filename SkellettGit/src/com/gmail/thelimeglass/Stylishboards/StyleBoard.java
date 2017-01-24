package com.gmail.thelimeglass.Stylishboards;

import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class StyleBoard {

	private Score score;
	private Scoreboard board;
	private Integer slot;
	
	public StyleBoard(Score score, Scoreboard board, Integer slot) {
		this.score = score;
		this.board = board;
		this.slot = slot;
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
}