package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;

public class CondIsDisguised extends Condition {
	
	//[skellett] [[Libs]Disguises] [(entity|player)] %entitys% [(is|are)] disguised
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] [(entity|player)] %entitys% [(is|are)] disguised";
	}
	public boolean check(Event e) {
		if (DisguiseAPI.isDisguised(entity.getSingle(e))) {
			return true;
		} else {
			return false;
		}
	}
}