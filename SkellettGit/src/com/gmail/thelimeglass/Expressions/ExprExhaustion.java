package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"exhaustion of %player%", "%player%'s exhaustion"})
@Config("Exhaustion")
@PropertyType(ExpressionType.COMBINED)
public class ExprExhaustion extends SimpleExpression<Number>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "exhaustion of %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		Number ex = player.getSingle(e).getExhaustion();
		return new Number[]{ex.floatValue()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number ex = (Number)delta[0];
		Number exNow = player.getSingle(e).getExhaustion();
		if (mode == ChangeMode.SET) {
			player.getSingle(e).setExhaustion(ex.floatValue());
		} else if (mode == ChangeMode.RESET) {
			player.getSingle(e).setExhaustion(0);
		} else if (mode == ChangeMode.ADD) {
			player.getSingle(e).setExhaustion(exNow.floatValue() + ex.floatValue());
		} else if (mode == ChangeMode.REMOVE) {
			player.getSingle(e).setExhaustion(exNow.floatValue() - ex.floatValue());
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}