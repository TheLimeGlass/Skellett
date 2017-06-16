package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.SkellettFiles;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax("[skellett] move file [path] %string% to [path] %string%")
@Config("Main.Files")
@FullConfig
public class EffMoveFile extends Effect {

	private Expression<String> file1, file2;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		file1 = (Expression<String>) e[0];
		file2 = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] move file [path] %string% to [path] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettFiles.moveFile(file1.getSingle(e), file2.getSingle(e));
	}
}
