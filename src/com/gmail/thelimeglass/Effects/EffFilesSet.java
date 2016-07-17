package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettAPI.SkellettFiles;

public class EffFilesSet extends Effect {

	//[skellett] set (file|[file] node) %string% (in|at) [file] %string% to %string%
	
	private Expression<String> tag;
	private Expression<String> file;
	private Expression<String> data;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		tag = (Expression<String>) e[0];
		file = (Expression<String>) e[1];
		data = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] set (file|[file] node) %string% (in|at) [file] %string% to %String%";
	}
	@Override
	protected void execute(Event e) {
		SkellettFiles.set(file.getSingle(e), tag.getSingle(e), data.getSingle(e));
	}
}
