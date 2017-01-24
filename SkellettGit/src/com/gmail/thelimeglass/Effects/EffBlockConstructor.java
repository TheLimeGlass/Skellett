package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.BlockConstructor;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(create|start|make|build|construct) %string% with %itemtype% at %location% [[with effect[s]] %-boolean%]")
@Config("BlockConstructor")
public class EffBlockConstructor extends Effect {
	
	private Expression<String> string;
	private Expression<ItemType> material;
	private Expression<Location> location;
	private Expression<Boolean> boo;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		material = (Expression<ItemType>) e[1];
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
		Material m = material.getSingle(e).getRandom().getType();
		if (boo == null) {
			new BlockConstructor(new BlockConstructor.BlockLayer(string.getSingle(e)).setBlockType('M', m)).generate(location.getSingle(e), false);
		} else {
			new BlockConstructor(new BlockConstructor.BlockLayer(string.getSingle(e)).setBlockType('M', m)).generate(location.getSingle(e), boo.getSingle(e));
		}
	}
}
