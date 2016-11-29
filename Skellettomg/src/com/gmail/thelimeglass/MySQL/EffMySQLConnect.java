package com.gmail.thelimeglass.MySQL;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EffMySQLConnect extends Effect {

	//[skellett] connect [to] mysql
	
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] connect [to] mysql";
	}
	@Override
	protected void execute(Event e) {
		String host = Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getString("MySQLSetup.Host", "");
		String user = Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getString("MySQLSetup.Username", "");
		String password = Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getString("MySQLSetup.Password", "");
		String database = Bukkit.getServer().getPluginManager().getPlugin("Skellett").getConfig().getString("MySQLSetup.Database", "");
		MySQLManager.setConnection(host, user, password, database);
		MySQLManager.connect();
	}
}
