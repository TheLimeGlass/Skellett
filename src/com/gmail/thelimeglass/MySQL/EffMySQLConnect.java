package com.gmail.thelimeglass.MySQL;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

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
		String host = Skellett.mysqlData.getString("MySQLSetup.Host", "");
		String database = Skellett.mysqlData.getString("MySQLSetup.Database", "");
		if (Skellett.mysqlData.getBoolean("MySQLSetup.SQLite")) {
			MySQLManager.setConnection(host, null, null, database);
		} else {
			String user = Skellett.mysqlData.getString("MySQLSetup.Username", "");
			String password = Skellett.mysqlData.getString("MySQLSetup.Password", "");
			MySQLManager.setConnection(host, user, password, database);
		}
		MySQLManager.connect();
	}
}
