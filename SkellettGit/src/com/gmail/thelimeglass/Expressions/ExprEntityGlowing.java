package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[skellett] [entity] glow[ing] [state] of %entities%")
@Config("EntityGlowing")
@PropertyType(ExpressionType.COMBINED)
@Version("1.9R1")
public class ExprEntityGlowing extends SimpleExpression<Boolean>{
	
	private Expression<Entity> entity;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [entity] glow[ing] [state] of %entities%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		ArrayList<Boolean> entities = new ArrayList<>();
		for (final Entity ent : entity.getAll(e)) {
			entities.add(ent.isGlowing());
		}
		return entities.toArray(new Boolean[entities.size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			for (final Entity ent : entity.getAll(e)) {
				ent.setGlowing((Boolean)delta[0]);
			}
		} else if (mode == ChangeMode.RESET) {
			for (final Entity ent : entity.getAll(e)) {
				ent.setGlowing(false);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}