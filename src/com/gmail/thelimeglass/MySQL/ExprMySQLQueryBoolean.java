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

public class ExprMySQLQueryBoolean extends SimpleExpression<Boolean>{
	
	//[skellett] mysql boolean[s] %string% (in|from|of) %resultset%
	
	private Expression<String> string;
	private Expression<ResultSet> rs;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
		return "[skellett] mysql boolean %string% (in|from|of) %resultset%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		ArrayList<Boolean> data = new ArrayList<Boolean>();
		try {
			while (rs.getSingle(e).next()) {
				Boolean name = rs.getSingle(e).getBoolean(string.getSingle(e));
				data.add(name);
			}
			return data.toArray(new Boolean[data.size()]);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}