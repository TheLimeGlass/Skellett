package com.gmail.thelimeglass.Stylishboards;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class StyleManager {
	private static final HashMap<String, Scoreboard> styleBoards = new HashMap<>();
	private static final HashMap<String, StyleBoard> memoryData = new HashMap<>();
	
	public static HashMap<String, StyleBoard> getMemory() {
		return memoryData;
	}
	
	public static void dump() {
		styleBoards.clear();
		memoryData.clear();
	}
	
	public static void createBoard(String ID) {
		if (!styleBoards.containsKey(ID)) {
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			board.registerNewObjective("StylishBoards", "dummy");
			board.getObjective("StylishBoards").setDisplaySlot(DisplaySlot.SIDEBAR);
			styleBoards.put(ID, board);
		}
	}
	
	public static void deleteBoard(String ID) {
		if (styleBoards.containsKey(ID)) {
			/* Not working
			for (String data : memoryData.keySet()) {
				deleteScore(data, ID);
			}
			*/
			styleBoards.get(ID).getObjective("StylishBoards").unregister();
			styleBoards.remove(ID);
		}
	}
	
	public static void setTitle(String board, String title) {
		if (styleBoards.containsKey(board)) {
			styleBoards.get(board).getObjective("StylishBoards").setDisplayName(title);
		}
	}
	
	public static String getTitle(String board) {
		if (styleBoards.containsKey(board)) {
			styleBoards.get(board).getObjective("StylishBoards").getDisplayName();
		}
		return null;
	}
	
	public static void createScore(String ID, String board, String value, int slot) {
		if (styleBoards.containsKey(board)) {
			String prefix = null, name = null, suffix = null;
			if (value.length() > 48) {
				value = value.substring(0, 47);
			}
			if (value.length() <= 16) {
				name = value;
			} else if (value.length() <= 32) {
				name = value.substring(0, 16);
				suffix = value.substring(16, value.length());
			} else {
				prefix = value.substring(0, 16);
				name = value.substring(16, 32);
				suffix = value.substring(32, value.length());
			}
			if (memoryData.containsKey(ID)) {
				deleteScore(ID, board);
			}
			Team team = styleBoards.get(board).getEntryTeam(name);
			if (team == null) {
				team = styleBoards.get(board).registerNewTeam(name);
			}
			team.addEntry(name);
			if ((prefix != null) || (suffix != null)) {
				if (prefix != null) {
					team.setPrefix(prefix);
				}
				team.setSuffix(suffix);
			}
			Score score = styleBoards.get(board).getObjective("StylishBoards").getScore(name);
			score.setScore(slot);
			memoryData.put(ID, new StyleBoard(score, styleBoards.get(board), slot, team));
		}
	}
	
	public static Boolean shouldUpdate(String ID, String board, String value, int slot) {
		if (styleBoards.containsKey(board)) {
			StyleBoard scoreboard = memoryData.get(ID);
			if (!getTeamValue(scoreboard.getTeam()).equals(value)) {
				return true;
			}
			Score score = styleBoards.get(board).getObjective("StylishBoards").getScore(scoreboard.getTeam().getName());
			if (score.getScore() != slot) {
				return true;
			}
		}
		return false;
	}
	
	public static void deleteScore(String ID, String board) {
		if (styleBoards.containsKey(board) && memoryData.containsKey(ID)) {
			StyleBoard style = memoryData.get(ID);
			styleBoards.get(board).resetScores(style.getScore().getEntry());
			if (styleBoards.get(board).getTeam(style.getScore().getEntry()) != null) {
				styleBoards.get(board).getTeam(style.getScore().getEntry()).unregister();
			}
			memoryData.remove(ID);
		}
	}
	
	public static void updateScore(String ID, String board, String value, int slot) {
		if (styleBoards.containsKey(board)) {
			if (shouldUpdate(ID, board, value, slot)) {
				if (memoryData.containsKey(ID)) {
					deleteScore(ID, board);
				}
				createScore(ID, board, value, slot);
			}
		}
	}
	
	public static void updateScore(String ID, Scoreboard scoreboard, String value, int slot) {
		for (String b : getBoards()) {
			if (styleBoards.get(b) == scoreboard) {
				updateScore(ID, b, value, slot);
			}
		}
	}
	
	public static void updateScore(String ID, Scoreboard scoreboard, Team team, int slot) {
		for (String b : getBoards()) {
			if (styleBoards.get(b) == scoreboard) {
				String update = getTeamValue(team);
				updateScore(ID, b, update, slot);
			}
		}
	}
	
	public static String getTeamValue(Team team) {
		String update = team.getName();
		if (team.getPrefix() != null) {
			update = team.getPrefix() + update;
		}
		if (team.getSuffix() != null) {
			update = update + team.getSuffix();
		}
		return update;
	}
	
	public static Scoreboard get(String board) {
		if (styleBoards.containsKey(board)) {
			return styleBoards.get(board);
		}
		return null;
	}
	
	public static Boolean contains(String board) {
		if (styleBoards.containsKey(board)) {
			return true;
		}
		return false;
	}
	
	public static String[] getBoards() {
		ArrayList<String> boards = new ArrayList<>();
		for (final String s : styleBoards.keySet()) {
			boards.add(s);
		}
		return boards.toArray(new String[boards.size()]);
	}
}