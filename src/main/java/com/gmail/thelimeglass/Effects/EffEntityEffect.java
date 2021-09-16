package com.gmail.thelimeglass.Effects;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.RegisterEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (make|force) %entity% [to] [(perform|do)] [entity] effect %entityeffect%")
@Config("EntityEffect")
@RegisterEnum(ExprClass=EntityEffect.class, value="entityeffect")
public class EffEntityEffect extends Effect {
	
	private Expression<Entity> entity;
	private Expression<EntityEffect> effect;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		effect = (Expression<EntityEffect>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (make|force) %entity% [to] [(perform|do)] [entity] effect %entityeffect%";
	}
	@Override
	protected void execute(Event e) {
		if (entity.getSingle(e) == null) {
			return;
		}
		entity.getSingle(e).playEffect(effect.getSingle(e));
	}
}
