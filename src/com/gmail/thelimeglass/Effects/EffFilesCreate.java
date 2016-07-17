package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettFiles;

public class EffFilesCreate extends Effect {

	//[skellett] c[reate][ ][f][ile] %string%
	
	private Expression<String> file;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		file = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] c[reate][ ][f][ile] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettFiles.createFile(file.getSingle(e));
	}
}
