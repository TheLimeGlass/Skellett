package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[(make|set)] [entity] %entity% [to] [a[n]] (1在aby|2地dult)")
@Config("BabyAdult")
public class EffBabyAdult extends Effect {
	
	private Expression<Entity> entity;
	private Integer marker;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(make|set)] [entity] %entity% [to] [a[n]] (1在aby|2地dult)";
	}
	@Override
	protected void execute(Event e) {
		if (entity != null) {
			if (marker == 1) {
				if (Ageable.class.isAssignableFrom(entity.getSingle(e).getClass())) {
					((Ageable)entity.getSingle(e)).setBaby();
				} else if (Zombie.class.isAssignableFrom(entity.getSingle(e).getClass())) {
					((Zombie)entity.getSingle(e)).setBaby(true);
				}
			} else {
				if (Ageable.class.isAssignableFrom(entity.getSingle(e).getClass())) {
					((Ageable)entity.getSingle(e)).setAdult();
				} else if (Zombie.class.isAssignableFrom(entity.getSingle(e).getClass())) {
					((Zombie)entity.getSingle(e)).setBaby(false);
				}
			}
		}
	}
}
