package com.gmail.thelimeglass.MySQL;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprMySQLPort extends SimpleExpression<Number>{
	
	//[skellett] mysql['s] port
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] mysql['s] port";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{Skellett.mysqlData.getInt("MySQLSetup.Port")};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number value = (Number)delta[0];
		if (mode == ChangeMode.SET) {
			Skellett.mysqlData.set("MySQLSetup.Port", value.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Number.class);
		return null;
	}
}