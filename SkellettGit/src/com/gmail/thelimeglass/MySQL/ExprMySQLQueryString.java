package com.gmail.thelimeglass.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprMySQLQueryString extends SimpleExpression<String>{
	
	//[skellett] mysql string[s] %string% (in|from|of) %resultset%
	
	private Expression<String> string;
	private Expression<ResultSet> rs;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		rs = (Expression<ResultSet>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] mysql string %string% (in|from|of) %resultset%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			while (rs.getSingle(e).next()) {
				String name = rs.getSingle(e).getString(string.getSingle(e));
				data.add(name);
			}
			return data.toArray(new String[data.size()]);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}