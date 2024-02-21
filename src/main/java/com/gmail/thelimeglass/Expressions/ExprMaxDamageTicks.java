package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] [maximum] damage delay of %entity%", "[skellett] %entity%'s [maximum] damage delay"})
@Config("MaxDamageTicks")
@PropertyType(ExpressionType.COMBINED)
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

	@Override
	@Nullable
	protected Timespan[] get(Event e) {
		return new Timespan[]{Timespan.fromTicks((int)(entity.getSingle(e)).getMaximumNoDamageTicks())};
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (entity == null || entity.getSingle(e) == null)
			return;
		Timespan before = Timespan.fromTicks((int)(entity.getSingle(e)).getMaximumNoDamageTicks());
		if (mode == ChangeMode.SET) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks((int) ((Timespan)delta[0]).getTicks());
		} else if (mode == ChangeMode.ADD) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks((int) (before.getTicks() + ((Timespan)delta[0]).getTicks()));
		} else if (mode == ChangeMode.REMOVE) {
			((LivingEntity)entity.getSingle(e)).setMaximumNoDamageTicks((int) (before.getTicks() - ((Timespan)delta[0]).getTicks()));
		}
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Timespan.class);
		}
		return null;
	}

}
