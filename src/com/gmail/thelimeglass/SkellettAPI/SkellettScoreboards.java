package com.gmail.thelimeglass.SkellettAPI;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public final class SkellettScoreboards {
	public static final int SIZE = 15;
	private static final Map<Scoreboard, SkellettScoreboards> scoreboardCache = new WeakHashMap<Scoreboard, SkellettScoreboards>();
	private static final BiMap<Integer, OfflinePlayer> playerHolder = HashBiMap.create((int)15);
	private final Scoreboard scoreboard;
	private final Objective objective;
	private final Map<Integer, Team> teams = new HashMap<Integer, Team>();
	private int counter;
	public SkellettScoreboards(Scoreboard scoreboard) {
		this(scoreboard, null);
	}
	public SkellettScoreboards(Scoreboard scoreboard, String title) {
		this.scoreboard = scoreboard;
		int teamCounter = 0;
		for (int i = 15; i >= 0; --i) {
			Team team = scoreboard.registerNewTeam("SKELLETT_TEAM_" + teamCounter++);
			team.addPlayer(SkellettScoreboards.getPlayerForPosition(i));
			this.teams.put(i, team);
		}
		this.objective = scoreboard.registerNewObjective("SKELLETT_OBJ", "dummy");
		this.objective.setDisplayName(title == null ? "SKELLETT_SB" : title);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	public void clearText(int index) {
		this.setText(index, null);
	}
	public void setText(int index, String text) {
		int position = 15 - index;
		OfflinePlayer player = SkellettScoreboards.getPlayerForPosition(position);
		Team team = this.teams.get(position);
		if (text == null || text.isEmpty()) {
			--this.counter;
			team.setPrefix("");
			team.setSuffix("");
			this.scoreboard.resetScores(player);
			if (this.counter == 0) {
				this.scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			}
			return;
		}
		++this.counter;
		if (this.scoreboard.getObjective(DisplaySlot.SIDEBAR) != this.objective) {
			this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		SkellettScoreboards.applyTeamName(text, team);
		this.objective.getScore(player).setScore(position);
	}
	public void setTitle(String title) {
		this.objective.setDisplayName(title);
	}
	public Scoreboard getNewScoreboard() {
		return this.scoreboard;
	}
	public int getSize() {
		return 15;
	}
	public static SkellettScoreboards of(Scoreboard scoreboard) {
		SkellettScoreboards board = scoreboardCache.get((Object)scoreboard);
		if (board != null) {
			return board;
		}
		board = new SkellettScoreboards(scoreboard);
		scoreboardCache.put(scoreboard, board);
		return board;
	}
	public static BiMap<Integer, OfflinePlayer> getPlayerHolder() {
		return HashBiMap.create(playerHolder);
	}
	public static OfflinePlayer getPlayerForPosition(Integer position) {
		if (position != null) {
			return (OfflinePlayer)playerHolder.get((Object)position);
		} else {
			return null;
		}
	}
	public static Integer getPositionForPlayer(OfflinePlayer player) {
		if (player != null) {
			return (Integer)playerHolder.inverse().get((Object)player);
		} else {
			return null;
		}
	}
	public static void deleteScoreboard(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam("SKELLETT_TEAM_" + player.getName());
		if (team != null) {
			team.unregister();
		} else {
			return;
		}
	}
	private static void applyTeamName(String string, Team team) {
		if (team != null) {
			if (string.length() <= 16) {
				team.setPrefix(string);
				team.setSuffix("");
			} else {
				String firstPart = string.substring(0, 16);
				String secondPart = ChatColor.getLastColors((String)firstPart) + string.substring(16, Math.min(32, string.length()));
				team.setPrefix(firstPart);
				team.setSuffix(secondPart.length() <= 16 ? secondPart : secondPart.substring(0, 16));
			}
		} else {
			return;
		}
	}
	static {
		for (int i = 15; i >= 0; --i) {
			String name = ChatColor.values()[i].toString() + ChatColor.RESET.toString();
			playerHolder.put((Integer)i, (OfflinePlayer)Bukkit.getOfflinePlayer((String)name));
		}
	}
}