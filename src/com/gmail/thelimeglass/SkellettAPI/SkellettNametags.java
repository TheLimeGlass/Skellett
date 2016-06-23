package com.gmail.thelimeglass.SkellettAPI;

import org.bukkit.Bukkit;


import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Main;

public class SkellettNametags {
	@SuppressWarnings("deprecation")
	public static void setNametagPrefix(Player player, String tag, Player clientPlayers) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(player.getName());
		if (team == null) {
			team = board.registerNewTeam(player.getName());
		}
		String s = Main.cc(tag);
		if(s.length() > 15) {
            s = s.substring(0, 15);
        }
		team.setPrefix(s);
		team.addPlayer((OfflinePlayer)player);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (clientPlayers != null) {
				if (clientPlayers == p) {
					p.setScoreboard(board);
				}
			} else {
				p.setScoreboard(board);
			}
		}
		if (clientPlayers == null) {
			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				if (clientPlayers != null) {
					((Player)p).setScoreboard(board);
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static void resetNametagPrefix(Player player, Player clientPlayers) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(player.getName());
		if (team != null) {
			team.setPrefix("");
			team.addPlayer((OfflinePlayer)player);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (clientPlayers != null) {
					if (clientPlayers == p) {
						p.setScoreboard(board);
					}
				} else {
					p.setScoreboard(board);
				}
			}
		} else {
			return;
		}
	}
	public static String getNametagPrefix(Player player) {
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName());
		if (team != null) {
			return team.getPrefix();
		} else {
			return null;
		}
	}
	@SuppressWarnings("deprecation")
	public static void setNametagSuffix(Player player, String tag, Player clientPlayers) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(player.getName());
		if (team == null) {
			team = board.registerNewTeam(player.getName());
		}
		String s = Main.cc(tag);
		if(s.length() > 15) {
            s = s.substring(0, 15);
        }
		team.setSuffix(s);
		team.addPlayer((OfflinePlayer)player);
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (clientPlayers != null) {
				if (clientPlayers == p) {
					p.setScoreboard(board);
				}
			} else {
				p.setScoreboard(board);
			}
		}
		if (clientPlayers == null) {
			for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
				if (clientPlayers != null) {
					((Player)p).setScoreboard(board);
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	public static void resetNametagSuffix(Player player, Player clientPlayers) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(player.getName());
		if (team != null) {
			team.setSuffix("");
			team.addPlayer((OfflinePlayer)player);
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (clientPlayers != null) {
					if (clientPlayers == p) {
						p.setScoreboard(board);
					}
				} else {
					p.setScoreboard(board);
				}
			}
		} else {
			return;
		}
	}
	public static void resetNametag(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(player.getName());
		if (team != null) {
			team.unregister();
		} else {
			return;
		}
	}
	public static String getNametagSuffix(Player player) {
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName());
		if (team != null) {
			return team.getSuffix();
		} else {
			return null;
		}
	}
}
