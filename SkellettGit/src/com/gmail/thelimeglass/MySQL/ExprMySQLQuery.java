package com.gmail.thelimeglass.MySQL;

import java.sql.ResultSet;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprMySQLQuery extends SimpleExpression<ResultSet>{
	
	//[skellett] mysql result of query %string%
	
	private Expression<String> string;
	@Override
	public Class<? extends ResultSet> getReturnType() {
		return ResultSet.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] mysql result of query %string%";
	}
	@Override
	@Nullable
	protected ResultSet[] get(Event e) {
		if (string != null) {
			ResultSet rs = MySQLManager.query(string.getSingle(e));
			if (rs != null) {
				return new ResultSet[]{rs};
			}
		}
		return null;
	}
}