package com.gmail.thelimeglass.Stylishboards;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.gmail.thelimeglass.Skellett;

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
			if (memoryData.containsKey(ID)) {
				deleteScore(ID, board);
			}
			Score score = styleBoards.get(board).getObjective("StylishBoards").getScore(value);
			score.setScore(slot);
			memoryData.put(ID, new StyleBoard(score, styleBoards.get(board), slot));
		}
	}
	
	public static void deleteScore(String ID, String board) {
		if (styleBoards.containsKey(board) && memoryData.containsKey(ID)) {
			StyleBoard style = memoryData.get(ID);
			styleBoards.get(board).resetScores(style.getScore().getEntry());
			memoryData.remove(ID);
		}
	}
	
	public static void updateScore(String ID, String board, String value, int slot) {
		if (styleBoards.containsKey(board)) {
			if (memoryData.containsKey(ID)) {
				StyleBoard style = memoryData.get(ID);
				styleBoards.get(board).resetScores(style.getScore().getEntry());
				memoryData.remove(ID);
			}
			String finalValue = value;
			if (Skellett.plugin.getConfig().getBoolean("StylishBoardsSubString", true)) {
				if(finalValue.length() > 40) {
					finalValue = finalValue.substring(0, 40);
		        }
			}
			Score score = styleBoards.get(board).getObjective("StylishBoards").getScore(finalValue);
			score.setScore(slot);
			memoryData.put(ID, new StyleBoard(score, styleBoards.get(board), slot));
		}
	}
	
	public static void updateScore(String ID, Scoreboard board, String value, int slot) {
		if (styleBoards.containsValue(board)) {
			if (memoryData.containsKey(ID)) {
				StyleBoard style = memoryData.get(ID);
				board.resetScores(style.getScore().getEntry());
				memoryData.remove(ID);
			}
			String finalValue = value;
			if (Skellett.plugin.getConfig().getBoolean("StylishBoardsSubString", true)) {
				if(finalValue.length() > 40) {
					finalValue = finalValue.substring(0, 40);
		        }
			}
			Score score = board.getObjective("StylishBoards").getScore(finalValue);
			score.setScore(slot);
			memoryData.put(ID, new StyleBoard(score, board, slot));
		}
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