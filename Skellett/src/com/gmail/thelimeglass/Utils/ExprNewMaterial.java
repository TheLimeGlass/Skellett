package com.gmail.thelimeglass.Utils;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
	
public class ExprNewMaterial extends SimpleExpression<Material> {
	
	//[skellett] new [changed] material
	
	public Class<? extends Material> getReturnType() {
		return Material.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)EntityChangeBlockEvent.class)) {
			Skript.error((String)"%new block% can only be used with an EntityChangeBlock event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "new block";
	}
	@Nullable
	protected Material[] get(Event e) {
		return new Material[]{((EntityChangeBlockEvent)e).getTo()};
	}
}