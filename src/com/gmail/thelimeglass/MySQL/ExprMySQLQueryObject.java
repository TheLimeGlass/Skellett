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

public class ExprMySQLQueryObject extends SimpleExpression<Object>{
	
	//[skellett] mysql object %string% (in|from|of) %resultset%
	
	private Expression<String> string;
	private Expression<ResultSet> rs;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
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
		return "[skellett] mysql object[s] %string% (in|from|of) %resultset%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		ArrayList<Object> data = new ArrayList<Object>();
		try {
			while (rs.getSingle(e).next()) {
				Object name = rs.getSingle(e).getObject(string.getSingle(e));
				data.add(name);
			}
			return data.toArray(new Object[data.size()]);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}