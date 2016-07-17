package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.BlockConstructor;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffBlockConstructor extends Effect {

	//(create|start|make|build|construct) %string% with %string% at %location% [[with effect[s]] %-boolean%]
	
	private Expression<String> string;
	private Expression<String> material;
	private Expression<Location> location;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		material = (Expression<String>) e[1];
		location = (Expression<Location>) e[2];
		boo = (Expression<Boolean>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(create|start|make|build|construct) %string% with %string% at %location% [[with] [effect[s]][ ]%-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		Material m = Material.valueOf(material.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
		try {
			m = Material.valueOf(m.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException t) {
			Skript.error(m.toString() + " Unknown material type");
			return;
		}
		if (boo == null) {
			new BlockConstructor(new BlockConstructor.BlockLayer(string.getSingle(e)).setBlockType('M', m)).generate(location.getSingle(e), false);
		} else {
			new BlockConstructor(new BlockConstructor.BlockLayer(string.getSingle(e)).setBlockType('M', m)).generate(location.getSingle(e), boo.getSingle(e));
		}
	}
}
