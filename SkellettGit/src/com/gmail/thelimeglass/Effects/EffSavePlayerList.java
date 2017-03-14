package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;


import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("save [offline] player list")
@Config("SavePlayerList")
public class EffSavePlayerList extends Effect {
	
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "save [offline] player list";
	}
	@Override
	protected void execute(Event e) {
		Bukkit.savePlayers();
	}
}