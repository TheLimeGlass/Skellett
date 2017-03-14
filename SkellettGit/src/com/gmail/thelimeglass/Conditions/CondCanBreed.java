package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[entity] %entity% (1¦can|2¦can([ ]no|')t) [be] breed")
@Config("Breed")
public class CondCanBreed extends Condition {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[entity] %entity% (1¦can|2¦(can([ ]no|')t) breed";
	}
	public boolean check(Event e) {
		if (entity.getSingle(e) instanceof Ageable) {
			if (((Ageable)entity.getSingle(e)).canBreed()) {
				return isNegated();
			} else {
				return !isNegated();
			}
		}					return false;
	}
}