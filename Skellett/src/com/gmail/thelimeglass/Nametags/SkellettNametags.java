package com.gmail.thelimeglass.Nametags;

import org.bukkit.Bukkit;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Skellett;

public class SkellettNametags {
	public static void createNametag(String nametag) {
		if(nametag.length() > 16) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cA nametag ID name can't have more than 16 characters!"));
            return;
        }
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team == null) {
			team = board.registerNewTeam(nametag);
		}
	}
	@SuppressWarnings("deprecation")
	public static void addPlayerNametag(Player player, String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team == null) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
		team.addPlayer((OfflinePlayer)player);
		player.setScoreboard(board);
	}
	@SuppressWarnings("deprecation")
	public static void removePlayerNametag(Player player, String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team == null) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
		team.removePlayer((OfflinePlayer)player);
	}
	public static void setNametagPrefix(String nametag, String tag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team == null) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
		String s = Skellett.cc(tag);
		if(s.length() > 16) {
            s = s.substring(0, 16);
        }
		team.setPrefix(tag);
	}
	public static void setNametagSuffix(String nametag, String tag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team == null) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
		String s = Skellett.cc(tag);
		if(s.length() > 16) {
            s = s.substring(0, 16);
        }
		team.setSuffix(tag);
	}
	public static void resetNametag(String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team != null) {
			team.setPrefix("");
			team.setSuffix("");
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
	}
	public static void resetNametagPrefix(String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team != null) {
			team.setPrefix("");
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
	}
	public static void resetNametagSuffix(String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team != null) {
			team.setSuffix("");
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
	}
	public static void deleteNametag(String nametag) {
		Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
		Team team = board.getTeam(nametag);
		if (team != null) {
			team.unregister();
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return;
		}
	}
	public static String getNametagPrefix(String nametag) {
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(nametag);
		if (team != null) {
			return team.getPrefix();
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return null;
		}
	}
	public static String getNametagSuffix(String nametag) {
		Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(nametag);
		if (team != null) {
			return team.getSuffix();
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cNo nametag under the name " + nametag + " &cwas found!"));
			return null;
		}
	}
}