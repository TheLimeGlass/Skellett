package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] spawn reason")
@Config("SpawnReason")
@PropertyType(ExpressionType.SIMPLE)
@RegisterEnum("spawnreason")
public class ExprSpawnReason extends SimpleExpression<CreatureSpawnEvent.SpawnReason> {
	
	public Class<? extends CreatureSpawnEvent.SpawnReason> getReturnType() {
		return CreatureSpawnEvent.SpawnReason.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(CreatureSpawnEvent.class)) {
			Skript.error("You can not use SpawnReason expression in any event but on spawn!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Spawn reason";
	}
	@Nullable
	protected CreatureSpawnEvent.SpawnReason[] get(Event e) {
		return new CreatureSpawnEvent.SpawnReason[]{((CreatureSpawnEvent)e).getSpawnReason()};
	}
}