package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;

import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (load|create) world %string% [with generator %-string%] [[and] [with] [fawe] async[hronous]]")
@Config("World")
public class EffLoadCreateWorld extends Effect {
	
	private String parsedSyntax;
	private Expression<String> world, generator;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		world = (Expression<String>) e[0];
		generator = (Expression<String>) e[1];
		parsedSyntax = parser.expr;
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (load|create) world %string% [with generator %-string%] [[and] [with] [fawe] async[hronous]]";
	}
	@Override
	protected void execute(Event e) {
		Boolean FAWE = false;
		if (Bukkit.getWorld(world.getSingle(e)) != null) {
			Bukkit.unloadWorld(world.getSingle(e), true);
		}
		if (parsedSyntax.contains("async") && Skellett.instance.getConfig().getBoolean("Async", false)) FAWE = true;
		if (FAWE) {
			AsyncWorld FAWEworld = AsyncWorld.create(new WorldCreator(world.getSingle(e)));
			FAWEworld.commit();
		} else {
			WorldCreator creator = new WorldCreator(world.getSingle(e));
			if (generator != null) {
				creator.generator(generator.getSingle(e));
			}
			creator.createWorld();
		}
	}
}