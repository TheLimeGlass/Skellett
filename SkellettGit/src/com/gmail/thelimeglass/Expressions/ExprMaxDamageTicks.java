package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] [maximum] damage delay of %entity%", "[skellett] %entity%'s [maximum] damage delay"})
@Config("MaxDamageTicks")
@PropertyType("COMBINED")
public class ExprMaxDamageTicks extends SimpleExpression<Timespan>{
	
	private Expression<LivingEntity> entity;
	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<LivingEntity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[maximum] damage delay of %entity%";
	}
	@SuppressWarnings("deprecation")
	@Override
	@Nullable
	protected Timespan[] get(Event e) {
		return new Timespan[]{Timespan.fromTicks((int)(entity.getSingle(e)).getMaximumNoDamageTicks())};
	}
	@SuppressWarnings("deprecation")
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Timespan before = Timespan.fromTicks((int)(entity.getSingle(e)).getMaximumNoDamageTicks());
		if (mode == ChangeMode.SET) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks(((Timespan)delta[0]).getTicks());
		} else if (mode == ChangeMode.ADD) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks(before.getTicks() + ((Timespan)delta[0]).getTicks());
		} else if (mode == ChangeMode.REMOVE) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks(before.getTicks() - ((Timespan)delta[0]).getTicks());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Timespan.class);
		}
		return null;
	}
}