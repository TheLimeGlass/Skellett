package com.gmail.thelimeglass.Regenerator;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(create|make) [a] [new] [skellett] regenerator with ID %string% (from|within) [location[s]] %location% (to|and) %location%")
@Config("Main.Regenerator")
@FullConfig
public class EffCreateRegenerator extends Effect {
	
	private Expression<String> ID;
	private Expression<Location> pos1, pos2;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		pos1 = (Expression<Location>) e[1];
		pos2 = (Expression<Location>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(create|make) [a] [new] [skellett] regenerator with ID %string% (from|within) [location[s]] %location% (to|and) %location%";
	}
	@Override
	protected void execute(Event e) {
		RegeneratorManager.addRegenerator(ID.getSingle(e), pos1.getSingle(e), pos2.getSingle(e));
	}
}